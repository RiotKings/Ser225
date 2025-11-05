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
public class SpeedBoots extends Item {

    public SpeedBoots(int id, float x, float y) {
        super(id, x, y, makeFrame());
    }

    // Single-frame (no animation)
    private static Frame makeFrame() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.load("SpeedBoots.png"), 16, 16);
        return new FrameBuilder(sheet.getSprite(0, 0))
                .withScale(2)
                .build();
    }

    @Override
    protected void onCollect(Player player) {
        if (!player.hasSpeedBoots()) {
            player.setHasSpeedBoots(true);
            System.out.println("Player collected SpeedBoots — speed increased!");
        }
    }
}
