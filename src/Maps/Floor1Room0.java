package Maps;

import Level.*;
import Tilesets.CommonTileset;

import NPCs.EnemyBasic;
import Utils.Point;
import java.util.ArrayList;

public class Floor1Room0 extends Map {
    public Floor1Room0() {
        super("Floor1Room0", new CommonTileset());
        this.playerStartPosition = new Point(7, 8);
    }

    @Override
    protected ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }

    @Override
    protected ArrayList<NPC> loadNPCs() {
      
          ArrayList<NPC> npcs = new ArrayList<>();
        
       
        
        // Add EnemyBasic NPC
        MapTile enemyTile0 = getMapTile(12, 2);  // Spawn at a different location
        if (enemyTile0 != null) {
            EnemyBasic enemy = new EnemyBasic(2, enemyTile0.getLocation().x, enemyTile0.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            enemy.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(enemy);
        }

        // Add EnemyBasic NPC
        MapTile enemyTile1 = getMapTile(1, 7);  // Spawn at a different location
        if (enemyTile1 != null) {
            EnemyBasic enemy = new EnemyBasic(2, enemyTile1.getLocation().x, enemyTile1.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            enemy.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(enemy);
        }
        
        return npcs;
        // TEMP FIX: disable NPCs for Map Editor stability
        
    }

    @Override
    protected ArrayList<Trigger> loadTriggers() {
        // TEMP FIX: no triggers so editor doesn't crash
        return new ArrayList<>();
    }

    @Override
    protected void loadScripts() {
        // Add dialogue/triggers here later if needed
    }

}