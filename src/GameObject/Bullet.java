package GameObject;

import Level.MapEntity;
import Level.NPC;
import Engine.GraphicsHandler;
import java.awt.Color;
import GameObject.Rectangle;


public class Bullet extends NPC {
    private float vx, vy;
    private static final float SPEED = 180f;

    public Bullet(float x, float y, float dx, float dy) {
        super(x, y);
        float len = (float) Math.sqrt(dx * dx + dy * dy);
        if (len == 0) len = 1;
        this.vx = SPEED * dx / len;
        this.vy = SPEED * dy / len;
    }
    
    public void update(float dt) {
        x += vx * dt;
        y += vy * dt;    
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