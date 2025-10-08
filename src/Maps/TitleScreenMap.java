package Maps;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.ImageEffect;
import GameObject.Sprite;
import Level.Map;
import Tilesets.CommonTileset;
import Utils.Colors;
import Utils.Point;
import SpriteFont.SpriteFont;

import java.awt.*;

public class TitleScreenMap extends Map {

    private Sprite enemy;
    private Sprite alex;
    private SpriteFont titleText;

    public TitleScreenMap() {
        super("title_screen_map.txt", new CommonTileset());
        
        // put Enemy (samurai) on the left side of the screen facing towards right
        Point enemyLocation = getMapTile(6, 7).getLocation();
        enemy = new Sprite(ImageLoader.loadSubImage("samurai.png", Colors.MAGENTA, 0, 0, 22, 16));
        enemy.setScale(4);
        enemy.setImageEffect(ImageEffect.NONE); 
        enemy.setLocation(enemyLocation.x, enemyLocation.y);
        
        // put Alex on the right side of the screen facing towards left
        Point alexLocation = getMapTile(10, 7).getLocation();
        alex = new Sprite(ImageLoader.loadSubImage("Alex sprite planning.png", Colors.MAGENTA, 0, 0, 24, 24));
        alex.setScale(4);
        alex.setImageEffect(ImageEffect.NONE); 
        alex.setLocation(alexLocation.x, alexLocation.y);
        
        // title text at the top
        titleText = new SpriteFont("THE SUMMONS OF CTHULHU", 120, 30, "Comic Sans MS", 40, new Color(255, 255, 255));
        titleText.setOutlineColor(Color.BLACK);
        titleText.setOutlineThickness(3.0f);
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        enemy.draw(graphicsHandler);
        alex.draw(graphicsHandler);
        titleText.draw(graphicsHandler);
    }
}
