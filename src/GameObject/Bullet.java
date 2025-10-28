package GameObject;

import Level.MapEntityStatus;
import Level.NPC;
import Level.Player;
import Engine.GraphicsHandler;
import java.awt.Color;

public class Bullet extends NPC {
  

    private float vx, vy;
    private static final float Speed = 5;
    private static final int BulletSize = 10;
    private static final float Tm = 5;

    private final int damage;

    private static final float STEP_DT = 1 / 60;

    private boolean markedForRemoval = false;
    private float t = Tm;

    public Bullet(int id, float x, float y, float nx, float ny, int damage) {
        super(id, x, y);
        float len = (float) Math.sqrt(nx * nx + ny * ny);
        if (len < 1e-6f) { nx = 1f; ny = 0f; }
        else { nx /= len; ny /= len; }

        this.vx = Speed * nx;
        this.vy = Speed * ny;
        this.damage = damage;
    }

    @Override
    public void update(Player player) {
        if (markedForRemoval) return;

        //System.out.println(vx);
        //System.out.println(vy); 

        x += vx;
        y += vy;

        t -= STEP_DT;
/*
        if (t <= 0f) {
            markedForRemoval = true;
            this.mapEntityStatus = MapEntityStatus.REMOVED;
            return;
        }
*/

        if (map != null) {
            int w = map.getWidthPixels();
            int h = map.getHeightPixels();
            final int Margin = 6;
            float cx = x;
            float cy = y;
            if (cx < -Margin || cx > (w + Margin) || cy < -Margin || cy > (h + Margin)) {
                markedForRemoval = true;
                this.mapEntityStatus = MapEntityStatus.REMOVED;
                return;
            }
        }

        if (player != null) {
            Rectangle br = getBounds();
            Rectangle pr = player.getBounds();
            boolean hit = (br.getX1() < pr.getX1() + pr.getWidth()) && (br.getX1() + br.getWidth() > pr.getX1()) && (br.getY1() < pr.getY1() + pr.getHeight()) && (br.getY1() + br.getHeight() > pr.getY1());
            if (hit) {
                System.out.println("Hit player for " + damage + " damage!");
                player.takeDamage(damage);
                markedForRemoval = true;
                this.mapEntityStatus = MapEntityStatus.REMOVED;
            }
        }
    }

    @Override
    public void draw(GraphicsHandler g) {
        g.drawFilledRectangle(Math.round(getCalibratedXLocation()), Math.round(getCalibratedYLocation()), BulletSize, BulletSize, Color.RED);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x - 3, y - 3, 6, 6);
    }

    public boolean isMarkedForRemoval() { 
        return markedForRemoval; 
    }
}
