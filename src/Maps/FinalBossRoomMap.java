package Maps;

import Level.Map;
import Level.MapTile;
import Level.NPC;
import Level.Player;
import Level.Trigger;
import Tilesets.Tileset2;
import Utils.Point;
import NPCs.FinalBoss;
import Engine.ScreenManager;
import Engine.GraphicsHandler;
import Scripts.DoorScript;

import java.util.ArrayList;
import java.awt.geom.AffineTransform;

public class FinalBossRoomMap extends Map {
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    
    public FinalBossRoomMap() {
        super("FinalBossFloor.txt", new Tileset2());
        
        // Disable camera adjustment so camera stays fixed
        this.adjustCamera = false;
        
        
        MapTile spawn = getMapTile(12, 17); // Row 17 has passable floor tiles (tile 34)
        if (spawn != null) {
            this.playerStartPosition = spawn.getLocation();
        } else {
            // Fallback: spawn at a safe position (bottom center, accounting for tile size)
            // Tileset2 uses 16x16 tiles with scale 3, so 48 pixels per tile
            this.playerStartPosition = new Point(12 * 48, 17 * 48);
        }
        this.setEnemyCount(1);
    }
    
    @Override
    public void setupMap() {
        super.setupMap();
        
        // Calculate scale to fit entire map on screen
        int mapWidthPixels = width * tileset.getScaledSpriteWidth();
        int mapHeightPixels = height * tileset.getScaledSpriteHeight();
        int screenWidth = ScreenManager.getScreenWidth();
        int screenHeight = ScreenManager.getScreenHeight();
        
        scaleX = (float) screenWidth / mapWidthPixels;
        scaleY = (float) screenHeight / mapHeightPixels;
        
        // Use the smaller scale to maintain aspect ratio
        float scale = Math.min(scaleX, scaleY);
        scaleX = scale;
        scaleY = scale;
        
        // Position camera to show entire map (camera should cover full map bounds)
        if (camera != null) {
            camera.setLocation(0, 0);
            // Set camera size to cover entire map
            camera.setWidth(width);
            camera.setHeight(height);
        }
    }
    
    @Override
    public void draw(Player player, GraphicsHandler graphicsHandler) {
        // Apply scaling transformation to fit entire map on screen
        java.awt.Graphics2D g2d = graphicsHandler.getGraphics();
        AffineTransform originalTransform = g2d.getTransform();
        
        // Calculate offset to center the scaled map
        int mapWidthPixels = width * tileset.getScaledSpriteWidth();
        int mapHeightPixels = height * tileset.getScaledSpriteHeight();
        int scaledWidth = Math.round(mapWidthPixels * scaleX);
        int scaledHeight = Math.round(mapHeightPixels * scaleY);
        int offsetX = (ScreenManager.getScreenWidth() - scaledWidth) / 2;
        int offsetY = (ScreenManager.getScreenHeight() - scaledHeight) / 2;
        
        // Apply transform: translate to center, then scale
        g2d.translate(offsetX, offsetY);
        g2d.scale(scaleX, scaleY);
        
        // Draw the map with scaling applied
        camera.draw(player, graphicsHandler);
        
        // Restore original transform before drawing UI elements
        g2d.setTransform(originalTransform);
        
        // Draw textbox at normal size (after transform is restored)
        if (textbox.isActive()) {
            textbox.draw(graphicsHandler);
        }
    }
    
    @Override
    public void update(Player player) {
        // Clamp player position to map boundaries when camera adjustment is disabled
        // This prevents the player from getting stuck at the top or edges
        if (player != null) {
            int tileHeight = tileset.getScaledSpriteHeight();
            
            // Clamp Y position: prevent player from going above the first passable row (row 1, since row 0 is walls)
            // Player's top edge should not go above the bottom of row 0 (which is tileHeight pixels)
            float minY = tileHeight; // Bottom of row 0 (top wall row)
            float maxY = endBoundY - player.getHeight(); // Bottom of map minus player height
            
            // Clamp X position
            float minX = startBoundX;
            float maxX = endBoundX - player.getWidth();
            
            // Apply clamping
            if (player.getY() < minY) {
                player.setY(minY);
            } else if (player.getY() > maxY) {
                player.setY(maxY);
            }
            
            if (player.getX() < minX) {
                player.setX(minX);
            } else if (player.getX() > maxX) {
                player.setX(maxX);
            }
        }
        
        // Call parent update method
        super.update(player);
    }
    
    @Override
    public float[] screenToWorldCoordinates(int screenX, int screenY) {
        // In boss room, we apply scaling and centering offset
        // screenX/screenY are in game area coordinates (0 to GAME_WINDOW_WIDTH/HEIGHT)
        // after the base centering offset from GamePanel has been subtracted
        
        // Calculate the offset applied in draw() method (relative to game area)
        int mapWidthPixels = width * tileset.getScaledSpriteWidth();
        int mapHeightPixels = height * tileset.getScaledSpriteHeight();
        int scaledWidth = Math.round(mapWidthPixels * scaleX);
        int scaledHeight = Math.round(mapHeightPixels * scaleY);
        int bossRoomOffsetX = (ScreenManager.getScreenWidth() - scaledWidth) / 2;
        int bossRoomOffsetY = (ScreenManager.getScreenHeight() - scaledHeight) / 2;
        
        // screenX/screenY are in game area space (0-800, 0-600)
        // Subtract the boss room's centering offset to get coords relative to scaled map
        int adjustedX = screenX - bossRoomOffsetX;
        int adjustedY = screenY - bossRoomOffsetY;
        
        // Reverse the scale transformation to get coordinates in world space
        // Then add camera position (which should be 0,0 for full map view)
        float worldX = (adjustedX / scaleX) + (camera != null ? camera.getX() : 0);
        float worldY = (adjustedY / scaleY) + (camera != null ? camera.getY() : 0);
        
        return new float[] { worldX, worldY };
    }
    
    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        
        // Add the FinalBoss to the center of the room
        MapTile bossTile = getMapTile(12, 8); // Center of the room (24x20)
        if (bossTile != null) {
            FinalBoss boss = new FinalBoss(1, bossTile.getLocation().x, bossTile.getLocation().y);
        
            npcs.add(boss);
        }
        
        return npcs;
    }
    
    @Override
    public ArrayList<Trigger> loadTriggers() {
        ArrayList<Trigger> triggers = new ArrayList<>();
        triggers.add(new Trigger(550, 75, 40, 40, new DoorScript()));
        return triggers;
    
    }
    
    /**
     * Gets the scale factor for rendering the entire map
     */
    public float getScaleX() {
        return scaleX;
    }
    
    public float getScaleY() {
        return scaleY;
    }
}

