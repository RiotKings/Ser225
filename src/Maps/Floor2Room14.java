package Maps;

import Level.*;
import Tilesets.Tileset2;
import NPCs.Bug;
import NPCs.EnemyBasic;
import NPCs.Mine;
import NPCs.Zombie;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor2Room14 extends Map {
    public Floor2Room14() {
        super("Floor2Room14", new Tileset2());
            this.playerStartPosition = new Point(325, 370);
             this.setEnemyCount(6);
    }

    @Override
    protected ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }

    @Override
    protected ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        MapTile mineTile = getMapTile(7, 4);
        if (mineTile != null) 
            npcs.add(new Mine(1, mineTile.getLocation()));
        
        MapTile mineTile1 = getMapTile(7, 5);
        if (mineTile1 != null) 
            npcs.add(new Mine(1, mineTile1.getLocation()));

        MapTile zombieTile0 = getMapTile(10, 4);
        if (zombieTile0 != null) 
            npcs.add(new Zombie(1, zombieTile0.getLocation()));
        
        MapTile zombieTile1 = getMapTile(11, 7);
        if (zombieTile1 != null) 
            npcs.add(new Zombie(1, zombieTile1.getLocation()));
        
        MapTile enemyTile1 = getMapTile(1, 6);  // Spawn at a different location
        if (enemyTile1 != null) {
            EnemyBasic enemy1 = new EnemyBasic(2, enemyTile1.getLocation().x, enemyTile1.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            enemy1.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(enemy1);
        }

        MapTile enemyTile2 = getMapTile(4, 2);  // Spawn at a different location
        if (enemyTile2 != null) {
            EnemyBasic enemy2 = new EnemyBasic(2, enemyTile2.getLocation().x, enemyTile2.getLocation().y);
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