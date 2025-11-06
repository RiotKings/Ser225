package GameObject;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.Item;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.Player;
import Level.MapEntityStatus;
import java.util.HashMap;

/*/
public class DoubleDamage extends Item {
    public DoubleDamage(float x, float y) {
        super(x, y, new SpriteSheet(ImageLoader.load("DoubleDamage.png"), 16, 16), "DEFAULT");
    }

    @Override
    public void update(Player player) {
        // Check if player is colliding with item
        if (player.intersects(this)) {
            // Apply double damage effect
            player.setDamageMultiplier(2.0f);
            // Remove item from map
            this.setMapEntityStatus(MapEntityStatus.REMOVED);
            System.out.println("Double Damage collected!");
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(3)
                    .withBounds(1, 1, 14, 14)
                    .build()
            });
        }};
    }
}
*/

public class DoubleDamage extends Item {

    public DoubleDamage(int id, float x, float y) {
        super(id, x, y, makeFrame());
    }

   private static Frame makeFrame() {
        return new FrameBuilder(ImageLoader.load("DoubleDamage.png"))
        .withScale(0.1f)
        .build();
    }

    @Override
    protected void onCollect(Player player) {
        if (!player.hasDoubleDamage()) {
            player.setHasDoubleDamage(true);
            System.out.println("Player collected Double Damage — Damage Increased !");
        }
    }
}