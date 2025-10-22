package Screens;

import Engine.*;
import SpriteFont.SpriteFont;

import java.awt.*;

// This class is for the Game Over level screen
public class GameOverScreen extends Screen {
    protected SpriteFont loseMessage;
    protected SpriteFont instructions;
    protected KeyLocker keyLocker = new KeyLocker();
    protected PlayLevelScreen playLevelScreen;
    private int alpha = 0;           // starts transparent (0 = invisible)
    private int fadeSpeed = 3;       // higher = faster fade (try 2–5)
    private boolean fadeComplete = false;   

    public GameOverScreen(PlayLevelScreen playLevelScreen) {
        this.playLevelScreen = playLevelScreen;
        initialize();
    }

    @Override
    public void initialize() {
        loseMessage = new SpriteFont("Game Over!", 350, 239, "Arial", 30, Color.white);
        instructions = new SpriteFont("Press Space to play again or Escape to go back to the main menu", 120, 279,"Arial", 20, Color.white);
        keyLocker.lockKey(Key.SPACE);
        keyLocker.lockKey(Key.ESC);
    }

    @Override
    public void update() {
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (Keyboard.isKeyUp(Key.ESC)) {
            keyLocker.unlockKey(Key.ESC);
        }

        // if space is pressed, reset level. if escape is pressed, go back to main menu
        if (Keyboard.isKeyDown(Key.SPACE) && !keyLocker.isKeyLocked(Key.SPACE)) {
            playLevelScreen.resetLevel();
        } else if (Keyboard.isKeyDown(Key.ESC) && !keyLocker.isKeyLocked(Key.ESC)) {
            playLevelScreen.goBackToMenu();
        }
        if (!fadeComplete) {
            alpha += fadeSpeed;
            if (alpha >= 255) { // stop at semi-transparent black background
                alpha = 255;
                fadeComplete = true;
            }
        }   
    }

@Override
public void draw(GraphicsHandler graphicsHandler) {
    // draw translucent background that fades in
    graphicsHandler.drawFilledRectangle(
        0,
        0,
        ScreenManager.getScreenWidth(),
        ScreenManager.getScreenHeight(),
        new Color(0, 0, 0, alpha)
    );

    // only show text when background has fully faded in
    if (fadeComplete) {
        loseMessage.draw(graphicsHandler);
        instructions.draw(graphicsHandler);
    }
    }
}