package Maps;

import Level.*;
import Tilesets.CommonTileset;
import NPCs.Bug;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor1Room2 extends Map {
    public Floor1Room2() {
        super("Floor1Room2", new CommonTileset());
        this.playerStartPosition = new Point(325, 370);
        this.setEnemyCount(8);
    }
     @Override
    protected ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }

    @Override
    protected ArrayList<NPC> loadNPCs() {
      
          ArrayList<NPC> npcs = new ArrayList<>();
        
       // Add Bug NPC
        MapTile bugTile0 = getMapTile(4, 2);
        if (bugTile0 != null) 
            npcs.add(new Bug(1, bugTile0.getLocation()));
        // Add Bug NPC
        MapTile bugTile1 = getMapTile(4, 6);
        if (bugTile1 != null) 
            npcs.add(new Bug(1, bugTile1.getLocation()));
        // Add Bug NPC
        MapTile bugTile2 = getMapTile(10, 2);
        if (bugTile2 != null) 
            npcs.add(new Bug(1, bugTile2.getLocation()));
        // Add Bug NPC
        MapTile bugTile3 = getMapTile(10, 6);
        if (bugTile3 != null) 
            npcs.add(new Bug(1, bugTile3.getLocation()));
        
        return npcs;
        // TEMP FIX: disable NPCs for Map Editor stability
        
    }

     @Override
    public ArrayList<Trigger> loadTriggers() {
        ArrayList<Trigger> triggers = new ArrayList<>();
        triggers.add(new Trigger(340, 0, 40, 40, new DoorScript()));
        return triggers;
    
    }

    @Override
    protected void loadScripts() {
        // Add dialogue/triggers here later if needed
    }

}

