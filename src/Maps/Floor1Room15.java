package Maps;

import Level.*;
import Tilesets.CommonTileset;
import NPCs.Bug;
import NPCs.EnemyBasic;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor1Room15 extends Map {

    public Floor1Room15() {
        super("Floor1Room15.txt", new CommonTileset());

        this.playerStartPosition = new Point(325, 370);
        this.setEnemyCount(2);
    }

    @Override
    protected ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        
        // Add Bug NPC
        MapTile bugTile = getMapTile(11, 7);
        if (bugTile != null) 
            npcs.add(new Bug(1, bugTile.getLocation()));
        // Add Bug NPC
        MapTile bugTile2 = getMapTile(3, 3);
        if (bugTile != null) 
            npcs.add(new Bug(1, bugTile2.getLocation()));
        
        
        
        
        return npcs;
    }

    @Override
    protected ArrayList<EnhancedMapTile> loadEnhancedMapTiles() { 
        return new ArrayList<>(); 
    }

     @Override
    public ArrayList<Trigger> loadTriggers() {
        ArrayList<Trigger> triggers = new ArrayList<>();
        triggers.add(new Trigger(340, 0, 40, 40, new DoorScript()));
        return triggers;
    
    }

    @Override
    protected void loadScripts() { }
}