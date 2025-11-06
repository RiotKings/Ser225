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
        player.activateShield(10000); // 10 seconds = 10000 milliseconds
        System.out.println("Player collected Shield — 10 seconds of invincibility activated!");
    }
}

