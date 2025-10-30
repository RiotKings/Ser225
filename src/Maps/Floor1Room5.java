package Maps;
import java.util.ArrayList;

import Level.EnhancedMapTile;
import Level.Map;
import Level.MapTile;
import Level.NPC;
import Level.Trigger;
import NPCs.Bug;
import NPCs.EnemyBasic;
import Scripts.TestMap.LostBallScript;
import Tilesets.CommonTileset;
import Scripts.*;

import Utils.Point;
public class Floor1Room5 extends Map {
    public Floor1Room5() {
        super("Floor1Room5.txt", new CommonTileset());
        this.playerStartPosition = new Point(325, 200);

}
     @Override
    protected ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        // Add Bug NPC
        MapTile bugTile = getMapTile(11, 7);
        if (bugTile != null) 
            npcs.add(new Bug(1, bugTile.getLocation()));
        
        // Add EnemyBasic NPC
        MapTile enemyTile0 = getMapTile(10, 7);  // Spawn at a different location
        if (enemyTile0 != null) {
            EnemyBasic enemy0 = new EnemyBasic(2, enemyTile0.getLocation().x, enemyTile0.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            enemy0.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(enemy0);

        MapTile enemyTile1 = getMapTile(10, 4);  // Spawn at a different location
        if (enemyTile1 != null) {
            EnemyBasic enemy1 = new EnemyBasic(2, enemyTile1.getLocation().x, enemyTile1.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            enemy1.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(enemy1);
        }
        
        
        return npcs;
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