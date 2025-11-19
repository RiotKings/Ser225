package Maps;

import Level.*;
import Tilesets.Tileset2;
import NPCs.Bug;
import NPCs.EnemyBasic;
import NPCs.Zombie;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor2Room0 extends Map {
    public Floor2Room0() {
        super("Floor2Room0", new Tileset2());
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
        
        // Add EnemyBasic NPC
        MapTile enemyTile1 = getMapTile(2, 3);  // Spawn at a different location
        if (enemyTile1 != null) {
            EnemyBasic enemy = new EnemyBasic(2, enemyTile1.getLocation().x, enemyTile1.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            enemy.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(enemy);
        }
        MapTile enemyTile2 = getMapTile(10, 3);  // Spawn at a different location
        if (enemyTile2 != null) {
            EnemyBasic enemy = new EnemyBasic(2, enemyTile2.getLocation().x, enemyTile2.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            enemy.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(enemy);

            // Add Zombie NPC
        MapTile zombieTile0 = getMapTile(7, 4);
        if (zombieTile0 != null)
            npcs.add(new Zombie(1, zombieTile0.getLocation()));
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