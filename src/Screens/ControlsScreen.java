package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;

import java.awt.*;

// Screen that shows the control instructions
public class ControlsScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected KeyLocker keyLocker = new KeyLocker();
    protected SpriteFont controlsLabel;
    protected SpriteFont moveLabel;
    protected SpriteFont aimShootLabel;
    protected SpriteFont dodgeLabel;
    protected SpriteFont returnInstructionsLabel;

    public ControlsScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        background = new TitleScreenMap();
        background.setAdjustCamera(false);

        controlsLabel = new SpriteFont("Controls", 15, 6, "Times New Roman", 30, Color.white);
        moveLabel = new SpriteFont("Move: W A S D", 150, 140, "Times New Roman", 22, Color.white);
        aimShootLabel = new SpriteFont("Aim & Shoot: Click to shoot, cursor for aiming", 150, 180, "Times New Roman", 22, Color.white);
        dodgeLabel = new SpriteFont("Dodge: Space Bar", 150, 220, "Times New Roman", 22, Color.white);
        returnInstructionsLabel = new SpriteFont("Press Space to return to the menu", 20, 532, "Times New Roman", 30, Color.white);

        keyLocker.lockKey(Key.SPACE);
    }

    @Override
    public void update() {
        background.update(null);

        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        controlsLabel.draw(graphicsHandler);
        moveLabel.draw(graphicsHandler);
        aimShootLabel.draw(graphicsHandler);
        dodgeLabel.draw(graphicsHandler);
        returnInstructionsLabel.draw(graphicsHandler);
    }

    @Override
    public void onScreenSizeChanged() {
        if (background != null) {
            background.updateScreenSize();
        }
    }
}



