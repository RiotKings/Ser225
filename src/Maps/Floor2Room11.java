package Maps;

import Level.*;
import Tilesets.Tileset2;
import NPCs.Bug;
import NPCs.EnemyBasic;
import NPCs.Mine;
import NPCs.Zombie;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor2Room11 extends Map {
    public Floor2Room11() {
        super("Floor2Room11", new Tileset2());
            this.playerStartPosition = new Point(325, 370);
             this.setEnemyCount(3);
    }

    @Override
    protected ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }

    @Override
    protected ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        MapTile mineTile = getMapTile(1, 1);
        if (mineTile != null) 
            npcs.add(new Mine(1, mineTile.getLocation()));
        
        MapTile mineTile1 = getMapTile(1, 8);
        if (mineTile1 != null) 
            npcs.add(new Mine(1, mineTile1.getLocation()));
        
        MapTile mineTile2 = getMapTile(13, 1);
        if (mineTile2 != null) 
            npcs.add(new Mine(1, mineTile2.getLocation()));
        
        MapTile mineTile3 = getMapTile(13, 8);
        if (mineTile3 != null) 
            npcs.add(new Mine(1, mineTile3.getLocation()));
        
        MapTile zombieTile0 = getMapTile(7, 4);
        if (zombieTile0 != null) 
            npcs.add(new Zombie(1, zombieTile0.getLocation()));
        
        MapTile zombieTile1 = getMapTile(6, 4);
        if (zombieTile1 != null) 
            npcs.add(new Zombie(1, zombieTile1.getLocation()));
        
       
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