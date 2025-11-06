package Maps;

import Level.*;
import Tilesets.CommonTileset;
import NPCs.Bug;
import NPCs.EnemyBasic;
import NPCs.Mine;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor1Room14 extends Map {

    public Floor1Room14() {
        super("Floor1Room14.txt", new CommonTileset());

        MapTile center = getMapTile(10, 7);
        if (center != null) {
            this.playerStartPosition = center.getLocation();
        } else {
            MapTile fallback = getMapTile(2, 2);
            this.playerStartPosition = (fallback != null) ? fallback.getLocation() : new Point(0, 0);
        }
        this.setEnemyCount(4);
    }

    @Override
    protected ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        
       // Add mine NPC
        MapTile mineTile = getMapTile(1, 4);
        if (mineTile != null) 
            npcs.add(new Mine(1, mineTile.getLocation()));
// Add mine NPC
        MapTile mineTile2 = getMapTile(12, 4);
        if (mineTile2 != null) 
            npcs.add(new Mine(1, mineTile2.getLocation()));
 // Add EnemyBasic NPC
        MapTile enemyTile0 = getMapTile(8, 2);  // Spawn at a different location
        if (enemyTile0 != null) {
            EnemyBasic enemy = new EnemyBasic(2, enemyTile0.getLocation().x, enemyTile0.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            enemy.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(enemy);
        }
         // Add EnemyBasic NPC
        MapTile enemyTile1 = getMapTile(6, 2);  // Spawn at a different location
        if (enemyTile1 != null) {
            EnemyBasic enemy = new EnemyBasic(2, enemyTile1.getLocation().x, enemyTile1.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            enemy.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(enemy);
        }
        
        return npcs;
    }

    @Override
    protected ArrayList<EnhancedMapTile> loadEnhancedMapTiles() { 
        return new ArrayList<>(); 
    }

     @Override
    public ArrayList<Trigger> loadTriggers() {
        ArrayList<Trigger> triggers = new ArrayList<>();
        triggers.add(new Trigger(340, 0, 40, 40, new DoorScript()));
        return triggers;
    
    }

    @Override
    protected void loadScripts() { }
}