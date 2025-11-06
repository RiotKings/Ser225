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
import java.awt.AlphaComposite;
import java.awt.Graphics2D;

public class PhantomEnemy extends NPC {
    private int totalAmountMoved = 0;
    private Direction direction = Direction.RIGHT;
    private float speed = 1.5f;

    private int hitsRemaining = 3; // takes 3 hits to disappear
    private float lifetime = 10f; // seconds before auto-despawn
    private long spawnTime;
    
    
    private float detectionRange = 150f;
    private float attackRange = 50f;
    
   
    private float opacity = 0.8f;
    private float flickerTimer = 0f;
    
    public PhantomEnemy(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("Bug.png"), 24, 15), "WALK_RIGHT");
        this.spawnTime = System.currentTimeMillis();
    }

    @Override
    public void performAction(Player player) {
        // check lifetime
        long currentTime = System.currentTimeMillis();
        float elapsedSeconds = (currentTime - spawnTime) / 1000f;
        if (elapsedSeconds >= lifetime || hitsRemaining <= 0) {
            playPhantomDeathEffect();
            this.setMapEntityStatus(MapEntityStatus.REMOVED);
            return;
        }

        // flicker effect
        flickerTimer += 0.1f;
        opacity = 0.7f + (float)(Math.sin(flickerTimer) * 0.2f);

        // calculate distance to player
        float playerX = player.getX();
        float playerY = player.getY();
        float phantomX = this.getX();
        float phantomY = this.getY();
        
        float distanceToPlayer = (float) Math.sqrt(
            (playerX - phantomX) * (playerX - phantomX) + 
            (playerY - phantomY) * (playerY - phantomY)
        );

        // chase player if in range
        if (distanceToPlayer <= detectionRange) {
            chasePlayer(player, distanceToPlayer);
        } else {
            patrolBehavior();
        }

        // "attack" player 
        if (distanceToPlayer <= attackRange) {
            fakeAttackPlayer(player);
        }

        // updated animation
        if (direction == Direction.RIGHT) {
            currentAnimationName = "WALK_RIGHT";
        } else {
            currentAnimationName = "WALK_LEFT";
        }
    }

    private void chasePlayer(Player player, float distanceToPlayer) {
        float playerX = player.getX();
        float playerY = player.getY();
        float phantomX = this.getX();
        float phantomY = this.getY();
        
        float dx = playerX - phantomX;
        float dy = playerY - phantomY;
        
        if (Math.abs(dx) > 0.1f || Math.abs(dy) > 0.1f) {
            float moveX = (dx / distanceToPlayer) * speed;
            float moveY = (dy / distanceToPlayer) * speed;
            
            moveXHandleCollision(moveX);
            moveYHandleCollision(moveY);
            
            if (dx > 0) {
                direction = Direction.RIGHT;
            } else {
                direction = Direction.LEFT;
            }
        }
    }

    private void patrolBehavior() {
        if (totalAmountMoved < 90) {
            float amountMoved = moveXHandleCollision(speed * direction.getVelocity());
            totalAmountMoved += Math.abs(amountMoved);
        } else {
            totalAmountMoved = 0;
            direction = (direction == Direction.LEFT) ? Direction.RIGHT : Direction.LEFT;
        }
    }

    private void fakeAttackPlayer(Player player) {
        // phantom doesn't actually damage player
        // play visual effect
        
    }

    public void takeDamage(int damage) {
        hitsRemaining--;
        
        
        // flash effect when hit
        opacity = 0.3f;
        
        if (hitsRemaining <= 0) {
            playPhantomDeathEffect();
            this.setMapEntityStatus(MapEntityStatus.REMOVED);
        }
    }

    private void playPhantomDeathEffect() {

        // add particle effect here
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
        // Draw with transparency
        Graphics2D g = graphicsHandler.getGraphics();
        AlphaComposite oldComposite = (AlphaComposite) g.getComposite();
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        super.draw(graphicsHandler);
        g.setComposite(oldComposite);
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