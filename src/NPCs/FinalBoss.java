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

public class FinalBoss extends NPC {
    private Direction direction = Direction.RIGHT;
    private float chaseSpeed = 90f;

    private int currentHealth = 20;
    private int maxHealth = 20;
    
    private float detectionRange = 1000f;
    private float attackRange = 80f;
    private long lastDamageTime = 0;
    private long damageCooldown = 1000;
    private static final float StopDistance = 5f;
    
    private static final float AttackRadius = 500f;
    private float bulletCooldown = 0f;
    private static final float BulletInterval = 1.0f;
    private static final float MuzzleOffset = 50f;
    private static final int BulletDamage = 2;
    
    private float circleShotCooldown = 0f;
    private static final float CircleShotInterval = 8.0f;
    private static final int CircleBulletCount = 30;
    private static final float CircleBulletSpeed = 150f;
    private int stuckCounter = 0;
    private float lastX, lastY;
    private long lastPositionCheck = 0;
    private static final int STUCK_THRESHOLD = 30;
    private static final long POSITION_CHECK_INTERVAL = 100;
    
    public FinalBoss(int id, float x, float y) {
        super(id, x, y, new SpriteSheet(ImageLoader.load("Cuthulu.png"), 1000, 1000), "STAND_RIGHT");
    }

    @Override
    public void performAction(Player player) {
        final float STEP_DT = 1f / 60f;
        
        if (currentHealth <= 0) {
            this.setMapEntityStatus(MapEntityStatus.REMOVED);
            map.decreaseEnemyCount();
            System.out.println("Final Boss defeated! Enemy count = " + map.getEnemyCount());
            return;
        }

        Rectangle bossBounds = getBounds();
        Rectangle playerBounds = player.getBounds();
        float bossX = bossBounds.getX() + bossBounds.getWidth() * 0.5f;
        float bossY = bossBounds.getY() + bossBounds.getHeight() * 0.5f;
        float playerX = playerBounds.getX() + playerBounds.getWidth() * 0.5f;
        float playerY = playerBounds.getY() + playerBounds.getHeight() * 0.5f;
        
        float dx = playerX - bossX;
        float dy = playerY - bossY;
        float distanceToPlayer = (float) Math.sqrt(dx * dx + dy * dy);

        if (dx > 0) {
            direction = Direction.RIGHT;
            this.setCurrentAnimationName("STAND_RIGHT");
        } else {
            direction = Direction.LEFT;
            this.setCurrentAnimationName("STAND_LEFT");
        }

        if (distanceToPlayer <= detectionRange) {
            chasePlayer(player, distanceToPlayer, STEP_DT);
        }

        boolean isColliding = bossBounds.intersects(playerBounds);
        
        if (isColliding || distanceToPlayer <= attackRange) {
            damagePlayer(player);
        }

        if (distanceToPlayer <= AttackRadius) {
            bulletCooldown -= STEP_DT;
            if (bulletCooldown <= 0f) {
                shootAtPlayer(player, bossX, bossY, dx, dy, distanceToPlayer);
                bulletCooldown = BulletInterval;
            }
        } else {
            bulletCooldown = 0f;
        }
        
        circleShotCooldown -= STEP_DT;
        if (circleShotCooldown <= 0f) {
            shootCircleOfBullets(bossX, bossY);
            circleShotCooldown = CircleShotInterval;
        }
        
        clampToMapBounds();
    }

    private void chasePlayer(Player player, float distanceToPlayer, float STEP_DT) {
        if (distanceToPlayer > StopDistance) {
            Rectangle bossBounds = getBounds();
            Rectangle playerBounds = player.getBounds();
            float bossX = bossBounds.getX() + bossBounds.getWidth() * 0.5f;
            float bossY = bossBounds.getY() + bossBounds.getHeight() * 0.5f;
            float playerX = playerBounds.getX() + playerBounds.getWidth() * 0.5f;
            float playerY = playerBounds.getY() + playerBounds.getHeight() * 0.5f;
            
            float dx = playerX - bossX;
            float dy = playerY - bossY;
            
            float inv = (distanceToPlayer < 1e-5f) ? 0f : 1f / distanceToPlayer;
            float nx = dx * inv;
            float ny = dy * inv;
            float move = chaseSpeed * STEP_DT;
            
            checkIfStuck();
            
            if (stuckCounter > STUCK_THRESHOLD) {
                tryAlternativeMovement(player, dx, dy, STEP_DT);
            } else {
                float movedX = moveXHandleCollision(nx * move);
                float movedY = moveYHandleCollision(ny * move);
                
                if (Math.abs(movedX) < Math.abs(nx * move) * 0.5f && Math.abs(movedY) < Math.abs(ny * move) * 0.5f) {
                    if (Math.abs(dx) > Math.abs(dy)) {
                        moveXHandleCollision(nx * move);
                    } else {
                        moveYHandleCollision(ny * move);
                    }
                } else {
                    if (Math.abs(movedX) > 0.1f || Math.abs(movedY) > 0.1f) {
                        stuckCounter = Math.max(0, stuckCounter - 2);
                    }
                }
            }
        }
    }

