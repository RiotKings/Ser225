package Engine;

import java.awt.image.BufferedImage;
import java.awt.*;

/**
 * Represents an item that can exist in game
 * Items can be collected, have different types, and display sprites
 */
public class Item {
    protected float x, y;
    protected int width, height;
    protected BufferedImage image;
    protected Rectangle bounds;
    protected ItemType itemType;
    protected String name;
    protected String description;
    protected boolean isCollected;
    protected int value; 
    
    // Constructor for items with default sprite
    public Item(float x, float y, ItemType itemType, String name) {
        this.x = x;
        this.y = y;
        this.itemType = itemType;
        this.name = name;
        this.description = "";
        this.isCollected = false;
        this.value = itemType.getDefaultValue();
        
        // Load default sprite based on item type
        this.image = ImageLoader.load(itemType.getSpritePath());
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.bounds = new Rectangle((int)x, (int)y, width, height);
    }
    
    // Constructor for items with custom sprite
    public Item(float x, float y, ItemType itemType, String name, String spritePath) {
        this.x = x;
        this.y = y;
        this.itemType = itemType;
        this.name = name;
        this.description = "";
        this.isCollected = false;
        this.value = itemType.getDefaultValue();
        
        this.image = ImageLoader.load(spritePath);
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.bounds = new Rectangle((int)x, (int)y, width, height);
    }
    
    // Constructor with all parameters
    public Item(float x, float y, ItemType itemType, String name, String description, int value, String spritePath) {
        this.x = x;
        this.y = y;
        this.itemType = itemType;
        this.name = name;
        this.description = description;
        this.isCollected = false;
        this.value = value;
        
        this.image = ImageLoader.load(spritePath);
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.bounds = new Rectangle((int)x, (int)y, width, height);
    }
    
    public void update() {
        // Items typically don't need to update unless they animate or move
        // Override this in specific item subclasses if needed
        if (isCollected) {
            // Potential collection animation here
        }
    }
    
    public void draw(GraphicsHandler graphicsHandler) {
        
        if (!isCollected && image != null) {
            graphicsHandler.drawImage(image, (int)x, (int)y);
        }
    }
    
    // Method to handle item collection
    public void collect() {
        this.isCollected = true;
        onCollected();
    }
    
    // Override this method in subclasses to add specific collection effects
    protected void onCollected() {
        // Default behavior - could play sound, add particles, etc.
        System.out.println(name + " collected!");
    }
    
    // Check if this item collides with another object
    public boolean checkCollision(float otherX, float otherY, int otherWidth, int otherHeight) {
        if (isCollected) return false;
        
        updateBounds();
        Rectangle otherBounds = new Rectangle((int)otherX, (int)otherY, otherWidth, otherHeight);
        return bounds.intersects(otherBounds);
    }
    
    // Update bounds based on current position
    private void updateBounds() {
        bounds.x = (int)x;
        bounds.y = (int)y;
    }
    
    // Get the bounds of this item
    public Rectangle getBounds() {
        updateBounds();
        return bounds;
    }
    
    // Getters and Setters
    public ItemType getItemType() {
        return itemType;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isCollected() {
        return isCollected;
    }
    
    public void setCollected(boolean collected) {
        this.isCollected = collected;
    }
    
    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    // Position getters and setters
    public float getX() {
        return x;
    }
    
    public void setX(float x) {
        this.x = x;
    }
    
    public float getY() {
        return y;
    }
    
    public void setY(float y) {
        this.y = y;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
}

// Enum to define different types of items
enum ItemType {
    COIN("coin.png", 10),
    HEALTH_POTION("health_potion.png", 25),
    KEY("key.png", 0),
    POWER_UP("power_up.png", 50),
    WEAPON("weapon.png", 100),
    COLLECTIBLE("collectible.png", 20);
    
    private final String spritePath;
    private final int defaultValue;
    
    ItemType(String spritePath, int defaultValue) {
        this.spritePath = spritePath;
        this.defaultValue = defaultValue;
    }
    
    public String getSpritePath() {
        return spritePath;
    }
    
    public int getDefaultValue() {
        return defaultValue;
    }
}