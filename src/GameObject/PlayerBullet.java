package GameObject;

import Level.MapEntityStatus;
import Level.NPC;
import Level.Player;
import NPCs.EnemyBasic;
import Engine.GraphicsHandler;
import java.awt.Color;
import GameObject.Rectangle;

public class PlayerBullet extends NPC {
  

    private float vx, vy;
    private static final float SPEED = 3f;
    private static final float BULLET_SIZE = 10f;
    private static final float Tm = 6.0f;

    private final int damage;

    private static final float STEP_DT = 1f / 60f;

    private boolean markedForRemoval = false;
    private float t = Tm;

    public PlayerBullet(int id, float x, float y, float nx, float ny, int damage) {
        super(id, x, y);
        float len = (float) Math.sqrt(nx * nx + ny * ny);
        if (len < 1e-6f) { nx = 1f; ny = 0f; }
        else { nx /= len; ny /= len; }

        this.vx = SPEED * nx;
        this.vy = SPEED * ny;
        this.damage = damage;
    }

    @Override
    public void update(Player player) {
        if (markedForRemoval) return;

        //System.out.println(vx);
        //System.out.println(vy); 

        x += vx;
        y += vy;

        if (t <= 0f) {
            markedForRemoval = true;
            return;
        }

        if (map != null) {
            int w = map.getWidthPixels();
            int h = map.getHeightPixels();
            final int Margin = 6;
            float cx = x;
            float cy = y;
            if (cx < -Margin || cx > (w + Margin) || cy < -Margin || cy > (h + Margin)) {
                markedForRemoval = true;
                return;
            }
        }

        if (map != null) {
            Rectangle br = getBounds();
            var npcs = map.getNPCs();
            for (int i = npcs.size() - 1; i >= 0; i--) {
                NPC npc = npcs.get(i);
                if (!(npc instanceof EnemyBasic enemy)) continue;
                if (enemy.getMapEntityStatus() == MapEntityStatus.REMOVED) continue;

                Rectangle er = enemy.getBounds();
                boolean hit = (br.getX1() < er.getX1() + er.getWidth()) && (br.getX1() + br.getWidth() > er.getX1()) && (br.getY1() < er.getY1() + er.getHeight()) && (br.getY1() + br.getHeight() > er.getY1());
                if (hit) {
                    enemy.takeDamage(damage);
                    System.out.println("Hit enemy for " + damage + " damage!");
                    markedForRemoval = true;
                    this.mapEntityStatus = MapEntityStatus.REMOVED;
                    break;
                }
            }
        }
    }

    @Override
    public void draw(GraphicsHandler g) {
        float sx = x;
        float sy = y;
        if (map != null && map.getCamera() != null) {
            sx -= map.getCamera().getX();
            sy -= map.getCamera().getY();
        }
        g.drawFilledRectangle(Math.round(getCalibratedXLocation()), Math.round(getCalibratedYLocation()), 7, 7, Color.YELLOW);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x - 3, y - 3, 6, 6);
    }

    public boolean isMarkedForRemoval() { 
        return markedForRemoval; 
    }
}