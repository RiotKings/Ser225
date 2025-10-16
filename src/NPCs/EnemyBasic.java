package NPCs;

import Level.NPC;
import Level.Player;
import Utils.Direction;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import GameObject.Bullet;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.GraphicsHandler;

public class EnemyBasic extends NPC {

    // Wander
    private float speed = 55f;
    private float timeToNextHeadingChange = 0f;
    private float headingChangeIntervalMin = 0.6f;
    private float headingChangeIntervalMax = 1.8f;

    private float chaseSpeed = 80f;

    private Direction wanderDir = Direction.RIGHT;

    // Arena bounds
    private float boundLeft, boundTop, boundRight, boundBottom;
    private boolean hasBounds = false;
    private float inset = 24f;

    // Attack
    private static final float ATTACK_RADIUS = 215f;
    private boolean isAttacking = false;
    private float bulletCooldown = 0f;
    private static final float BULLET_INTERVAL = 1f;
    private static final float MUZZLE_OFFSET = 10f;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private static final int ENEMY_BULLET_DAMAGE = 1;

    private static final float STOP_DISTANCE = 5f;

    public EnemyBasic(int id, float x, float y) {
        super(id, x, y, new SpriteSheet(ImageLoader.load("samurai.png"), 22, 16), "STAND_LEFT");
        System.out.println("[EnemyBasic] spawned at (" + x + "," + y + ")");
    }

