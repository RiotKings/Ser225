package Maps;

import Level.*;
import Tilesets.CommonTileset;
import NPCs.Bug;
import NPCs.EnemyBasic;


import Utils.Point;
import java.util.ArrayList;

public class Floor1Room3 extends Map {
    public Floor1Room3() {
        super("Floor1Room3", new CommonTileset());
        this.playerStartPosition = new Point(5, 5);
    }
     @Override
    protected ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }

    @Override
    protected ArrayList<NPC> loadNPCs() {
      
          ArrayList<NPC> npcs = new ArrayList<>();
        
       // Add Bug NPC
        MapTile bugTile0 = getMapTile(3, 3);
        if (bugTile0 != null) 
            npcs.add(new Bug(1, bugTile0.getLocation()));
        // Add Bug NPC
        MapTile bugTile1 = getMapTile(8, 3);
        if (bugTile1 != null) 
            npcs.add(new Bug(1, bugTile1.getLocation()));

        // Add EnemyBasic NPC
        MapTile enemyTile1 = getMapTile(10, 7);  // Spawn at a different location
        if (enemyTile1 != null) {
            EnemyBasic enemy = new EnemyBasic(2, enemyTile1.getLocation().x, enemyTile1.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            enemy.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(enemy);
        
        return npcs;
        } 
        // TEMP FIX: disable NPCs for Map Editor stability
        return npcs;
        
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
