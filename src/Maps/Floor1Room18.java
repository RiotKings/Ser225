package Maps;

import Level.*;
import Tilesets.CommonTileset;
import NPCs.Bug;
import NPCs.EnemyBasic;
import NPCs.Mine;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor1Room18 extends Map {

    public Floor1Room18() {
        super("Floor1Room18.txt", new CommonTileset());

        MapTile center = getMapTile(10, 7);
        if (center != null) {
            this.playerStartPosition = center.getLocation();
        } else {
            MapTile fallback = getMapTile(2, 2);
            this.playerStartPosition = (fallback != null) ? fallback.getLocation() : new Point(0, 0);
        }
        this.setEnemyCount(3);
    }

    @Override
    protected ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        
        // Add Bug NPC
        MapTile bugTile = getMapTile(11, 7);
        if (bugTile != null) 
            npcs.add(new Bug(1, bugTile.getLocation()));
        
        // Add EnemyBasic NPC
        MapTile enemyTile0 = getMapTile(12, 2);  // Spawn at a different location
        if (enemyTile0 != null) {
            EnemyBasic enemy = new EnemyBasic(2, enemyTile0.getLocation().x, enemyTile0.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            enemy.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(enemy);
        }
         // Add mine NPC
        MapTile mineTile = getMapTile(3, 8);
        if (mineTile != null) 
            npcs.add(new Mine(1, mineTile.getLocation()));

        
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