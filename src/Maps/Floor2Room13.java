package Maps;

import Level.*;
import Tilesets.Tileset2;
import NPCs.Bug;
import NPCs.EnemyBasic;
import NPCs.Mine;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor2Room13 extends Map {
    public Floor2Room13() {
        super("Floor2Room13", new Tileset2());
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

        MapTile mineTile = getMapTile(4, 2);
        if (mineTile != null) 
            npcs.add(new Mine(1, mineTile.getLocation()));
        
        MapTile mineTile1 = getMapTile(10, 2);
        if (mineTile1 != null) 
            npcs.add(new Mine(1, mineTile1.getLocation()));

        MapTile enemyTile0 = getMapTile(13, 4);  // Spawn at a different location
        if (enemyTile0 != null) {
            EnemyBasic enemy0 = new EnemyBasic(2, enemyTile0.getLocation().x, enemyTile0.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            enemy0.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(enemy0);
        }

        MapTile enemyTile01 = getMapTile(1, 4);  // Spawn at a different location
        if (enemyTile01 != null) {
            EnemyBasic enemy1 = new EnemyBasic(2, enemyTile01.getLocation().x, enemyTile01.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            enemy1.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(enemy1);
        }

        MapTile enemyTile02 = getMapTile(7, 4);  // Spawn at a different location
        if (enemyTile02 != null) {
            EnemyBasic enemy2 = new EnemyBasic(2, enemyTile02.getLocation().x, enemyTile02.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            enemy2.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(enemy2);
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