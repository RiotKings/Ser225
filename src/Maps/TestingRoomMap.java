package Maps;

import Level.*;
import Tilesets.CommonTileset;
import NPCs.Bug;
import Utils.Point;
import java.util.ArrayList;

public class TestingRoomMap extends Map {

    public TestingRoomMap() {
        super("testing_room.txt", new CommonTileset());

        MapTile center = getMapTile(10, 7);
        if (center != null) {
            this.playerStartPosition = center.getLocation();
        } else {
            MapTile fallback = getMapTile(2, 2);
            this.playerStartPosition = (fallback != null) ? fallback.getLocation() : new Point(0, 0);
        }
    }

    @Override
    protected ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        MapTile bugTile = getMapTile(11, 7);   //  picks tile of the bug
        if (bugTile != null) 
            npcs.add(new Bug(1, bugTile.getLocation()));  //   actually creates the Bug
        return npcs;
    }


    @Override
    protected ArrayList<EnhancedMapTile> loadEnhancedMapTiles() { return new ArrayList<>(); }

    @Override
    protected void loadScripts() { }
}
