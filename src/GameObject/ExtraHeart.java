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
public class ExtraHeart extends Item {

    public ExtraHeart(int id, float x, float y) {
        super(id, x, y, makeFrame());
    }

   private static Frame makeFrame() {
    return new FrameBuilder(ImageLoader.load("ExtraHeart.png"))
            .withScale(0.15f)
            .build();
}

    @Override
    protected void onCollect(Player player) {
        if (!player.hasExtraHeart()) {
            player.setHasExtraHeart(true);
            System.out.println("Player collected Extra Heart — Max Life Increased !");
        }
    }
}
