package Maps;

import Level.*;
import Tilesets.CommonTileset;
import Utils.Point;
import java.util.ArrayList;

public class TestMap extends Map {

    public TestMap() {
        super("test_map.txt", new CommonTileset());

        // Safe spawn setup
        MapTile spawn = getMapTile(2, 2);   // pick a valid tile inside your map
        if (spawn != null) {
            this.playerStartPosition = spawn.getLocation();
        } else {
            this.playerStartPosition = new Point(0, 0); // fallback so editor won't crash
        }
    }

    @Override
    protected ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }

    @Override
    protected ArrayList<NPC> loadNPCs() {
        // TEMP FIX: disable NPCs for Map Editor stability
        return new ArrayList<>();
    }

    @Override
    protected ArrayList<Trigger> loadTriggers() {
        // TEMP FIX: no triggers so editor doesn't crash
        return new ArrayList<>();
    }

    @Override
    protected void loadScripts() {
        // Add dialogue/triggers here later if needed
    }
}
