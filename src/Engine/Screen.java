package Engine;

// Base Screen class
// This game engine runs off the idea of "screens", which are classes that contain their own update/draw methods for a particular piece of the game
// For example, there may be a "MenuScreen" or a "PlayGameScreen"
public abstract class Screen {
    public abstract void initialize();
    public abstract void update();
    public abstract void draw(GraphicsHandler graphicsHandler);
    
    /**
     * Called when the screen size changes (e.g., window resize, fullscreen toggle)
     * Override this method in screens that need to update their content for new screen dimensions
     */
    public void onScreenSizeChanged() {
        // Default implementation does nothing - screens can override if needed
    }
}
