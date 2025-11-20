package Maps;

import Level.*;
import Tilesets.Tileset2;
import NPCs.Bug;
import NPCs.EnemyBasic;
import NPCs.Zombie;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor2Room24 extends Map {
    public Floor2Room24() {
        super("Floor2Room24", new Tileset2());
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
        
        MapTile enemyTile0 = getMapTile(7, 4);
        if (enemyTile0 != null) {
            EnemyBasic enemy = new EnemyBasic(2, enemyTile0.getLocation().x, enemyTile0.getLocation().y);
            enemy.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(enemy);
        }

        MapTile bugTile1 = getMapTile(2, 2);
        if (bugTile1 != null) {
            npcs.add(new Bug(1, bugTile1.getLocation()));
        }

        MapTile zombieTile1 = getMapTile(10, 5);
        if (zombieTile1 != null) {
            npcs.add(new Zombie(1, zombieTile1.getLocation()));
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