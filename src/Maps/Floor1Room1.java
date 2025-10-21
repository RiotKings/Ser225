package Maps;


import Level.*;
import Tilesets.CommonTileset;
import NPCs.Bug;
import NPCs.EnemyBasic;
import Utils.Point;
import java.util.ArrayList;

public class Floor1Room1 extends Map {
    public Floor1Room1() {
        super("Floor1Room1", new CommonTileset());
        this.playerStartPosition = new Point(7, 8);
    }
    @Override
    protected ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }

    @Override
    protected ArrayList<NPC> loadNPCs() {
      
          ArrayList<NPC> npcs = new ArrayList<>();
        
       // Add Bug NPC
        MapTile bugTile = getMapTile(1, 4);
        if (bugTile != null) 
            npcs.add(new Bug(1, bugTile.getLocation()));
        
        
      
        // Add EnemyBasic NPC
        MapTile enemyTile1 = getMapTile(10, 7);  // Spawn at a different location
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
