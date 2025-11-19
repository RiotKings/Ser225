package Maps;

import Level.*;
import Tilesets.Tileset2;
import NPCs.Bug;
import NPCs.EnemyBasic;
import NPCs.Mine;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor2Room6 extends Map {
    public Floor2Room6() {
        super("Floor2Room6", new Tileset2());
            this.playerStartPosition = new Point(325, 370);
             this.setEnemyCount(4);
    }

    @Override
    protected ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }

    @Override
    protected ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        // Add Bug NPC
        MapTile bugTile0 = getMapTile(6, 3);
        if (bugTile0 != null) 
            npcs.add(new Bug(1, bugTile0.getLocation()));
        // Add Bug NPC
        MapTile bugTile1 = getMapTile(10, 3);
        if (bugTile1 != null) 
            npcs.add(new Bug(1, bugTile1.getLocation()));
        
        // Add EnemyBasic NPC
        MapTile enemyTile = getMapTile(11, 3);  // Spawn at a different location
        if (enemyTile != null) {
            EnemyBasic enemy = new EnemyBasic(2, enemyTile.getLocation().x, enemyTile.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            enemy.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(enemy);
        }

        // Add mine NPC
        MapTile mineTile = getMapTile(8, 3);
        if (mineTile != null) 
            npcs.add(new Mine(1, mineTile.getLocation()));
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
    }

}