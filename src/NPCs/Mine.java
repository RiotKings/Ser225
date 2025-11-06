package NPCs;

import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import GameObject.Bullet;
import Level.MapEntityStatus;
import Level.NPC;
import Level.Player;
import Utils.Direction;
import Utils.Point;

public class Mine extends NPC {
    private int totalAmountMoved = 0;
    private Direction direction = Direction.RIGHT;
    private float speed = 0f;

    private int currentHealth = 3;
    private int maxHealth = 3;
    
    private float detectionRange = 1000f;
    private float attackRange = 50f;
    private long lastDamageTime = 0;
    private long damageCooldown = 1000;
    
    // Explosion timing
    private boolean playerDetected = false;
    private long detectStartTime = 0;
    private boolean hasExploded = false;
    private static final long EXPLOSION_DELAY_MS = 8_000; // 8 seconds

    // Stuck prevention
    private int stuckCounter = 0;
    private float lastX, lastY;
    private long lastPositionCheck = 0;
    private static final int STUCK_THRESHOLD = 30;
    private static final long POSITION_CHECK_INTERVAL = 100;

    public Mine(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("Mine.png"), 24, 24), "STAND_RIGHT");
    }

    @Override
    public void performAction(Player player) {
        // If already removed, don't do anything
        if (this.mapEntityStatus == MapEntityStatus.REMOVED) {
            return;
        }
        
        if (currentHealth <= 0) {
            this.setMapEntityStatus(MapEntityStatus.REMOVED);
            map.decreaseEnemyCount();
            System.out.println("Mine killed by bullets. Enemy count = " +map.getEnemyCount());
            return;
        }

        float playerX = player.getX();
        float playerY = player.getY();
        float mineX = this.getX();
        float mineY = this.getY();
        
        float distanceToPlayer = (float) Math.sqrt(
            (playerX - mineX) * (playerX - mineX) + 
            (playerY - mineY) * (playerY - mineY)
        );

        // Detect player presence
        if (distanceToPlayer <= detectionRange) {
            currentAnimationName = "EXPLODE";
            if (!playerDetected) {
                playerDetected = true;
                detectStartTime = System.currentTimeMillis();
            } else {
                // Check if enough time has passed to trigger explosion
                long elapsed = System.currentTimeMillis() - detectStartTime;
                if (!hasExploded && elapsed >= EXPLOSION_DELAY_MS) {
                    explode();
                    hasExploded = true;
                    return;
                }
            }
        } else {
            // Player left range, reset detection timer
            playerDetected = false;
        }

        // Optional: Keep patrol or idle
        patrolBehavior();
    }

    /**
     * Spawns bullets in a 360° circle and removes the mine.
     */
    private void explode() {
        // Number of bullets and radius
        int numBullets = 24;
        float bulletSpeed = 150f;
        float centerX = getX();
        float centerY = getY();

        for (int i = 0; i < numBullets; i++) {
            double angle = (2 * Math.PI / numBullets) * i;
            float nx = (float) Math.cos(angle);
            float ny = (float) Math.sin(angle);

            // Create bullet slightly offset from center
            float bx = centerX + nx * 10f;
            float by = centerY + ny * 10f;

            Bullet bullet = new Bullet(1000, bx, by, nx * bulletSpeed, ny * bulletSpeed, 1);
            bullet.setMap(this.map);
            map.addNPC(bullet);
        }

        // Remove mine after explosion
        if (this.mapEntityStatus != MapEntityStatus.REMOVED) {
            this.setMapEntityStatus(MapEntityStatus.REMOVED);
            map.decreaseEnemyCount();
            System.out.println("Mine exploded. Enemy count = " +map.getEnemyCount());
        }
    }

    private void patrolBehavior() {
        // (Same as before)
        checkIfStuck();
        if (stuckCounter > STUCK_THRESHOLD) {
            direction = (Math.random() < 0.5) ? Direction.LEFT : Direction.RIGHT;
            totalAmountMoved = 0;
            stuckCounter = 0;
        }

        if (totalAmountMoved < 90) {
            float amountMoved = moveXHandleCollision(speed * direction.getVelocity());
            totalAmountMoved += Math.abs(amountMoved);
        } else {
            totalAmountMoved = 0;
            if (Math.random() < 0.3) {
                direction = (direction == Direction.LEFT) ? Direction.RIGHT : Direction.LEFT;
            }
        }

        if (Math.random() < 0.1) {
            float randomVerticalMove = (float)(Math.random() - 0.5) * speed * 0.5f;
            moveYHandleCollision(randomVerticalMove);
        }
    }

    private void checkIfStuck() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastPositionCheck >= POSITION_CHECK_INTERVAL) {
            float currentX = this.getX();
            float currentY = this.getY();
            float distanceMoved = (float) Math.sqrt(
                (currentX - lastX) * (currentX - lastX) + 
                (currentY - lastY) * (currentY - lastY)
            );
            if (distanceMoved < 1.0f) stuckCounter++;
            else stuckCounter = 0;
            lastX = currentX;
            lastY = currentY;
            lastPositionCheck = currentTime;
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(3)
                    .withBounds(6, 12, 12, 7)
                    .build()
            });
            put("STAND_RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(3)
                    .withBounds(6, 12, 12, 7)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .build()
           });
            put("EXPLODE", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(1, 0), 14)
                    .withScale(3)
                    .withBounds(6, 12, 12, 7)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 1), 7)
                    .withScale(3)
                    .withBounds(6, 12, 12, 7)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 2), 14)
                    .withScale(3)
                    .withBounds(6, 6, 12, 7)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 3), 7)
                    .withScale(3)
                    .withBounds(6, 6, 12, 7)
                    .build()
            });
        }};
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

    public int getHealth() { return currentHealth; }
    public int getMaxHealth() { return maxHealth; }
    public void setHealth(int health) { this.currentHealth = Math.max(0, Math.min(health, maxHealth)); }
    public void takeDamage(int damage) { setHealth(currentHealth - damage); }

    @Override
    public Rectangle getBounds() {
        Rectangle b = super.getBounds();
        if (b.getWidth() < 1 || b.getHeight() < 1)
            return new Rectangle(b.getX1(), b.getY1(), 20, 20);
        return b;
    }
}
