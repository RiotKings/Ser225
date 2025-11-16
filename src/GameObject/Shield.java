package GameObject;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.Item;
import GameObject.Frame;
import Level.Player;

/**
 * Shield item — grants player 10 seconds of invincibility when collected.
 */
public class Shield extends Item {

    public Shield(int id, float x, float y) {
        super(id, x, y, makeFrame());
    }

   private static Frame makeFrame() {
    return new FrameBuilder(ImageLoader.load("shield.png"))
            .withScale(0.15f)
            .build();
}

    @Override
    protected void onCollect(Player player) {
        // Grant the player a shield that will block the next instance of damage
        // in every room (one block per room).
        player.activateShield();
        System.out.println("Player collected Shield — it will block the next hit in each room!");
    }
}

