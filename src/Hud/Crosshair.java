package Hud;

import Engine.GraphicsHandler;
import java.awt.image.BufferedImage;
import GameObject.GameObject; // Import GameObject class
import Engine.Mouse;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Crosshair extends GameObject {

    private BufferedImage crosshairImage;
    private Mouse mouse;

    // Constructor
    public Crosshair(float x, float y,Mouse mouse) {
        super(x, y);
        this.mouse = mouse;

        // Load the crosshair image from the file
        try {
            crosshairImage = ImageIO.read(new File("resources/Crosshair.png")); 
        } catch (IOException e) {
            e.printStackTrace();  // Handle the exception if the image is not found
        }
    }

    // Override update method if necessary, for instance to follow the mouse
    @Override
    public void update() {
        super.update();

        if (mouse != null) {
            int mouseX = mouse.getMouseX();
            int mouseY = mouse.getMouseY();
            // Set the crosshair's position to the mouse position (centering it)
            this.x = mouseX - crosshairImage.getWidth() / 2;  // Center the crosshair on the mouse
            this.y = mouseY - crosshairImage.getHeight() / 2;
        }

        super.update();  // Call the super update method if necessary (for additional logic)
    }

    // Override the draw method to render the crosshair image
    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawImage(
            crosshairImage, 
            Math.round(getCalibratedXLocation()), 
            Math.round(getCalibratedYLocation()), 
            crosshairImage.getWidth(), 
            crosshairImage.getHeight(), 
            null
        );
    }

    // Optionally override other methods to customize behavior (e.g., collision detection if needed)
}
