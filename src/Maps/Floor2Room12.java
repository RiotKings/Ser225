package Maps;

import Level.*;
import Tilesets.Tileset2;
import NPCs.Bug;
import NPCs.EnemyBasic;
import NPCs.Mine;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor2Room12 extends Map {
    public Floor2Room12() {
        super("Floor2Room12", new Tileset2());
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

        MapTile enemyTile0 = getMapTile(11, 1);  // Spawn at a different location
        if (enemyTile0 != null) {
            EnemyBasic enemy = new EnemyBasic(2, enemyTile0.getLocation().x, enemyTile0.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            enemy.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(enemy);
        }
        
        MapTile enemyTile1 = getMapTile(4, 1);  // Spawn at a different location
        if (enemyTile1 != null) {
            EnemyBasic enemy1 = new EnemyBasic(2, enemyTile1.getLocation().x, enemyTile1.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            enemy1.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(enemy1);
        }

        MapTile mineTile = getMapTile(4, 5);
        if (mineTile != null) 
            npcs.add(new Mine(1, mineTile.getLocation()));

        MapTile mineTile1 = getMapTile(10, 5);
        if (mineTile1 != null) 
            npcs.add(new Mine(1, mineTile1.getLocation()));

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