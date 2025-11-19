package Maps;

import Level.*;
import Tilesets.Tileset2;
import NPCs.Bug;
import NPCs.EnemyBasic;
import NPCs.Mine;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor2Room2 extends Map {
    public Floor2Room2() {
        super("Floor2Room2", new Tileset2());
            this.playerStartPosition = new Point(325, 370);
             this.setEnemyCount(5);
    }

    @Override
    protected ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }

    @Override
    protected ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
         // Add Bug NPC
        MapTile bugTile = getMapTile(1, 1);
        if (bugTile != null) 
            npcs.add(new Bug(1, bugTile.getLocation()));
         // Add Bug NPC
        MapTile bugTile1 = getMapTile(13, 1);
        if (bugTile1 != null) 
            npcs.add(new Bug(1, bugTile1.getLocation()));
         // Add Bug NPC
        MapTile bugTile2 = getMapTile(1, 8);
        if (bugTile2 != null) 
            npcs.add(new Bug(1, bugTile2.getLocation()));
         // Add Bug NPC
        MapTile bugTile3 = getMapTile(13, 8);
        if (bugTile3 != null) 
            npcs.add(new Bug(1, bugTile3.getLocation()));

           // Add mine NPC
        MapTile mineTile = getMapTile(7, 2);
        if (mineTile != null) 
            npcs.add(new Mine(1, mineTile.getLocation()));
       
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