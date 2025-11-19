package GameObject;

import Level.MapEntityStatus;
import Level.NPC;
import Level.Player;
import NPCs.Bug;
import NPCs.Zombie;
import NPCs.Mine;
import NPCs.Sentry;
import NPCs.EnemyBasic;
import NPCs.FloorBoss;
import NPCs.FinalBoss;
import Engine.GraphicsHandler;
import java.awt.Color;
import GameObject.Rectangle;
import Utils.Direction;

public class PlayerBullet extends NPC {
 

    private float vx, vy;
    private static final float Speed = 5;
    private static final int BulletSize = 10;
    private static final float Tm = 3;

    private final int damage;

    private static final float STEP_DT = 1 / 60;

    private boolean markedForRemoval = false;
    private float t = Tm;

    public PlayerBullet(int id, float x, float y, float nx, float ny, int damage) {
        super(id, x, y);
        
        this.isUncollidable = true;
        
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

        if (map != null) {
            Rectangle br = getBounds();
            var npcs = map.getNPCs();
            for (int i = npcs.size() - 1; i >= 0; i--) {
                NPC npc = npcs.get(i);
                if (npc.getMapEntityStatus() == MapEntityStatus.REMOVED) continue;

                if (npc instanceof EnemyBasic enemy){
                    Rectangle er = enemy.getBounds();

                    final float PAD_X = 4f, PAD_UP = 30, PAD_DOWN = 2f;

                    boolean hit = (br.getX1() < er.getX1() + er.getWidth() + PAD_X) && (br.getX1() + br.getWidth() > er.getX1() - PAD_X) && br.getY1() < (er.getY1() + er.getHeight() + PAD_UP) && (br.getY1() + br.getHeight() > er.getY1() - PAD_UP);
                    if (hit) {
                        enemy.takeDamage(damage);
                        //System.out.println("Hit enemy for " + damage + " damage!");
                        markedForRemoval = true;
                        this.mapEntityStatus = MapEntityStatus.REMOVED;
                        break;
                    }
                }
                else if (npc instanceof Bug bug) {
                    if (bug.getMapEntityStatus() == MapEntityStatus.REMOVED) {
                        continue;
                    }
                    Rectangle bugr = bug.getBounds();

                    final float PAD_X1 = 4f, PAD_UP1 = 6f, PAD_DOWN1 = 2f;

                    boolean hitbug = (br.getX1() < bugr.getX1() + bugr.getWidth() + PAD_X1 * 2) && (br.getX1() + br.getWidth() > bugr.getX1() - PAD_X1) && (br.getY1() < bugr.getY1() + bugr.getHeight() + PAD_UP1 + PAD_DOWN1) && (br.getY1() + br.getHeight() > bugr.getY1() - PAD_UP1);
                    if (hitbug) {
                        bug.takeDamage(damage);
                        //System.out.println("Hit bug for " + damage + " damage!");
                        markedForRemoval = true;
                        this.mapEntityStatus = MapEntityStatus.REMOVED;
                        break;
                    }
                }
                else if (npc instanceof FloorBoss floorBoss) {
                    if (floorBoss.getMapEntityStatus() == MapEntityStatus.REMOVED) continue;

                    Rectangle bossr = floorBoss.getBounds();

                    final float PAD_X1 = 0f, PAD_UP1 = 0f, PAD_DOWN1 = 0f;

                    boolean hitbug = (br.getX1() < bossr.getX1() + bossr.getWidth() + PAD_X1 * 2) && (br.getX1() + br.getWidth() > bossr.getX1() - PAD_X1) && (br.getY1() < bossr.getY1() + bossr.getHeight() + PAD_UP1 + PAD_DOWN1) && (br.getY1() + br.getHeight() > bossr.getY1() - PAD_UP1);
                    if (hitbug) {
                        floorBoss.takeDamage(damage);
                        //System.out.println("Hit boss for " + damage + " damage!");
                        markedForRemoval = true;
                        this.mapEntityStatus = MapEntityStatus.REMOVED;
                        break;
                    }
                }
                else if (npc instanceof FinalBoss finalBoss) {
                    if (finalBoss.getMapEntityStatus() == MapEntityStatus.REMOVED) continue;

                    Rectangle bossr = finalBoss.getBounds();

                    final float PAD_X1 = 0f, PAD_UP1 = 0f, PAD_DOWN1 = 0f;

                    boolean hitBoss = (br.getX1() < bossr.getX1() + bossr.getWidth() + PAD_X1 * 2) && (br.getX1() + br.getWidth() > bossr.getX1() - PAD_X1) && (br.getY1() < bossr.getY1() + bossr.getHeight() + PAD_UP1 + PAD_DOWN1) && (br.getY1() + br.getHeight() > bossr.getY1() - PAD_UP1);
                    if (hitBoss) {
                        finalBoss.takeDamage(damage);
                        System.out.println("Hit Final Boss for " + damage + " damage!");
                        markedForRemoval = true;
                        this.mapEntityStatus = MapEntityStatus.REMOVED;
                        break;
                    }
                }
                else if (npc instanceof Zombie zombie){
                    if (zombie.getMapEntityStatus() == MapEntityStatus.REMOVED) continue;

                    Rectangle zombier = zombie.getBounds();

                    final float PAD_X1 = 3f, PAD_UP1 = 2f, PAD_DOWN1 = 1f;

                    boolean hitzombie = (br.getX1() < zombier.getX1() + zombier.getWidth() + PAD_X1 * 2) && (br.getX1() + br.getWidth() > zombier.getX1() - PAD_X1) && (br.getY1() < zombier.getY1() + zombier.getHeight() + PAD_UP1 + PAD_DOWN1) && (br.getY1() + br.getHeight() > zombier.getY1() - PAD_UP1);
                    if (hitzombie){
                        zombie.takeDamage(damage);

                        markedForRemoval = true;
                        this.mapEntityStatus = MapEntityStatus.REMOVED;
                        break;
                    }
                }
                else if (npc instanceof Mine mine){
                    if (mine.getMapEntityStatus() == MapEntityStatus.REMOVED) continue;

                    Rectangle miner = mine.getBounds();

                    final float PAD_X1 = 6f, PAD_UP1 = 30f, PAD_DOWN1 = 3f;

                    boolean hitmine = (br.getX1() < miner.getX1() + miner.getWidth() + PAD_X1 * 2) && (br.getX1() + br.getWidth() > miner.getX1() - PAD_X1) && (br.getY1() < miner.getY1() + miner.getHeight() + PAD_UP1 + PAD_DOWN1) && (br.getY1() + br.getHeight() > miner.getY1() - PAD_UP1);
                    if (hitmine){
                        mine.takeDamage(damage);

                        markedForRemoval = true;
                        this.mapEntityStatus = MapEntityStatus.REMOVED;
                        break;
                    }
                }
                else if (npc instanceof Sentry sentry) {
                    if (sentry.getMapEntityStatus() == MapEntityStatus.REMOVED) continue;

                    Rectangle sentryr = sentry.getBounds();

                    final float PAD_X1 = 6f, PAD_UP1 = 30f, PAD_DOWN1 = 3f;

                    boolean hitsentry = (br.getX1() < sentryr.getX1() + sentryr.getWidth() + PAD_X1 * 2) && (br.getX1() + br.getWidth() > sentryr.getX1() - PAD_X1) && (br.getY1() < sentryr.getY1() + sentryr.getHeight() + PAD_UP1 + PAD_DOWN1) && (br.getY1() + br.getHeight() > sentryr.getY1() - PAD_UP1);
                    if (hitsentry){
                        sentry.takeDamage(damage);

                        markedForRemoval = true;
                        this.mapEntityStatus = MapEntityStatus.REMOVED;
                        break;
                    }
                }
            }
        }
    }
    
    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction, GameObject entityCollidedWith) {
        if (entityCollidedWith instanceof Player) {
            return;
        }
        super.onEndCollisionCheckX(hasCollided, direction, entityCollidedWith);
    }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction, GameObject entityCollidedWith) {
        if (entityCollidedWith instanceof Player) {
            return;
        }
        super.onEndCollisionCheckY(hasCollided, direction, entityCollidedWith);
    }

    @Override
    public void touchedPlayer(Player player) {
        // PlayerBullets should NOT interact with player
        // Do nothing - bullets pass through player
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