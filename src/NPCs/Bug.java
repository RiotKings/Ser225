package NPCs;

import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import Level.MapEntityStatus;
import Level.NPC;
import Level.Player;
import Utils.Direction;
import Utils.Point;

public class Bug extends NPC {
    private int totalAmountMoved = 0;
    private Direction direction = Direction.RIGHT;
    private float speed = 1.5f; // Increased speed for chasing

    private int currentHealth = 2;
    private int maxHealth = 2;
    
    // Chasing behavior variables
    private float detectionRange = 150f; // Range at which bug starts chasing player
    private float attackRange = 50f; // Range at which bug can damage player (increased)
    private long lastDamageTime = 0;
    private long damageCooldown = 1000; // 1 second cooldown between damage
    
    // Movement and stuck prevention
    private int stuckCounter = 0;
    private float lastX, lastY;
    private long lastPositionCheck = 0;
    private static final int STUCK_THRESHOLD = 30; // frames before considering stuck
    private static final long POSITION_CHECK_INTERVAL = 100; // ms between position checks
    
    public Bug(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("Bug.png"), 24, 15), "WALK_RIGHT");
    }

    // Chasing behavior - bug will chase player when in range
    @Override
    public void performAction(Player player) {
        if (currentHealth <= 0) {
            this.setMapEntityStatus(MapEntityStatus.REMOVED);
            return;
        }

        // Calculate distance to player
        float playerX = player.getX();
        float playerY = player.getY();
        float bugX = this.getX();
        float bugY = this.getY();
        
        float distanceToPlayer = (float) Math.sqrt(
            (playerX - bugX) * (playerX - bugX) + 
            (playerY - bugY) * (playerY - bugY)
        );
        
        // Debug output every 60 frames (about once per second)
        if (System.currentTimeMillis() % 1000 < 50) {
            System.out.println("Bug position: (" + bugX + ", " + bugY + "), Player position: (" + playerX + ", " + playerY + "), Distance: " + distanceToPlayer);
        }

        // If player is within detection range, chase them
        if (distanceToPlayer <= detectionRange) {
            chasePlayer(player, distanceToPlayer);
        } else {
            // Default patrol behavior when player is not in range
            patrolBehavior();
        }

        // Check if bug is close enough to damage player
        if (distanceToPlayer <= attackRange) {
            System.out.println("Bug is within attack range! Distance: " + distanceToPlayer + ", Attack Range: " + attackRange);
            damagePlayer(player);
        }

        // Update animation based on direction
        if (direction == Direction.RIGHT) {
            currentAnimationName = "WALK_RIGHT";
        } else {
            currentAnimationName = "WALK_LEFT";
        }
    }

    private void chasePlayer(Player player, float distanceToPlayer) {
        float playerX = player.getX();
        float playerY = player.getY();
        float bugX = this.getX();
        float bugY = this.getY();

        // Check if bug is stuck
        checkIfStuck();
        
        // Calculate direction to player
        float dx = playerX - bugX;
        float dy = playerY - bugY;
        
        // Normalize direction
        if (Math.abs(dx) > 0.1f || Math.abs(dy) > 0.1f) {
            float moveX = (dx / distanceToPlayer) * speed;
            float moveY = (dy / distanceToPlayer) * speed;
            
            // If stuck, try alternative movement
            if (stuckCounter > STUCK_THRESHOLD) {
                tryAlternativeMovement(player);
            } else {
                // Normal movement towards player
                float movedX = moveXHandleCollision(moveX);
                float movedY = moveYHandleCollision(moveY);
                
                // If we couldn't move much, try moving in one direction at a time
                if (Math.abs(movedX) < Math.abs(moveX) * 0.5f && Math.abs(movedY) < Math.abs(moveY) * 0.5f) {
                    // Try moving horizontally first
                    if (Math.abs(dx) > Math.abs(dy)) {
                        moveXHandleCollision(moveX);
                    } else {
                        // Try moving vertically first
                        moveYHandleCollision(moveY);
                    }
                }
            }
            
            // Update facing direction
            if (dx > 0) {
                direction = Direction.RIGHT;
            } else {
                direction = Direction.LEFT;
            }
        }
    }

    private void patrolBehavior() {
        // Check if stuck during patrol
        checkIfStuck();
        
        // If stuck during patrol, try to get unstuck
        if (stuckCounter > STUCK_THRESHOLD) {
            // Try moving in a random direction
            direction = (Math.random() < 0.5) ? Direction.LEFT : Direction.RIGHT;
            totalAmountMoved = 0; // Reset patrol distance
            stuckCounter = 0;
        }
        
        // Random patrol behavior - move back and forth with some randomness
        if (totalAmountMoved < 90) {
            float amountMoved = moveXHandleCollision(speed * direction.getVelocity());
            totalAmountMoved += Math.abs(amountMoved);
        } else {
            totalAmountMoved = 0;
            // Random direction change
            if (Math.random() < 0.3) { // 30% chance to change direction randomly
                direction = (direction == Direction.LEFT) ? Direction.RIGHT : Direction.LEFT;
            }
        }
        
        // Occasionally add some vertical movement for more randomness
        if (Math.random() < 0.1) { // 10% chance each frame
            float randomVerticalMove = (float)(Math.random() - 0.5) * speed * 0.5f; // Small vertical movement
            moveYHandleCollision(randomVerticalMove);
        }
    }

    private void damagePlayer(Player player) {
        long currentTime = System.currentTimeMillis();
        System.out.println("damagePlayer called! Cooldown check: " + (currentTime - lastDamageTime) + " >= " + damageCooldown);
        if (currentTime - lastDamageTime >= damageCooldown) {
            player.takeDamage(1);
            lastDamageTime = currentTime;
            System.out.println("Bug damaged player! Player health: " + player.getHealth());
        } else {
            System.out.println("Bug damage on cooldown, time remaining: " + (damageCooldown - (currentTime - lastDamageTime)));
        }
    }
    
    private void checkIfStuck() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastPositionCheck >= POSITION_CHECK_INTERVAL) {
            float currentX = this.getX();
            float currentY = this.getY();
            
            // Check if position has changed significantly
            float distanceMoved = (float) Math.sqrt(
                (currentX - lastX) * (currentX - lastX) + 
                (currentY - lastY) * (currentY - lastY)
            );
            
            if (distanceMoved < 1.0f) {
                stuckCounter++;
            } else {
                stuckCounter = 0; // Reset if we're moving
            }
            
            lastX = currentX;
            lastY = currentY;
            lastPositionCheck = currentTime;
        }
    }
    
    private void tryAlternativeMovement(Player player) {
        float playerX = player.getX();
        float playerY = player.getY();
        float bugX = this.getX();
        float bugY = this.getY();
        
        // Try different movement strategies when stuck
        float dx = playerX - bugX;
        float dy = playerY - bugY;
        
        // Strategy 1: Try moving only horizontally
        if (Math.abs(dx) > 0.1f) {
            float moveX = (dx > 0 ? speed : -speed);
            moveXHandleCollision(moveX);
        }
        
        // Strategy 2: Try moving only vertically
        if (Math.abs(dy) > 0.1f) {
            float moveY = (dy > 0 ? speed : -speed);
            moveYHandleCollision(moveY);
        }
        
        // Strategy 3: Try moving in a random direction to get unstuck
        if (stuckCounter > STUCK_THRESHOLD * 2) {
            // Move in a random direction for a few frames
            float randomAngle = (float) (Math.random() * 2 * Math.PI);
            float randomMoveX = (float) (Math.cos(randomAngle) * speed);
            float randomMoveY = (float) (Math.sin(randomAngle) * speed);
            
            moveXHandleCollision(randomMoveX);
            moveYHandleCollision(randomMoveY);
            
            // Reset stuck counter after trying random movement
            if (Math.random() < 0.3f) { // 30% chance to reset
                stuckCounter = 0;
            }
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(2)
                    .withBounds(3, 5, 18, 7)
                    .build()
            });
            put("STAND_RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(2)
                    .withBounds(3, 5, 18, 7)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .build()
           });
           put("WALK_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(2)
                        .withBounds(3, 5, 18, 7)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 8)
                        .withScale(2)
                        .withBounds(3, 5, 18, 7)
                        .build()
            });
            put("WALK_RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(2)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(3, 5, 18, 7)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 8)
                        .withScale(2)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(3, 5, 18, 7)
                        .build()
            });
        }};
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

    public int getHealth() {
        return currentHealth;
    }

    public  int getMaxHealth() {
        return maxHealth;
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
}
