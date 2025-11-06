package Engine;

import Engine.GraphicsHandler;
import GameObject.Frame;
import GameObject.Rectangle;
import Level.MapEntityStatus;
import Level.NPC;
import Level.Player;

public abstract class Item extends NPC {

    public Item(int id, float x, float y, Frame frame) {
        super(id, x, y, frame);

        // IMPORTANT:
        //  - We don't want this to act like a solid obstacle.
        //  - We'll do our own overlap check with the player (like bullets do).
        this.isUncollidable = true;
    }

    // We don't use performAction for items
    @Override
    protected void performAction(Player player) {
        // no AI / movement by default
    }

    // Do our own collision with the player, like Bullet / PlayerBullet
    @Override
    public void update(Player player) {
        // If we've already been removed, don't do anything
        if (this.mapEntityStatus == MapEntityStatus.REMOVED) {
            return;
        }

        if (player != null) {
            Rectangle ir = getBounds();
            Rectangle pr = player.getBounds();

            boolean overlaps =
                    ir.getX1() < pr.getX1() + pr.getWidth() &&
                    ir.getX1() + ir.getWidth() > pr.getX1() &&
                    ir.getY1() < pr.getY1() + pr.getHeight() &&
                    ir.getY1() + ir.getHeight() > pr.getY1();

            if (overlaps) {
                // Tell subclass to apply its effect (update player boolean, etc.)
                onCollect(player);

                // Remove this item from the map so it disappears
                this.mapEntityStatus = MapEntityStatus.REMOVED;
                return;
            }
        }

        // Still let MapEntity handle animation/camera stuff
        super.update();
    }

    // Subclasses implement what happens on pickup (set player booleans here)
    protected abstract void onCollect(Player player);

    @Override
    public void draw(GraphicsHandler g) {
        super.draw(g);
    }

    @Override
    public Rectangle getBounds() {
        return super.getBounds();
    }
}
