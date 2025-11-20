package Maps;

import Level.*;
import Tilesets.Tileset2;
import NPCs.Bug;
import NPCs.EnemyBasic;
import NPCs.Mine;
import NPCs.Zombie;
import NPCs.Sentry;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor2Room20 extends Map {
    public Floor2Room20() {
        super("Floor2Room20", new Tileset2());
            this.playerStartPosition = new Point(325, 370);
             this.setEnemyCount(2);
    }

    @Override
    protected ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }

    @Override
    protected ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        
       

        MapTile zombieTile1 = getMapTile(10, 2);
        if (zombieTile1 != null) {
            npcs.add(new Zombie(1, zombieTile1.getLocation()));
        }

        MapTile mineTile1 = getMapTile(8, 5);
        if (mineTile1 != null) {
            npcs.add(new Mine(1, mineTile1.getLocation()));
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