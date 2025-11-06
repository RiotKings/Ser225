package Maps;

import Level.*;
import Tilesets.CommonTileset;
import NPCs.Bug;
import NPCs.Mine;
import NPCs.EnemyBasic;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

import Engine.Item;
import GameObject.DoubleDamage;

public class Floor1Room6 extends Map {

    public Floor1Room6() {
        super("Floor1Room6.txt", new CommonTileset());

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
        MapTile bugTile = getMapTile(5, 3);
        if (bugTile != null) 
            npcs.add(new Bug(1, bugTile.getLocation()));
        
        // Add EnemyBasic NPC
        MapTile enemyTile = getMapTile(10, 3);  // Spawn at a different location
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
    protected ArrayList<EnhancedMapTile> loadEnhancedMapTiles() { 
        return new ArrayList<>(); 
    }

     @Override
    public ArrayList<Trigger> loadTriggers() {
        ArrayList<Trigger> triggers = new ArrayList<>();
        triggers.add(new Trigger(340, 0, 40, 40, new DoorScript()));        return triggers;
    
    }

    @Override
    protected void loadScripts() { }
}