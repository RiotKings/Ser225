package Maps;

import Level.Map;
import Level.MapTile;
import Level.NPC;
import Tilesets.CommonTileset;
import Utils.Point;
import NPCs.FloorBoss;

import java.util.ArrayList;

public class Floor1BossRoomMap extends Map {
    public Floor1BossRoomMap() {
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
        
        // Add the FloorBoss to the center of the room
        MapTile bossTile = getMapTile(12, 8); // Center of the room (24x20)
        if (bossTile != null) {
            FloorBoss boss = new FloorBoss(1, bossTile.getLocation().x, bossTile.getLocation().y);
        
            npcs.add(boss);
        }
        
        return npcs;
    }
}
