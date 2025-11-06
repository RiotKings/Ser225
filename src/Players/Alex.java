package Players;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Player;

import java.util.HashMap;

// This is the class for the Alex player character
// basically just sets some values for physics and then defines animations
public class Alex extends Player {
    private long dodgeStartTime;
    private long dodgeDuration = 400; // milliseconds
    private int knowledge;
    private int sanity;
    
    
    public Alex(SpriteSheet spriteSheet, float x, float y, String startingAnimationName) {
        super(spriteSheet, x, y, startingAnimationName); 
        this.sanity = 6;
        this.knowledge = 0;
        
    }

     public Alex(SpriteSheet spriteSheet, float x, float y, String startingAnimationName,
        int health, int sanity, int knowledge) {
        super(spriteSheet, x, y, startingAnimationName);
        this.sanity = sanity;
        this.knowledge = knowledge;
       
    }

    public int getSanity() { return sanity; }
    public int getKnowledge() { return knowledge; }
   

    public void setSanity(int sanity) {
        this.sanity = Math.max(0, Math.min(sanity, 100));
    }

    public void setKnowledge(int knowledge) {
        this.knowledge = Math.max(0, knowledge);
    }

    

    public void takeDamage(int damage){
        
        if (invincible == false){
                super.takeDamage(damage);
                System.out.println("Player has " + super.getHealth() + " health left!"); 
                System.out.println(invincible);
        }

    }
    
    public Alex(float x, float y) {
        super(new SpriteSheet(ImageLoader.load("Alex sprite planning 2.png"), 24, 24), x, y, "STAND_RIGHT");
        
     
    }
    

    public void update() {
        super.update();
        if (super.getHealth() <= 0){
            // Player is dead - game over logic can go here
        }
        if(hasSpeedBoots){
                walkSpeed = 3.3f;
        }
        if(hasExtraHeart){
                maxHealth = maxHealth + 1;
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build()
            });

            put("STAND_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build()
            });

            put("WALK_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 14)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 14)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 14)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 14)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build()
            });

            put("WALK_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build()
            });
            put("DODGE_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 0), 9)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(2, 1), 9)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(2, 2), 9)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(2, 3), 9)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build()
            });
            put("DODGE_RIGHT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(2, 0), 9)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(2, 1), 9)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(2, 2), 9)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(2, 3), 9)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build()
            });
        }};
    }
}
