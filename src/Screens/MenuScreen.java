package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;

import java.awt.*;

import javax.sound.sampled.*;
import java.io.File;
import java.net.URL;

public class MenuScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected int currentMenuItemHovered = 0;
    protected int menuItemSelected = -1;
    protected SpriteFont title;
    protected SpriteFont playGame;
    protected SpriteFont credits;
    protected Map background;
    protected int keyPressTimer;
    protected int pointerLocationX, pointerLocationY;
    protected KeyLocker keyLocker = new KeyLocker();

    private Clip bgmClip;
    private int loopStartFrame = -1;
    private int loopEndFrame   = -1;

    public MenuScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        title = new SpriteFont("SUMMONS OF CTHULHU", 175, 50, "Arial", 35, Color.white);
        title.setOutlineColor(Color.black);
        title.setOutlineThickness(3);
        playGame = new SpriteFont("PLAY GAME", 200, 123, "Arial", 30, new Color(49, 207, 240));
        playGame.setOutlineColor(Color.black);
        playGame.setOutlineThickness(3);
        credits = new SpriteFont("CREDITS", 200, 223, "Arial", 30, new Color(49, 207, 240));
        credits.setOutlineColor(Color.black);
        credits.setOutlineThickness(3);
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        keyPressTimer = 0;
        menuItemSelected = -1;
        keyLocker.lockKey(Key.SPACE);
        keyLocker.lockKey(Key.ENTER);          // <-- ADDED (prevents accidental auto-press)
        startTitleMusic();
    }

    public void update() {
        background.update(null);

        if (Keyboard.isKeyDown(Key.DOWN) && keyPressTimer == 0) {
            keyPressTimer = 14;
            currentMenuItemHovered++;
        } else if (Keyboard.isKeyDown(Key.UP) && keyPressTimer == 0) {
            keyPressTimer = 14;
            currentMenuItemHovered--;
        } else {
            if (keyPressTimer > 0) {
                keyPressTimer--;
            }
        }

        if (currentMenuItemHovered > 1) {
            currentMenuItemHovered = 0;
        } else if (currentMenuItemHovered < 0) {
            currentMenuItemHovered = 1;
        }

        if (currentMenuItemHovered == 0) {
            playGame.setColor(new Color(255, 215, 0));
            credits.setColor(new Color(49, 207, 240));
            pointerLocationX = 170;
            pointerLocationY = 130;
        } else if (currentMenuItemHovered == 1) {
            playGame.setColor(new Color(49, 207, 240));
            credits.setColor(new Color(255, 215, 0));
            pointerLocationX = 170;
            pointerLocationY = 230;
        }

        // unlock keys when released
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (Keyboard.isKeyUp(Key.ENTER)) {      // <-- ADDED
            keyLocker.unlockKey(Key.ENTER);
        }

        // SPACE selects
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            menuItemSelected = currentMenuItemHovered;
            if (menuItemSelected == 0) {
                stopTitleMusic();
                screenCoordinator.setGameState(GameState.LEVEL);
            } else if (menuItemSelected == 1) {
                stopTitleMusic();
                screenCoordinator.setGameState(GameState.CREDITS);
            }
        }

        // ENTER selects (same behavior as SPACE)  <-- ADDED
        if (!keyLocker.isKeyLocked(Key.ENTER) && Keyboard.isKeyDown(Key.ENTER)) {
            menuItemSelected = currentMenuItemHovered;
            if (menuItemSelected == 0) {
                stopTitleMusic();
                screenCoordinator.setGameState(GameState.LEVEL);
            } else if (menuItemSelected == 1) {
                stopTitleMusic();
                screenCoordinator.setGameState(GameState.CREDITS);
            }
            keyLocker.lockKey(Key.ENTER);
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        title.draw(graphicsHandler);
        playGame.draw(graphicsHandler);
        credits.draw(graphicsHandler);
        graphicsHandler.drawFilledRectangleWithBorder(
            pointerLocationX, pointerLocationY, 20, 20,
            new Color(49, 207, 240), Color.black, 2
        );
    }

    @Override
    public void onScreenSizeChanged() {
        // Update background map camera when screen size changes (e.g., fullscreen)
        if (background != null) {
            background.updateScreenSize();
        }
    }

    private void startTitleMusic() {
        try {
            URL url = new File("Resources/title_theme.wav").toURI().toURL();
            AudioInputStream in = AudioSystem.getAudioInputStream(url);

            AudioFormat base = in.getFormat();
            AudioFormat decoded = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    base.getSampleRate(),
                    16,
                    base.getChannels(),
                    base.getChannels() * 2,
                    base.getSampleRate(),
                    false
            );
            AudioInputStream din = AudioSystem.getAudioInputStream(decoded, in);

            bgmClip = AudioSystem.getClip();
            bgmClip.open(din);

            float frameRate = decoded.getFrameRate();
            int totalFrames = (int) bgmClip.getFrameLength();
            int startMs = 0;
            int endMs   = 40000;
            loopStartFrame = Math.max(0, (int) (startMs / 1000f * frameRate));
            loopEndFrame   = Math.min(totalFrames - 1, (int) (endMs   / 1000f * frameRate));

            bgmClip.setLoopPoints(loopStartFrame, loopEndFrame);
            bgmClip.setFramePosition(loopStartFrame);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopTitleMusic() {
        try {
            if (bgmClip != null) {
                bgmClip.stop();
                bgmClip.flush();
                bgmClip.close();
            }
        } catch (Exception ignored) {}
        bgmClip = null;
    }
}
