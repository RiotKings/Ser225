package Maps;

import Level.*;
import Tilesets.CommonTileset;
import NPCs.Bug;
import NPCs.EnemyBasic;
import NPCs.Zombie;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor1Room13 extends Map {

    public Floor1Room13() {
        super("Floor1Room13.txt", new CommonTileset());

        MapTile center = getMapTile(10, 7);
        if (center != null) {
            this.playerStartPosition = center.getLocation();
        } else {
            MapTile fallback = getMapTile(2, 2);
            this.playerStartPosition = (fallback != null) ? fallback.getLocation() : new Point(0, 0);
        }
        this.setEnemyCount(5);
    }

    @Override
    protected ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        
         // Add Zombie NPC
        MapTile zombieTile0 = getMapTile(7, 1);
        if (zombieTile0 != null)
            npcs.add(new Zombie(1, zombieTile0.getLocation()));
        MapTile zombieTile01 = getMapTile(8, 1);
        if (zombieTile01 != null)
            npcs.add(new Zombie(1, zombieTile01.getLocation()));
        
            MapTile zombieTile2 = getMapTile(6, 1);
        if (zombieTile2 != null)
            npcs.add(new Zombie(1, zombieTile2.getLocation()));
        
            MapTile zombieTile3 = getMapTile(5, 1);
        if (zombieTile3 != null)
            npcs.add(new Zombie(1, zombieTile3.getLocation()));
        
            MapTile zombieTile4 = getMapTile(9, 1);
        if (zombieTile4 != null)
            npcs.add(new Zombie(1, zombieTile4.getLocation()));
        
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