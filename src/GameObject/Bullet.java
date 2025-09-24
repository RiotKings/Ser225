package GameObject;

import Level.MapEntity;
import Engine.GraphicsHandler;
import java.awt.Color;

public class Bullet extends MapEntity {
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

    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawFilledRectangle((int)x - 3, (int)y - 3, 6, 6, Color.RED);
    }
}