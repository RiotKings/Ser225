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

import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.lang.reflect.Method;

public class Sentry extends NPC {

    private static final float STEP_DT = 1f / 60f;
    private static final float AttackRadius = 1000f;
    private static final float MuzzleOffset = 10f;
    private static final float BulletInterval = 0.7f;
    private static final int Damage = 1;

    private int currentHealth = 6;
    private int maxHealth = 6;

    private float bulletCooldown = 0f;

    private final BufferedImage baseImage;
    private double lastAngle = 0.0;

    public Sentry(int id, float x, float y) {
        super(id, x, y, new SpriteSheet(ImageLoader.load("Sentry.png"), 47, 47), "STAND");
        this.baseImage = ImageLoader.load("Sentry.png");
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(2)
                    .withBounds(0, 0, 47, 47)
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

            float inv = (dist < 1e-5f) ? 0f : 1f / dist;
            float nx = dx * inv, ny = dy * inv;
            lastAngle = Math.atan2(ny, nx);

            bulletCooldown -= STEP_DT;
            if (bulletCooldown <= 0f) {
                float bx = sx + nx * MuzzleOffset;
                float by = sy + ny * MuzzleOffset;

                Bullet bullet = new Bullet(1000, bx, by, nx, ny, Damage);
                bullet.setMap(this.map);
                map.addNPC(bullet);

                bulletCooldown = BulletInterval;
            }
            this.setCurrentAnimationName("STAND");
        } else {
            bulletCooldown = Math.max(0f, bulletCooldown - STEP_DT);
            this.setCurrentAnimationName("STAND");
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        if (baseImage == null) {
            super.draw(graphicsHandler);
            return;
        }
        try {
            Method m = graphicsHandler.getClass().getMethod("getGraphics");
            Object raw = m.invoke(graphicsHandler);
            if (raw instanceof Graphics2D) {
                Graphics2D g2 = (Graphics2D) raw;
                AffineTransform old = g2.getTransform();

                Rectangle b = getBounds();
                double cx = b.getX() + b.getWidth() * 0.5;
                double cy = b.getY() + b.getHeight() * 0.5;

                g2.translate(cx, cy);
                g2.rotate(lastAngle);

                int iw = baseImage.getWidth();
                int ih = baseImage.getHeight();
                g2.drawImage(baseImage, -iw / 2, -ih / 2, null);

                g2.setTransform(old);
                return;
            }
        } catch (Exception ignored) {
        }

        super.draw(graphicsHandler);
    }

    public int getHealth() { return currentHealth; }

    public void setHealth(int health) {
        this.currentHealth = Math.max(0, Math.min(health, maxHealth));
    }

    public void takeDamage(int damage) {
        setHealth(currentHealth - damage);
    }

    public void setBounds(float left, float top, float right, float bottom) {
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