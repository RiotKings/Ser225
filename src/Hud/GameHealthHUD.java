package Hud;

import Engine.GraphicsHandler;
import Level.Player;
import java.awt.*;

/**
 * Health HUD specifically designed for your game engine
 * Displays player health as hearts in the top-left corner
 */
public class GameHealthHUD {
    private Player player;
    private final int heartSize = 30;
    private final int heartSpacing = 8;
    private final int xOffset = 15;  // Distance from left edge
    private final int yOffset = 15;  // Distance from top edge
    
    public GameHealthHUD(Player player) {
        this.player = player;
    }
    
    /**
     * Draw the health HUD
     * Call this in your PlayLevelScreen's draw method
     */
    public void draw(GraphicsHandler graphicsHandler) {
        Graphics2D g = graphicsHandler.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int currentHealth = player.getHealth();
        int maxHealth = player.getMaxHealth();
        
        // Calculate hearts to display
        int fullHearts = currentHealth / 2;
        boolean hasHalfHeart = (currentHealth % 2) == 1;
        int totalHearts = (maxHealth + 1) / 2; // Round up for max hearts
        
        int x = xOffset;
        int y = yOffset;
        
        // Draw hearts
        for (int i = 0; i < totalHearts; i++) {
            if (i < fullHearts) {
                drawFullHeart(g, x, y);
            } else if (i == fullHearts && hasHalfHeart) {
                drawHalfHeart(g, x, y);
            } else {
                drawEmptyHeart(g, x, y);
            }
            x += heartSize + heartSpacing;
        }
    }
    
    private void drawFullHeart(Graphics2D g, int x, int y) {
        g.setColor(Color.RED);
        drawHeartShape(g, x, y);
        drawHeartBorder(g, x, y);
    }
    
    private void drawHalfHeart(Graphics2D g, int x, int y) {
        // Draw empty heart first
        g.setColor(new Color(80, 80, 80));
        drawHeartShape(g, x, y);
        
        // Draw half heart on top
        g.setColor(Color.RED);
        Shape oldClip = g.getClip();
        g.setClip(x, y, heartSize / 2, heartSize);
        drawHeartShape(g, x, y);
        g.setClip(oldClip);
        
        drawHeartBorder(g, x, y);
    }
    
    private void drawEmptyHeart(Graphics2D g, int x, int y) {
        g.setColor(new Color(80, 80, 80));
        drawHeartShape(g, x, y);
        drawHeartBorder(g, x, y);
    }
    
    private void drawHeartShape(Graphics2D g, int x, int y) {
        int[] xPoints = new int[13];
        int[] yPoints = new int[13];
        
        double scale = heartSize / 40.0;
        
        xPoints[0] = x + (int)(20 * scale);  yPoints[0] = y + (int)(35 * scale);
        xPoints[1] = x + (int)(10 * scale);  yPoints[1] = y + (int)(25 * scale);
        xPoints[2] = x + (int)(5 * scale);   yPoints[2] = y + (int)(20 * scale);
        xPoints[3] = x + (int)(5 * scale);   yPoints[3] = y + (int)(12 * scale);
        xPoints[4] = x + (int)(10 * scale);  yPoints[4] = y + (int)(5 * scale);
        xPoints[5] = x + (int)(15 * scale);  yPoints[5] = y + (int)(5 * scale);
        xPoints[6] = x + (int)(20 * scale);  yPoints[6] = y + (int)(10 * scale);
        xPoints[7] = x + (int)(25 * scale);  yPoints[7] = y + (int)(5 * scale);
        xPoints[8] = x + (int)(30 * scale);  yPoints[8] = y + (int)(5 * scale);
        xPoints[9] = x + (int)(35 * scale);  yPoints[9] = y + (int)(12 * scale);
        xPoints[10] = x + (int)(35 * scale); yPoints[10] = y + (int)(20 * scale);
        xPoints[11] = x + (int)(30 * scale); yPoints[11] = y + (int)(25 * scale);
        xPoints[12] = x + (int)(20 * scale); yPoints[12] = y + (int)(35 * scale);
        
        g.fillPolygon(xPoints, yPoints, 13);
    }
    
    private void drawHeartBorder(Graphics2D g, int x, int y) {
        int[] xPoints = new int[13];
        int[] yPoints = new int[13];
        
        double scale = heartSize / 40.0;
        
        xPoints[0] = x + (int)(20 * scale);  yPoints[0] = y + (int)(35 * scale);
        xPoints[1] = x + (int)(10 * scale);  yPoints[1] = y + (int)(25 * scale);
        xPoints[2] = x + (int)(5 * scale);   yPoints[2] = y + (int)(20 * scale);
        xPoints[3] = x + (int)(5 * scale);   yPoints[3] = y + (int)(12 * scale);
        xPoints[4] = x + (int)(10 * scale);  yPoints[4] = y + (int)(5 * scale);
        xPoints[5] = x + (int)(15 * scale);  yPoints[5] = y + (int)(5 * scale);
        xPoints[6] = x + (int)(20 * scale);  yPoints[6] = y + (int)(10 * scale);
        xPoints[7] = x + (int)(25 * scale);  yPoints[7] = y + (int)(5 * scale);
        xPoints[8] = x + (int)(30 * scale);  yPoints[8] = y + (int)(5 * scale);
        xPoints[9] = x + (int)(35 * scale);  yPoints[9] = y + (int)(12 * scale);
        xPoints[10] = x + (int)(35 * scale); yPoints[10] = y + (int)(20 * scale);
        xPoints[11] = x + (int)(30 * scale); yPoints[11] = y + (int)(25 * scale);
        xPoints[12] = x + (int)(20 * scale); yPoints[12] = y + (int)(35 * scale);
        
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawPolygon(xPoints, yPoints, 13);
    }
}