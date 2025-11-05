package Engine;

import Engine.GraphicsHandler;
import GameObject.Frame;
import GameObject.Rectangle;
import Level.MapEntityStatus;
import Level.NPC;
import Level.Player;

/**
 * Abstract base class for items the player can pick up.
 * - Formatted as an NPC (extends NPC)
 * - No animations (single Frame only)
 * - Disappears on contact with Player
 * - Flips a boolean 'collected' when picked up
 */
public abstract class Item extends NPC {

    protected boolean collected = false;

    public Item(int id, float x, float y, Frame frame) {
        // Use the NPC constructor that takes a single Frame, no animations
        super(id, x, y, frame);
        this.isUncollidable = false; // make sure it collides with the player
    }

    /**
     * Items don't do anything per-frame by default.
     * They just sit there until collected.
     */
    @Override
    protected void performAction(Player player) {
        // no movement / AI for items
    }

    /**
     * Called by the engine when the player collides with this NPC.
     * Here we:
     *  - mark as collected
     *  - call onCollect hook for subclasses
     *  - remove from map so it disappears
     */
    @Override
    public void touchedPlayer(Player player) {
        if (collected) return;  // already picked up, ignore

        collected = true;
        onCollect(player);                  // let subclass do something (heal, add key, etc.)
        this.mapEntityStatus = MapEntityStatus.REMOVED;  // remove from map
    }

    /**
     * Hook for subclasses to define what happens when the item is collected.
     * Example: player.heal(2), set a flag, increase score, etc.
     */
    protected abstract void onCollect(Player player);

    public boolean isCollected() {
        return collected;
    }

    /**
     * Only draw the item if it hasn't been collected yet.
     * Once collected, it visually disappears.
     */
    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        if (!collected) {
            super.draw(graphicsHandler);
        }
        // if collected, do nothing (invisible)
    }

    /**
     * Optional: you can override bounds if you want a different hitbox.
     * Otherwise, it uses the default from MapEntity/NPC's Frame.
     */
    @Override
    public Rectangle getBounds() {
        return super.getBounds();
    }
}