    private void shootAtPlayer(Player player, float bossX, float bossY, float dx, float dy, float dist) {
        if (dist < 1e-5f) return;
        
        float inv = 1f / dist;
        float nx = dx * inv;
        float ny = dy * inv;

        float bx = bossX + nx * MuzzleOffset;
        float by = bossY + ny * MuzzleOffset;

        Bullet bullet = new Bullet(1000, bx, by, nx, ny, BulletDamage);
        bullet.setMap(this.map);
        map.addNPC(bullet);
        
        System.out.println("Final Boss shot at player!");
    }
    
    private void shootCircleOfBullets(float centerX, float centerY) {
        for (int i = 0; i < CircleBulletCount; i++) {
            double angle = (2 * Math.PI / CircleBulletCount) * i;
            float nx = (float) Math.cos(angle);
            float ny = (float) Math.sin(angle);
            
            float bx = centerX + nx * MuzzleOffset;
            float by = centerY + ny * MuzzleOffset;
            
            Bullet bullet = new Bullet(1000, bx, by, nx * CircleBulletSpeed, ny * CircleBulletSpeed, BulletDamage);
            bullet.setMap(this.map);
            map.addNPC(bullet);
        }
        
        System.out.println("Final Boss shot circle of bullets!");
    }

    private void damagePlayer(Player player) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastDamageTime >= damageCooldown) {
            player.takeDamage(2);
            lastDamageTime = currentTime;
            System.out.println("Final Boss damaged player! Player health: " + player.getHealth());
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
            
            if (distanceMoved < 2.0f) {
                stuckCounter++;
            } else {
                stuckCounter = Math.max(0, stuckCounter - 5);
            }
            
            lastX = currentX;
            lastY = currentY;
            lastPositionCheck = currentTime;
        }
    }
    
    private void tryAlternativeMovement(Player player, float dx, float dy, float STEP_DT) {
        if (Math.abs(dx) > Math.abs(dy)) {
            if (Math.abs(dx) > 0.1f) {
                float moveX = (dx > 0 ? chaseSpeed : -chaseSpeed) * STEP_DT;
                float movedX = moveXHandleCollision(moveX);
                if (Math.abs(movedX) > 0.1f && Math.abs(dy) > 0.1f) {
                    float moveY = (dy > 0 ? chaseSpeed : -chaseSpeed) * STEP_DT;
                    moveYHandleCollision(moveY);
                }
            }
        } else {
            if (Math.abs(dy) > 0.1f) {
                float moveY = (dy > 0 ? chaseSpeed : -chaseSpeed) * STEP_DT;
                float movedY = moveYHandleCollision(moveY);
                if (Math.abs(movedY) > 0.1f && Math.abs(dx) > 0.1f) {
                    float moveX = (dx > 0 ? chaseSpeed : -chaseSpeed) * STEP_DT;
                    moveXHandleCollision(moveX);
                }
            }
        }
        
        if (stuckCounter > STUCK_THRESHOLD * 2) {
            float randomAngle = (float) (Math.random() * 2 * Math.PI);
            float randomSpeed = chaseSpeed * 0.6f;
            float randomMoveX = (float) (Math.cos(randomAngle) * randomSpeed * STEP_DT);
            float randomMoveY = (float) (Math.sin(randomAngle) * randomSpeed * STEP_DT);
            
            moveXHandleCollision(randomMoveX);
            moveYHandleCollision(randomMoveY);
            
            if (Math.random() < 0.2f) {
                stuckCounter = STUCK_THRESHOLD / 2;
            }
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getImage())
                    .withScale(6)
                    .withBounds(5, 0, 35, 35)
                    .build()
            });
            put("STAND_RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getImage())
                    .withScale(6)
                    .withBounds(5, 0, 35, 35)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
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

    public int getMaxHealth() {
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
            return new Rectangle(b.getX1(), b.getY1(), 60, 60);
        }
        float paddingX = 8f;
        float paddingYTop = 15f;
        float paddingYBottom = 8f;
        return new Rectangle(
            b.getX1() - paddingX, 
            b.getY1() - paddingYTop,
            (int)(b.getWidth() + paddingX * 2), 
            (int)(b.getHeight() + paddingYTop + paddingYBottom)
        );
    }
    
    private void clampToMapBounds() {
        if (map == null) {
            return;
        }

        int mapStartX = 0;
        int mapStartY = 0;
        int mapEndX = map.getEndBoundX();
        int mapEndY = map.getEndBoundY();

        Rectangle bounds = getBounds();
        float bossX = getX();
        float bossY = getY();
        float bossWidth = bounds.getWidth();
        float bossHeight = bounds.getHeight();

        float bossLeft = bossX;
        float bossRight = bossX + bossWidth;
        float bossTop = bossY;
        float bossBottom = bossY + bossHeight;

        if (bossLeft < mapStartX) {
            setX(mapStartX);
        } else if (bossRight > mapEndX) {
            setX(mapEndX - bossWidth);
        }
        
        if (bossTop < mapStartY) {
            setY(mapStartY);
        } else if (bossBottom > mapEndY) {
            setY(mapEndY - bossHeight);
        }
    }
}

