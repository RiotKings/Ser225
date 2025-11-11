package GameObject;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.Item;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.Player;

/**
 * SpeedBoots item — increases player movement speed when collected.
 */
public class firerate extends Item {

    public firerate(int id, float x, float y) {
        super(id, x, y, makeFrame());
    }

   private static Frame makeFrame() {
    return new FrameBuilder(ImageLoader.load("bulletfire.png"))
            .withScale(0.15f)
            .build();
    }

    @Override
    protected void onCollect(Player player) {
        if (!player.hasbulletfire()) {
            player.setHasbulletfire(true);
            System.out.println("Player collected firerate — speed increased!");
        }
    }
}
