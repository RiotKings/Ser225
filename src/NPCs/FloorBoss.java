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
import java.util.concurrent.ThreadLocalRandom;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.GraphicsHandler;

public class FloorBoss extends NPC {
    // Enhanced stats for boss
    private int health = 6; // Boss health set to 6
    private int maxHealth = 6;
    
    // Wander
    private float speed = 55f;
    private float timeToNextHeadingChange = 0f;
    private float headingChangeIntervalMin = 0.6f;
    private float headingChangeIntervalMax = 1.8f;

    private float chaseSpeed = 80f;

    private Direction wanderDir = Direction.RIGHT;


    // Attack - Enhanced damage
    private static final float ATTACK_RADIUS = 215f;
    private boolean isAttacking = false;
    private float bulletCooldown = 0f;
    private static final float BULLET_INTERVAL = 1f;
    private static final float MUZZLE_OFFSET = 35f; // 3x bigger offset for 3x bigger boss
    private static final int ENEMY_BULLET_DAMAGE = 2; // Double damage (was 1)

    private static final float STOP_DISTANCE = 5f;

    public FloorBoss(int id, float x, float y) {
        super(id, x, y, new SpriteSheet(ImageLoader.load("samurai.png"), 22, 16), "STAND_LEFT");
        System.out.println("[FloorBoss] spawned at (" + x + "," + y + ")");
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
            float inv = (dist < 1e-5f) ? 0f : 1f / dist;
            float nx = dx * inv, ny = dy * inv;

            float bx = ex + nx * MUZZLE_OFFSET;
            float by = ey + ny * MUZZLE_OFFSET;

            Bullet bullet = new Bullet(1000, bx, by, nx, ny, ENEMY_BULLET_DAMAGE);
            bullet.setMap(this.map);
            map.addNPC(bullet);

            bulletCooldown = BULLET_INTERVAL;
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        // Don't draw if dead
        if (health <= 0) {
            return;
        }
        super.draw(graphicsHandler);
    }

    @Override
    public Rectangle getBounds() {
        Rectangle b = super.getBounds();
        if (b.getWidth() < 1 || b.getHeight() < 1) {
            return new Rectangle(b.getX1(), b.getY1(), 60, 60); // 3x bigger than EnemyBasic
        }
        return b;
    }

    // Attempt a move - no bounds checking for free movement
    private void tryMove(float dx, float dy) {
        if (dx != 0f) {
            super.moveX(dx);
        }

        if (dy != 0f) {
            super.moveY(dy);
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
