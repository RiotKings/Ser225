package NPCs;

import Level.NPC;
import Level.Player;
import Level.MapEntityStatus;
import Utils.Direction;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import GameObject.Bullet;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.GraphicsHandler;

public class FloorBoss extends NPC {
    // Enhanced stats for boss
    private int health = 20; // Double the HP (samurai has 10)
    private int maxHealth = 20;
    
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

    // Attack - Enhanced damage
    private static final float ATTACK_RADIUS = 215f;
    private boolean isAttacking = false;
    private float bulletCooldown = 0f;
    private static final float BULLET_INTERVAL = 1f;
    private static final float MUZZLE_OFFSET = 10f;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private static final int ENEMY_BULLET_DAMAGE = 2; // Double damage (was 1)

    private static final float STOP_DISTANCE = 5f;

    public FloorBoss(int id, float x, float y) {
        super(id, x, y, new SpriteSheet(ImageLoader.load("samurai.png"), 22, 16), "STAND_LEFT");
        System.out.println("[FloorBoss] spawned at (" + x + "," + y + ")");
    }

    public void setBounds(float left, float top, float right, float bottom) {
        this.boundLeft = left;
        this.boundTop = top;
        this.boundRight = right;
        this.boundBottom = bottom;
        this.hasBounds = true;
        System.out.println("[FloorBoss] Bounds set: left=" + left + ", top=" + top + ", right=" + right + ", bottom=" + bottom);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(9) // 3x bigger than samurai (was 3)
                    .withBounds(21, 39, 33, 21) // 3x bigger bounds (7*3, 13*3, 11*3, 7*3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .build()
            });
            put("STAND_RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(9) // 3x bigger than samurai (was 3)
                    .withBounds(21, 39, 33, 21) // 3x bigger bounds (7*3, 13*3, 11*3, 7*3)
                    .build()
            });
        }};
    }

    // Health system methods
    public int getHealth() {
        return health;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
    
    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, maxHealth));
        if (this.health <= 0) {
            System.out.println("[FloorBoss] Boss died!");
        }
    }
    
    public void takeDamage(int damage) {
        int oldHealth = health;
        setHealth(health - damage);
        System.out.println("[FloorBoss] Took " + damage + " damage. Health: " + oldHealth + " -> " + health + "/" + maxHealth);
        if (health <= 0) {
            System.out.println("[FloorBoss] Boss has died!");
        }
    }
    
    public boolean isDead() {
        return health <= 0;
    }

    @Override
    protected void performAction(Player player) {
        final float STEP_DT = 1f / 60f;

        // Check if boss is dead
        if (health <= 0) {
            this.setMapEntityStatus(MapEntityStatus.REMOVED);
            return;
        }

        // Distance to player
        Rectangle b = getBounds();
        float px = player.getBounds().getX() + player.getBounds().getWidth() / 2f;
        float py = player.getBounds().getY() + player.getBounds().getHeight() / 2f;
        float ex = b.getX() + b.getWidth() / 2f;
        float ey = b.getY() + b.getHeight() / 2f;
        float dx = px - ex;
        float dy = py - ey;
        float dist = (float)Math.sqrt(dx * dx + dy * dy);

        // Face player
        if (dx < 0) {
            this.setCurrentAnimationName("STAND_LEFT");
        } else {
            this.setCurrentAnimationName("STAND_RIGHT");
        }

        // ALWAYS chase the player (boss is aggressive)
        if (dist > STOP_DISTANCE) {
            float nx = dx / dist;
            float ny = dy / dist;
            float move = chaseSpeed * STEP_DT;
            tryMove(nx * move, ny * move);
        }
        
        // Fire bullets at player
        bulletCooldown -= STEP_DT;
        if (bulletCooldown <= 0f) {
            float exScreen = getCalibratedXLocation() + getWidth() / 2f;
            float eyScreen = getCalibratedYLocation() + getHeight() / 2f;

            float pxScreen = player.getCalibratedXLocation() + player.getBounds().getWidth() / 2f;
            float pyScreen = player.getCalibratedYLocation() + player.getBounds().getHeight() / 2f;

            float dxScreen = pxScreen - exScreen;
            float dyScreen = pyScreen - eyScreen;
            float distScreen = (float)Math.sqrt(dxScreen * dxScreen + dyScreen * dyScreen);
            if (distScreen < 1e-4f) distScreen = 1f;

            float bx = exScreen + (dxScreen / distScreen) * MUZZLE_OFFSET;
            float by = eyScreen + (dyScreen / distScreen) * MUZZLE_OFFSET;

            bullets.add(new Bullet(bx, by, dxScreen, dyScreen));
            bulletCooldown = BULLET_INTERVAL;
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
                System.out.println("[FloorBoss] Hit! Applied " + ENEMY_BULLET_DAMAGE + " damage to player.");
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
        // Don't draw if dead
        if (health <= 0) {
            return;
        }
        super.draw(graphicsHandler);
        for (Bullet bullet : bullets) {
            bullet.draw(graphicsHandler);
        }
    }

    @Override
    public Rectangle getBounds() {
        Rectangle b = super.getBounds();
        if (b.getWidth() < 1 || b.getHeight() < 1) {
            return new Rectangle(b.getX1(), b.getY1(), 60, 60); // 3x bigger than EnemyBasic
        }
        return b;
    }

    // Attempt a move - uses bounds checking like EnemyBasic
    private void tryMove(float dx, float dy) {
        if (dx != 0f) {
            super.moveX(dx);
            if (hasBounds) {
                Rectangle b = getBounds();
                float x1 = b.getX1(), w = b.getWidth();
                if (x1 < boundLeft) {
                    super.moveX(boundLeft - x1);
                }
                if (x1 + w > boundRight) {
                    super.moveX(boundRight - (x1 + w));
                }
            }
        }

        if (dy != 0f) {
            super.moveY(dy);
            if (hasBounds) {
                Rectangle b = getBounds();
                float y1 = b.getY1(), h = b.getHeight();
                if (y1 < boundTop) {
                    super.moveY(boundTop - y1);
                }
                if (y1 + h > boundBottom) {
                    super.moveY(boundBottom - (y1 + h));
                }
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
