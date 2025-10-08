package Hud;

import Engine.GraphicsHandler;
import GameObject.GameObject;
import GameObject.ImageEffect;
import Engine.Mouse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Crosshair extends GameObject {
    private BufferedImage crosshairImage;
    private final Mouse mouse;

    public Crosshair(float x, float y) {
        super(x, y);
        try {
            // put your image here or switch to getResourceAsStream if you package resources
            crosshairImage = ImageIO.read(new File("Resources/crosshair.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mouse = Mouse.getInstance();
    }

    @Override
    public void update() {
        super.update(); // once, not twice
        if (mouse != null && crosshairImage != null) {
            int mouseX = mouse.getMouseX();
            int mouseY = mouse.getMouseY();
            // HUD uses SCREEN space, so store raw screen coords here
            this.x = mouseX - crosshairImage.getWidth() / 2f;
            this.y = mouseY - crosshairImage.getHeight() / 2f;
        }
    }

    @Override
    public void draw(GraphicsHandler g) {
        if (crosshairImage == null) return;
        // IMPORTANT: draw in SCREEN space — DO NOT use calibrated locations here
        g.drawImage(
            crosshairImage,
            Math.round(this.x),
            Math.round(this.y),
            crosshairImage.getWidth(),
            crosshairImage.getHeight(),
            ImageEffect.NONE
        );
    }
}
