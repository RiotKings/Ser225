package Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HealthHUDdemo extends JPanel {
    private int currentHealth;
    private int maxHealth;
    private final int heartSize = 40;
    private final int heartSpacing = 10;
    
    public HealthHUDdemo(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        setPreferredSize(new Dimension(200, 60));
        setOpaque(false); // Transparent background
    }
    
    /**
     * Set the player's current health
     * @param health The new health value (will be clamped between 0 and maxHealth)
     */
    public void setHealth(int health) {
        this.currentHealth = Math.max(0, Math.min(health, maxHealth));
        repaint(); // Redraw the HUD
    }
    
    /**
     * Get the current health
     * @return Current health value
     */
    public int getHealth() {
        return currentHealth;
    }
    
    /**
     * Damage the player
     * @param amount Amount of damage to take
     */
    public void takeDamage(int amount) {
        setHealth(currentHealth - amount);
    }
    
    /**
     * Heal the player
     * @param amount Amount of health to restore
     */
    public void heal(int amount) {
        setHealth(currentHealth + amount);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Calculate hearts to display
        int fullHearts = currentHealth / 2;
        boolean hasHalfHeart = (currentHealth % 2) == 1;
        int totalHearts = (maxHealth + 1) / 2; // Round up for max hearts
        
        int x = 10;
        int y = 10;
        
        // Draw hearts
        for (int i = 0; i < totalHearts; i++) {
            if (i < fullHearts) {
                drawFullHeart(g2d, x, y);
            } else if (i == fullHearts && hasHalfHeart) {
                drawHalfHeart(g2d, x, y);
            } else {
                drawEmptyHeart(g2d, x, y);
            }
            x += heartSize + heartSpacing;
        }
    }
    
    private void drawFullHeart(Graphics2D g, int x, int y) {
        g.setColor(Color.RED);
        drawHeartShape(g, x, y, true);
    }
    
    private void drawHalfHeart(Graphics2D g, int x, int y) {
        // Draw empty heart first
        g.setColor(Color.DARK_GRAY);
        drawHeartShape(g, x, y, true);
        
        // Draw half heart on top
        g.setColor(Color.RED);
        Shape oldClip = g.getClip();
        g.setClip(x, y, heartSize / 2, heartSize);
        drawHeartShape(g, x, y, true);
        g.setClip(oldClip);
    }
    
    private void drawEmptyHeart(Graphics2D g, int x, int y) {
        g.setColor(Color.DARK_GRAY);
        drawHeartShape(g, x, y, true);
    }
    
    private void drawHeartShape(Graphics2D g, int x, int y, boolean fill) {
        // Create a heart shape using a polygon
        int[] xPoints = new int[13];
        int[] yPoints = new int[13];
        
        // Heart shape coordinates (scaled to heartSize)
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
        
        if (fill) {
            g.fillPolygon(xPoints, yPoints, 13);
        } else {
            g.drawPolygon(xPoints, yPoints, 13);
        }
        
        // Add border
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawPolygon(xPoints, yPoints, 13);
    }
    
    // Demo main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Health HUD Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            
            // Create HUD with 6 max health (3 hearts)
            HealthHUDdemo healthHUD = new HealthHUDdemo(6);
            healthHUD.setBackground(new Color(240, 240, 240));
            
            // Control panel
            JPanel controls = new JPanel();
            JButton damageBtn = new JButton("Take Damage (-1)");
            JButton healBtn = new JButton("Heal (+1)");
            JButton resetBtn = new JButton("Reset Health");
            JLabel healthLabel = new JLabel("Health: " + healthHUD.getHealth() + "/" + healthHUD.maxHealth);
            
            damageBtn.addActionListener(e -> {
                healthHUD.takeDamage(1);
                healthLabel.setText("Health: " + healthHUD.getHealth() + "/" + healthHUD.maxHealth);
            });
            
            healBtn.addActionListener(e -> {
                healthHUD.heal(1);
                healthLabel.setText("Health: " + healthHUD.getHealth() + "/" + healthHUD.maxHealth);
            });
            
            resetBtn.addActionListener(e -> {
                healthHUD.setHealth(healthHUD.maxHealth);
                healthLabel.setText("Health: " + healthHUD.getHealth() + "/" + healthHUD.maxHealth);
            });
            
            controls.add(damageBtn);
            controls.add(healBtn);
            controls.add(resetBtn);
            controls.add(healthLabel);
            
            frame.add(healthHUD, BorderLayout.NORTH);
            frame.add(controls, BorderLayout.SOUTH);
            
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}