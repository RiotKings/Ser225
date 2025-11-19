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

public class Floor2Room5 extends Map {
    public Floor2Room5() {
        super("Floor2Room5", new Tileset2());
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
        
       
        // Add mine NPC
        MapTile mineTile0 = getMapTile(8, 3);
        if (mineTile0 != null) 
            npcs.add(new Mine(1, mineTile0.getLocation()));
        
        // Add mine NPC
        MapTile mineTile1 = getMapTile(6, 3);
        if (mineTile1 != null) 
            npcs.add(new Mine(1, mineTile1.getLocation()));

        // Add Zombie NPC
        MapTile zombieTile0 = getMapTile(13, 1);
        if (zombieTile0 != null)
            npcs.add(new Zombie(1, zombieTile0.getLocation()));

        // Add Zombie NPC
        MapTile zombieTile1 = getMapTile(1, 1);
        if (zombieTile1 != null)
            npcs.add(new Zombie(1, zombieTile1.getLocation()));
        
        return npcs;
        
    }

     @Override
    public ArrayList<Trigger> loadTriggers() {
        ArrayList<Trigger> triggers = new ArrayList<>();
        triggers.add(new Trigger(340, 0, 40, 40, new DoorScript()));
        return triggers;
    
    }
    @Override
    protected void loadScripts() {
    }

}