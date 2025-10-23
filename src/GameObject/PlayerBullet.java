package GameObject;

import Level.MapEntityStatus;
import Level.NPC;
import Level.Player;
import NPCs.Bug;
import NPCs.EnemyBasic;
import Engine.GraphicsHandler;
import java.awt.Color;
import GameObject.Rectangle;

public class PlayerBullet extends NPC {
 

    private float vx, vy;
    private static final float Speed = 3;
    private static final int BulletSize = 4;
    private static final float Tm = 3;

    private final int damage;

    private static final float STEP_DT = 1 / 60;

    private boolean markedForRemoval = false;
    private float t = Tm;

    public PlayerBullet(int id, float x, float y, float nx, float ny, int damage) {
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

        t -= STEP_DT;

        if (t <= 0f) {
            markedForRemoval = true;
            this.mapEntityStatus = MapEntityStatus.REMOVED;
            return;
        }

        //System.out.println(vx);
        //System.out.println(vy);

        x += vx;
        y += vy;
/*
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
*/
        if (map != null) {
            Rectangle br = getBounds();
            var npcs = map.getNPCs();
            for (int i = npcs.size() - 1; i >= 0; i--) {
                NPC npc = npcs.get(i);
                if (npc.getMapEntityStatus() == MapEntityStatus.REMOVED) continue;

                if (npc instanceof EnemyBasic enemy){
                    Rectangle er = enemy.getBounds();

                    final float PAD_X = 4f, PAD_UP = 18f, PAD_DOWN = 2f;

                    boolean hit = (br.getX1() < er.getX1() + er.getWidth() + PAD_X * 2) && (br.getX1() + br.getWidth() > er.getX1() - PAD_X) && (br.getY1() < er.getY1() + er.getHeight() + PAD_UP + PAD_DOWN) && (br.getY1() + br.getHeight() > er.getY1() - PAD_UP);
                    if (hit) {
                        enemy.takeDamage(damage);
                        System.out.println("Hit enemy for " + damage + " damage!");
                        markedForRemoval = true;
                        this.mapEntityStatus = MapEntityStatus.REMOVED;
                        break;
                    }
                }
                else if (npc instanceof Bug bug) {
                    if (bug.getMapEntityStatus() == MapEntityStatus.REMOVED) continue;

                    Rectangle bugr = bug.getBounds();

                    final float PAD_X1 = 4f, PAD_UP1 = 18f, PAD_DOWN1 = 2f;

                    boolean hitbug = (br.getX1() < bugr.getX1() + bugr.getWidth() + PAD_X1 * 2) && (br.getX1() + br.getWidth() > bugr.getX1() - PAD_X1) && (br.getY1() < bugr.getY1() + bugr.getHeight() + PAD_UP1 + PAD_DOWN1) && (br.getY1() + br.getHeight() > bugr.getY1() - PAD_UP1);
                    if (hitbug) {
                        bug.takeDamage(damage);
                        System.out.println("Hit bug for " + damage + " damage!");
                        markedForRemoval = true;
                        this.mapEntityStatus = MapEntityStatus.REMOVED;
                        break;
                    }
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
        g.drawFilledRectangle(Math.round(getCalibratedXLocation()), Math.round(getCalibratedYLocation()), BulletSize, BulletSize, Color.YELLOW);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x - 3, y - 3, 6, 6);
    }

    public boolean isMarkedForRemoval() {
        return markedForRemoval;
    }
}
