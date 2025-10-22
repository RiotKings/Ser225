package NPCs;

import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import Level.NPC;
import Level.Player;
import Level.MapEntityStatus;
import Utils.Point;

public class Bug extends NPC {
    // Health system
    private int health = 2;
    private int maxHealth = 2;
    
    // Chase behavior
    private float chaseSpeed = 1.2f;
    private static final float CONTACT_DAMAGE = 1;
    private float damageCooldown = 0f;
    private static final float DAMAGE_INTERVAL = 2.0f; // Damage every 2 seconds
    
    public Bug(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("Bug.png"), 24, 15), "WALK_RIGHT");
    }

    // Bug now chases the player continuously and deals continuous contact damage
    @Override
    public void performAction(Player player) {
        final float STEP_DT = 1f / 60f; // Frame time
        
        // Check if bug is dead
        if (health <= 0) {
            this.setMapEntityStatus(MapEntityStatus.REMOVED);
            return;
        }
        
        // Update damage cooldown
        damageCooldown -= STEP_DT;
        
        // Get bounds for proper collision detection
        Rectangle bugBounds = getBounds();
        Rectangle playerBounds = player.getBounds();
        
        // Check if bug is actually touching/colliding with player (improved detection)
        boolean isTouchingPlayer = (bugBounds.getX() < playerBounds.getX() + playerBounds.getWidth() &&
                                   bugBounds.getX() + bugBounds.getWidth() > playerBounds.getX() &&
                                   bugBounds.getY() < playerBounds.getY() + playerBounds.getHeight() &&
                                   bugBounds.getY() + bugBounds.getHeight() > playerBounds.getY());
        
        // Calculate distance to player for movement
        float playerX = playerBounds.getX() + playerBounds.getWidth() / 2f;
        float playerY = playerBounds.getY() + playerBounds.getHeight() / 2f;
        float bugX = bugBounds.getX() + bugBounds.getWidth() / 2f;
        float bugY = bugBounds.getY() + bugBounds.getHeight() / 2f;
        
        float dx = playerX - bugX;
        float dy = playerY - bugY;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        
        // Deal damage if actually touching player
        if (isTouchingPlayer && damageCooldown <= 0f) {
            player.takeDamage((int) CONTACT_DAMAGE);
            System.out.println("[Bug] Contact damage! Applied " + (int) CONTACT_DAMAGE + " damage to player. Next damage in " + DAMAGE_INTERVAL + " seconds.");
            damageCooldown = DAMAGE_INTERVAL; // Reset cooldown
        } else if (isTouchingPlayer && damageCooldown > 0f) {
            System.out.println("[Bug] Touching player but on cooldown. " + String.format("%.1f", damageCooldown) + " seconds remaining.");
        }
        
        // Always chase the player
        if (distance > 1f) { // Small buffer to prevent jittering
            // Normalize direction and move towards player
            float moveX = (dx / distance) * chaseSpeed;
            float moveY = (dy / distance) * chaseSpeed;
            
            // Move bug towards player
            moveXHandleCollision(moveX);
            moveYHandleCollision(moveY);
            
            // Set animation based on movement direction
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) {
                    currentAnimationName = "WALK_RIGHT";
                } else {
                    currentAnimationName = "WALK_LEFT";
                }
            } else {
                // Use last horizontal direction for vertical movement
                if (dx > 0) {
                    currentAnimationName = "WALK_RIGHT";
                } else {
                    currentAnimationName = "WALK_LEFT";
                }
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
            System.out.println("[Bug] Bug died!");
        }
    }
    
    public void takeDamage(int damage) {
        int oldHealth = health;
        setHealth(health - damage);
        System.out.println("[Bug] Took " + damage + " damage. Health: " + oldHealth + " -> " + health + "/" + maxHealth);
        if (health <= 0) {
            System.out.println("[Bug] Bug has died!");
        }
    }
    
    public boolean isDead() {
        return health <= 0;
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        // Don't draw if dead
        if (health <= 0) {
            return;
        }
        super.draw(graphicsHandler);
    }
}
