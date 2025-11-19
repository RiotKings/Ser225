package Maps;

import Level.*;
import Tilesets.Tileset2;
import NPCs.Bug;
import NPCs.EnemyBasic;
import NPCs.Zombie;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor2Room7 extends Map {
    public Floor2Room7() {
        super("Floor2Room7", new Tileset2());
            this.playerStartPosition = new Point(325, 370);
             this.setEnemyCount(5);
    }

    @Override
    protected ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }

    @Override
    protected ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        
        // Add Bug NPC
        MapTile bugTile0 = getMapTile(11, 7);
        if (bugTile0 != null) 
            npcs.add(new Bug(1, bugTile0.getLocation()));
        
        // Add Bug NPC
        MapTile bugTile1 = getMapTile(2, 7);
        if (bugTile1 != null) 
            npcs.add(new Bug(1, bugTile1.getLocation()));
            
        // Add Zombie NPC
        MapTile zombieTile0 = getMapTile(7, 1);
        if (zombieTile0 != null)
            npcs.add(new Zombie(1, zombieTile0.getLocation()));

        // Add Zombie NPC
        MapTile zombieTile1 = getMapTile(1, 1);
        if (zombieTile1 != null)
            npcs.add(new Zombie(1, zombieTile1.getLocation()));
        
        // Add EnemyBasic NPC
        MapTile enemyTile0 = getMapTile(12, 2);  // Spawn at a different location
        if (enemyTile0 != null) {
            EnemyBasic enemy = new EnemyBasic(2, enemyTile0.getLocation().x, enemyTile0.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            enemy.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(enemy);
        }
        
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
        // Add dialogue/triggers here later if needed
    }

}