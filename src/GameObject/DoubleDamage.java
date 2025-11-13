package GameObject;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.Item;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.Player;
import Level.MapEntityStatus;
import java.util.HashMap;

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