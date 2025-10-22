package Maps;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.ImageEffect;
import GameObject.Sprite;
import GameObject.SpriteSheet;
import Level.Map;
import Tilesets.CommonTileset;
import Utils.Point;

// Represents the map that is used as a background for the main menu and credits menu screen
public class TitleScreenMap extends Map {

    private Sprite alex;
    private Sprite samurai;

    public TitleScreenMap() {
        super("title_screen_map.txt", new CommonTileset());
        
        // Center the characters on the screen
        // Alex on the left side, centered vertically - use same sprite as Alex player
        Point alexLocation = getMapTile(6, 7).getLocation().subtractX(6).subtractY(7);
        SpriteSheet alexSpriteSheet = new SpriteSheet(ImageLoader.load("Alex sprite planning 2.png"), 24, 24);
        alex = new Sprite(alexSpriteSheet.getSprite(0, 0));
        alex.setScale(3);
        alex.setImageEffect(ImageEffect.FLIP_HORIZONTAL); // Face right
        alex.setLocation(alexLocation.x, alexLocation.y);
        
        // Samurai on the right side, centered vertically - use same sprite as EnemyBasic
        Point samuraiLocation = getMapTile(10, 7).getLocation().subtractX(6).subtractY(7);
        SpriteSheet samuraiSpriteSheet = new SpriteSheet(ImageLoader.load("samurai.png"), 22, 16);
        samurai = new Sprite(samuraiSpriteSheet.getSprite(0, 0));
        samurai.setScale(3);
        // Samurai faces left toward Alex (with flip to face left)
        samurai.setImageEffect(ImageEffect.FLIP_HORIZONTAL);
        samurai.setLocation(samuraiLocation.x, samuraiLocation.y);
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        alex.draw(graphicsHandler);
        samurai.draw(graphicsHandler);
    }
}
