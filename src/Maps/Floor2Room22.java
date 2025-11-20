package Maps;

import Level.*;
import Tilesets.Tileset2;
import NPCs.Bug;
import NPCs.EnemyBasic;
import NPCs.Zombie;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor2Room22 extends Map {
    public Floor2Room22() {
        super("Floor2Room22", new Tileset2());
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
        
        MapTile zombieTile1 = getMapTile(4, 2);
        if (zombieTile1 != null) {
            npcs.add(new Zombie(1, zombieTile1.getLocation()));
        }

        MapTile zombieTile2 = getMapTile(9, 4);
        if (zombieTile2 != null) {
            npcs.add(new Zombie(1, zombieTile2.getLocation()));
        }

        MapTile bugTile1 = getMapTile(12, 3);
        if (bugTile1 != null) {
            npcs.add(new Bug(1, bugTile1.getLocation()));
        }
       
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
        // Add dialogue/triggers here later if needed
    }
}