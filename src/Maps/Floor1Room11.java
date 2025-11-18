package Maps;

import Level.*;
import Tilesets.CommonTileset;
import NPCs.Bug;
import NPCs.EnemyBasic;
import NPCs.Mine;
import NPCs.Zombie;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor1Room11 extends Map {

    public Floor1Room11() {
        super("Floor1Room11.txt", new CommonTileset());

        this.playerStartPosition = new Point(325, 370);
        this.setEnemyCount(3);
    }

    @Override
    protected ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        
       // Add Zombie NPC
        MapTile zombieTile0 = getMapTile(7, 1);
        if (zombieTile0 != null)
            npcs.add(new Zombie(1, zombieTile0.getLocation()));
            
         // Add mine NPC
        MapTile mineTile = getMapTile(2, 4);
        if (mineTile != null) 
            npcs.add(new Mine(1, mineTile.getLocation()));
         // Add mine NPC
        MapTile mineTile2 = getMapTile(11, 4);
        if (mineTile != null) 
            npcs.add(new Mine(1, mineTile2.getLocation()));

        
        
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