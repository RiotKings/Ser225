package Maps;

import Level.*;
import Tilesets.Tileset2;
import NPCs.Bug;
import NPCs.EnemyBasic;
import NPCs.Zombie;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor2Room17 extends Map {
    public Floor2Room17() {
        super("Floor2Room17", new Tileset2());
            this.playerStartPosition = new Point(325, 370);
             this.setEnemyCount(4);
    }

    @Override
    protected ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }

    @Override
    protected ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        
        MapTile zombieTile1 = getMapTile(3, 3);
        if (zombieTile1 != null) {
            npcs.add(new Zombie(1, zombieTile1.getLocation()));
        }
        MapTile zombieTile2 = getMapTile(12, 3);
        if (zombieTile2 != null) {
            npcs.add(new Zombie(1, zombieTile2.getLocation()));
        }
        MapTile zombieTile3 = getMapTile(3, 7);
        if (zombieTile3 != null) {
            npcs.add(new Zombie(1, zombieTile3.getLocation()));
        }
        MapTile zombieTile4 = getMapTile(12, 7);
        if (zombieTile4 != null) {
            npcs.add(new Zombie(1, zombieTile4.getLocation()));
        }
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