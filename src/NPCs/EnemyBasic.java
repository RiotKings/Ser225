package NPCs;

import Level.NPC;
import Level.Player;
import Utils.Direction;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import GameObject.Bullet;
import Level.MapEntityStatus;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

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
    private static final int DAMAGE = 1;

    private int currentHealth = 10;
    private int maxHealth = 10;

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

        if (currentHealth <= 0) {
            this.setMapEntityStatus(MapEntityStatus.REMOVED);
            return;
        } 

        Rectangle eb = getBounds();
        Rectangle pb = player.getBounds();
        float ex = eb.getX() + eb.getWidth() * 0.5f;
        float ey = eb.getY() + eb.getHeight() * 0.5f;
        float px = pb.getX() + pb.getWidth() * 0.5f;
        float py = pb.getY() + pb.getHeight() * 0.5f;

        float dx = px - ex;
        float dy = py - ey;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        isAttacking = (dist <= ATTACK_RADIUS);

        this.setCurrentAnimationName(dx < 0 ? "STAND_LEFT" : "STAND_RIGHT");

        if (isAttacking) {
            if (dist > STOP_DISTANCE) {
                float inv = (dist < 1e-5f) ? 0f : 1f / dist;
                float nx = dx * inv, ny = dy * inv;
                float move = chaseSpeed * STEP_DT;
                tryMove(nx * move, ny * move);
            }

            bulletCooldown -= STEP_DT;
            if (bulletCooldown <= 0f) {
                float inv = (dist < 1e-5f) ? 0f : 1f / dist;
                float nx = dx * inv, ny = dy * inv;

                float bx = ex + nx * MUZZLE_OFFSET;
                float by = ey + ny * MUZZLE_OFFSET;

                Bullet bullet = new Bullet(1000, bx, by, nx, ny, DAMAGE);
                bullet.setMap(this.map);
                map.addNPC(bullet);

                bulletCooldown = BULLET_INTERVAL;
            }

        } else {
            timeToNextHeadingChange -= STEP_DT;
            if (timeToNextHeadingChange <= 0f) {
                wanderDir = pickRandomDir();
                timeToNextHeadingChange = randRange(headingChangeIntervalMin, headingChangeIntervalMax);
            }

            if (hasBounds) {
                float x1 = eb.getX1(), y1 = eb.getY1(), w = eb.getWidth(), h = eb.getHeight();
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
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
    
    public int getHealth() {
        return currentHealth;
    }

    public void setHealth(int health) {
        this.currentHealth = Math.max(0, Math.min(health, maxHealth));
    }

    public void takeDamage(int damage) {
        setHealth(currentHealth - damage);
    }

    @Override
    public Rectangle getBounds() {
        Rectangle b = super.getBounds();
        if (b.getWidth() < 1 || b.getHeight() < 1) {
            return new Rectangle(b.getX1(), b.getY1(), 20, 20);
        }
        return b;
    }

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
