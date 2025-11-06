package Engine;


import GameObject.Rectangle;

/*
 * The game engine uses this class to start off the cascading Screen updating/drawing
 * The idea is an external class should be allowed to set its own Screen to this class's currentScreen variable,
 * and then that class can handle coordinating which Screen to show.
 */
public class ScreenManager {
    private Screen currentScreen;
    private static Rectangle screenBounds = new Rectangle(0, 0, 0, 0);
    private static int windowWidth = 0;
    private static int windowHeight = 0;

    public void initialize(Rectangle screenBounds) {
        ScreenManager.screenBounds = screenBounds;
        setCurrentScreen(new DefaultScreen());
    }

    /**
     * Updates screen bounds without resetting the current screen
     * This is useful when window is resized or enters/exits fullscreen
     */
    public void updateScreenBounds(Rectangle screenBounds) {
        ScreenManager.screenBounds = screenBounds;
    }
    
    /**
     * Updates the window dimensions for calculating screen offset
     */
    public static void setWindowDimensions(int width, int height) {
        windowWidth = width;
        windowHeight = height;
    }

    // attach an external Screen class here for the ScreenManager to start calling its update/draw cycles
    public void setCurrentScreen(Screen screen) {
        screen.initialize();
        this.currentScreen = screen;
    }

    public void update() {
        currentScreen.update();
    }

    public void draw(GraphicsHandler graphicsHandler) {
        currentScreen.draw(graphicsHandler);
    }

    // gets width of currentScreen -- can be called from anywhere in an application
    public static int getScreenWidth() {
        return screenBounds.getWidth();
    }

    // gets height of currentScreen -- can be called from anywhere in an application
    public static int getScreenHeight() {
        return screenBounds.getHeight();
    }
    
    /**
     * Gets the X offset needed to center the game content on screen
     */
    public static int getScreenOffsetX() {
        if (windowWidth <= 0) return 0;
        return (windowWidth - screenBounds.getWidth()) / 2;
    }
    
    /**
     * Gets the Y offset needed to center the game content on screen
     */
    public static int getScreenOffsetY() {
        if (windowHeight <= 0) return 0;
        return (windowHeight - screenBounds.getHeight()) / 2;
    }

    // gets bounds of currentScreen -- can be called from anywhere in an application
    public static Rectangle getScreenBounds() {
        return screenBounds;
    }
    
    public Screen getCurrentScreen() {
        return currentScreen;
    }
}
