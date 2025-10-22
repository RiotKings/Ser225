package Maps;

import Level.Map;
import Level.MapTile;
import Level.NPC;
import Tilesets.CommonTileset;
import Utils.Point;

import java.util.ArrayList;

public class BossRoomMap extends Map {
    public BossRoomMap() {
        super("Floor1BossFloor.txt", new CommonTileset());
        
        // Set player spawn position in the boss room
        MapTile spawn = getMapTile(12, 18); // Bottom center of the room (24x20)
        if (spawn != null) {
            this.playerStartPosition = spawn.getLocation();
        } else {
            this.playerStartPosition = new Point(384, 576); // Fallback position for 24x20 room
        }
    }
    
    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        
        // No boss - empty room for free movement
        return npcs;
    }
}