    public void setBounds(float left, float top, float right, float bottom) {
        this.boundLeft = left;
        this.boundTop = top;
        this.boundRight = right;
        this.boundBottom = bottom;
        this.hasBounds = true;
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(3)
                    .withBounds(7, 13, 11, 7)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .build()
            });
            put("STAND_RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(3)
                    .withBounds(7, 13, 11, 7)
                    .build()
            });
        }};
    }

    @Override
    protected void performAction(Player player) {
        final float STEP_DT = 1f / 60f;

        // Distance to player
        Rectangle b = getBounds();
        float px = player.getBounds().getX() + player.getBounds().getWidth() / 2f;
        float py = player.getBounds().getY() + player.getBounds().getHeight() / 2f;
        float ex = b.getX() + b.getWidth() / 2f;
        float ey = b.getY() + b.getHeight() / 2f;
        float dx = px - ex;
        float dy = py - ey;
        float dist = (float)Math.sqrt(dx * dx + dy * dy);

        // Attack
        if (dist <= ATTACK_RADIUS) {
            isAttacking = true;
        } else {
            isAttacking = false;
        }

        // Face player
        if (dx < 0) {
            this.setCurrentAnimationName("STAND_LEFT");
        } else {
            this.setCurrentAnimationName("STAND_RIGHT");
        }

        if (isAttacking) {
            // Chase
            if (dist > STOP_DISTANCE) {
                float nx = dx / dist;
                float ny = dy / dist;
                float move = chaseSpeed * STEP_DT;
                tryMove(nx * move, ny * move);
            }
            // Fire while chasing
            bulletCooldown -= STEP_DT;
            if (bulletCooldown <= 0f) {
            float exScreen = getCalibratedXLocation() + getWidth() / 2f;
            float eyScreen = getCalibratedYLocation() + getHeight() / 2f;

            float pxScreen = player.getCalibratedXLocation() + player.getBounds().getWidth() / 2f;
            float pyScreen = player.getCalibratedYLocation() + player.getBounds().getHeight() / 2f;

            float dxScreen = pxScreen - exScreen;
            float dyScreen = pyScreen - eyScreen;
            float distScreen = (float)Math.sqrt(dxScreen * dxScreen + dyScreen * dyScreen);
            if (dist < 1e-4f) dist = 1f;

            float bx = exScreen + (dxScreen / distScreen) * MUZZLE_OFFSET;
            float by = eyScreen + (dyScreen / dist) * MUZZLE_OFFSET;

            bullets.add(new Bullet(bx, by, dxScreen, dyScreen));
            bulletCooldown = BULLET_INTERVAL;
        }
        } else {

            // Wander
            timeToNextHeadingChange -= STEP_DT;
            if (timeToNextHeadingChange <= 0f) {
                wanderDir = pickRandomDir();
                timeToNextHeadingChange = randRange(headingChangeIntervalMin, headingChangeIntervalMax);
            }

            if (hasBounds) {
                float x1 = b.getX1(), y1 = b.getY1(), w = b.getWidth(), h = b.getHeight();
                if (x1 <= boundLeft + inset)          wanderDir = Direction.RIGHT;
                if (x1 + w >= boundRight - inset)     wanderDir = Direction.LEFT;
                if (y1 <= boundTop + inset)           wanderDir = Direction.DOWN;
                if (y1 + h >= boundBottom - inset)    wanderDir = Direction.UP;
            }

            float perFrameSpeed = speed * STEP_DT;
            switch (wanderDir) {
                case LEFT  -> tryMove(-perFrameSpeed, 0f);
                case RIGHT -> tryMove(perFrameSpeed, 0f);
                case UP    -> tryMove(0f, -perFrameSpeed);
                case DOWN  -> tryMove(0f, perFrameSpeed);
            }
        }

        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.update(STEP_DT);

            Rectangle br = bullet.getBounds();
            Rectangle pr = player.getBounds();

            float bx1 = br.getX1(), by1 = br.getY1();
            float bx2 = bx1 + br.getWidth(), by2 = by1 + br.getHeight();

            float px1 = pr.getX1(), py1 = pr.getY1();
            float px2 = px1 + pr.getWidth(), py2 = py1 + pr.getHeight();

            // Inflate upward/sides to cover torso/head
            final float PAD_X = 4f;
            final float PAD_UP = 18f;
            final float PAD_DOWN = 2f;

            px1 -= PAD_X;  px2 += PAD_X;
            py1 -= PAD_UP; py2 += PAD_DOWN;

            boolean hit = (bx1 < px2) && (bx2 > px1) && (by1 < py2) && (by2 > py1);

            if (hit) {
                player.takeDamage(ENEMY_BULLET_DAMAGE);
                System.out.println("[EnemyBasic] Hit! Applied " + ENEMY_BULLET_DAMAGE + " damage to player.");
                bullets.remove(i);
                continue;
            }

            // Off-map cull
            if (isOffMap(bullet)) {
                bullets.remove(i);
            }
        }
    }

    // Draw bullets
    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        for (Bullet bullet : bullets) {
            bullet.draw(graphicsHandler);
        }
    }

    @Override
    public Rectangle getBounds() {
        Rectangle b = super.getBounds();
        if (b.getWidth() < 1 || b.getHeight() < 1) {
            return new Rectangle(b.getX1(), b.getY1(), 20, 20);
        }
        return b;
    }

    // Attempt a move
    private void tryMove(float dx, float dy) {

        if (dx != 0f) {
            super.moveX(dx);
            if (hasBounds) {
                Rectangle b = getBounds();
                float x1 = b.getX1(), w = b.getWidth();
                if (x1 < boundLeft) super.moveX(boundLeft - x1);
                if (x1 + w > boundRight) super.moveX(boundRight - (x1 + w));
            }
        }

        if (dy != 0f) {
            super.moveY(dy);
            if (hasBounds) {
                Rectangle b = getBounds();
                float y1 = b.getY1(), h = b.getHeight();
                if (y1 < boundTop) super.moveY(boundTop - y1);
                if (y1 + h > boundBottom) super.moveY(boundBottom - (y1 + h));
            }
        }
    }

    // Off-map culling for enemy bullets (WORLD space)
    private boolean isOffMap(Bullet b) {
        if (map == null) return false;

        Rectangle rb = b.getBounds();
        float bx = rb.getX() + rb.getWidth() * 0.5f;
        float by = rb.getY() + rb.getHeight() * 0.5f;

        int w = map.getWidthPixels();
        int h = map.getHeightPixels();
        final int MARGIN = 6;

        return (bx < -MARGIN || bx > (w + MARGIN) || by < -MARGIN || by > (h + MARGIN));
    }

    private static float randRange(float a, float b) {
        return (float) (a + ThreadLocalRandom.current().nextDouble() * (b - a));
    }

    private static Direction pickRandomDir() {
        int r = ThreadLocalRandom.current().nextInt(4);
        return switch (r) {
            case 0 -> Direction.LEFT;
            case 1 -> Direction.RIGHT;
            case 2 -> Direction.UP;
            default -> Direction.DOWN;
        };
    }
}