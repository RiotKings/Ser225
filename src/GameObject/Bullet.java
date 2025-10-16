package GameObject;

import Level.MapEntity;
import Engine.GraphicsHandler;
import java.awt.Color;
import Level.Player;
import Players.Alex;


public class Bullet extends MapEntity {
    private float vx, vy;
    private static final float SPEED = 180f;
    private boolean active = true;

    private Player player;

    public Bullet(float x, float y, float dx, float dy) {
        super(x, y);
        float len = (float) Math.sqrt(dx * dx + dy * dy);
        if (len == 0) len = 1;
        this.vx = SPEED * dx / len;
        this.vy = SPEED * dy / len;

        this.player = player; // store reference for collision
    }
    
    public void update(float dt) {
        // Move bullet
        x += vx * dt;
        y += vy * dt;

        // --- Collision detection with player ---
        if (player != null && checkCollisionWithPlayer()) {
            if (!player.isDodging()) {
            ((Alex) player).takeDamage(5); 
            this.active = false;
        }
            // else: bullet passes through (no removal)
        }
    }

    private boolean checkCollisionWithPlayer() {
        
        float playerX = player.getX();
        float playerY = player.getY();
        float playerWidth = player.getWidth();
        float playerHeight = player.getHeight();

        float bulletSize = 6; // since drawn as 6x6 rectangle

        return (x < playerX + playerWidth &&
                x + bulletSize > playerX &&
                y < playerY + playerHeight &&
                y + bulletSize > playerY);
    }

    @Override
    public void draw(GraphicsHandler g) {
        float sx = x;
        float sy = y;
        if (map != null && map.getCamera() != null) {
            sx -= map.getCamera().getX();
            sy -= map.getCamera().getY();
        }
        g.drawFilledRectangle(Math.round(sx) - 3, Math.round(sy) - 3, 6, 6, Color.RED);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x - 3, y - 3, 6, 6);
    }

    
}