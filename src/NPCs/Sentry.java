package NPCs;

import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import GameObject.Bullet;
import Level.MapEntityStatus;
import Level.NPC;
import Level.Player;

import GameObject.Frame;
import GameObject.SpriteSheet;
import GameObject.Frame;
import GameObject.ImageEffect;

public class Sentry extends NPC {

    private static final float STEP_DT = 1f / 60f;
    private static final float AttackRadius = 500f;    // range to detect player
    private static final float MuzzleOffset = 10f;
    private static final float BulletInterval = 0.6f;  // fast fire rate (seconds)
    private static final int Damage = 1;

    private int currentHealth = 8;  // decent HP
    private int maxHealth = 8;

    private float bulletCooldown = 0f;

    public Sentry(int id, float x, float y) {
        // choose appropriate sprite sheet size — adjust if your asset differs
        super(id, x, y, new SpriteSheet(ImageLoader.load("cat.png"), 24, 24), "STAND");
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(2)
                    .withBounds(0, 0, 24, 24)
                    .build()
            });
            put("FIRE", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 1), 6)
                    .withScale(2)
                    .withBounds(0, 0, 24, 24)
                    .build()
            });
        }};
    }

    @Override
    protected void performAction(Player player) {
        if (currentHealth <= 0) {
            this.setMapEntityStatus(MapEntityStatus.REMOVED);
            if (map != null) map.decreaseEnemyCount();
            return;
        }

        if (player == null || map == null) return;

        Rectangle sb = getBounds();
        Rectangle pb = player.getBounds();
        float sx = sb.getX() + sb.getWidth() * 0.5f;
        float sy = sb.getY() + sb.getHeight() * 0.5f;
        float px = pb.getX() + pb.getWidth() * 0.5f;
        float py = pb.getY() + pb.getHeight() * 0.5f;

        float dx = px - sx;
        float dy = py - sy;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        if (dist <= AttackRadius) {
            // aim at player and shoot
            bulletCooldown -= STEP_DT;
            if (bulletCooldown <= 0f) {
                float inv = (dist < 1e-5f) ? 0f : 1f / dist;
                float nx = dx * inv, ny = dy * inv;

                float bx = sx + nx * MuzzleOffset;
                float by = sy + ny * MuzzleOffset;

                Bullet bullet = new Bullet(1000, bx, by, nx, ny, Damage);
                bullet.setMap(this.map);
                map.addNPC(bullet);

                bulletCooldown = BulletInterval;
                this.setCurrentAnimationName("FIRE");
            } else {
                this.setCurrentAnimationName("STAND");
            }
        } else {
            // idle
            this.setCurrentAnimationName("STAND");
            bulletCooldown = Math.max(0f, bulletCooldown - STEP_DT);
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

    public int getHealth() { return currentHealth; }

    public void setHealth(int health) {
        this.currentHealth = Math.max(0, Math.min(health, maxHealth));
    }

    public void takeDamage(int damage) {
        setHealth(currentHealth - damage);
    }

    @Override
    public Rectangle getBounds() {
        Rectangle b = super.getBounds();
        if (b.getWidth() < 1 || b.getHeight() < 1) {
            return new Rectangle(b.getX1(), b.getY1(), 20, 20);
        }
        return b;
    }
}
