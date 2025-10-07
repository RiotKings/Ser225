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

// This is the class for the main menu screen
public class MenuScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected int currentMenuItemHovered = 0; // current menu item being "hovered" over
    protected int menuItemSelected = -1;
    protected SpriteFont playGame;
    protected SpriteFont credits;
    protected Map background;
    protected int keyPressTimer;
    protected int pointerLocationX, pointerLocationY;
    protected KeyLocker keyLocker = new KeyLocker();

    // audio fields (new)
    private Clip bgmClip;
    private FloatControl bgmVolume;
    private int loopStartFrame = -1;
    private int loopEndFrame   = -1;

    public MenuScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
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

        // 🔊 start title music
        startTitleMusic();
    }

    public void update() {
        // update background map (to play tile animations)
        background.update(null);

        // if down or up is pressed, change menu item "hovered" over (blue square in front of text will move along with currentMenuItemHovered changing)
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

        // if down is pressed on last menu item or up is pressed on first menu item, "loop" the selection back around to the beginning/end
        if (currentMenuItemHovered > 1) {
            currentMenuItemHovered = 0;
        } else if (currentMenuItemHovered < 0) {
            currentMenuItemHovered = 1;
        }

        // sets location for blue square in front of text (pointerLocation) and also sets color of spritefont text based on which menu item is being hovered
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

        // if space is pressed on menu item, change to appropriate screen based on which menu item was chosen
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            menuItemSelected = currentMenuItemHovered;
            if (menuItemSelected == 0) {
                // 🔊 stop music before switching screens
                stopTitleMusic();
                screenCoordinator.setGameState(GameState.LEVEL);
            } else if (menuItemSelected == 1) {
                // 🔊 stop music before switching screens
                stopTitleMusic();
                screenCoordinator.setGameState(GameState.CREDITS);
            }
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        playGame.draw(graphicsHandler);
        credits.draw(graphicsHandler);
        graphicsHandler.drawFilledRectangleWithBorder(
            pointerLocationX, pointerLocationY, 20, 20,
            new Color(49, 207, 240), Color.black, 2
        );
    }

    // =========================
    // 🔊 music helpers (new)
    // =========================
    private void startTitleMusic() {
        try {
            // load WAV from Resources
            URL url = new File("Resources/title_theme.wav").toURI().toURL();
            AudioInputStream in = AudioSystem.getAudioInputStream(url);

            // make sure it's PCM-signed for Clip
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

            // 40-second loop
            float frameRate = decoded.getFrameRate();
            int totalFrames = (int) bgmClip.getFrameLength();
            int startMs = 0;        // change if you want to skip intro
            int endMs   = 40000;    // 40s
            loopStartFrame = Math.max(0, (int) (startMs / 1000f * frameRate));
            loopEndFrame   = Math.min(totalFrames - 1, (int) (endMs   / 1000f * frameRate));

            // optional volume
            if (bgmClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                bgmVolume = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
                // bgmVolume.setValue(-6.0f); // quieter if desired
            }

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
