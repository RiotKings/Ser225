package Maps;

import Level.*;
import Tilesets.Tileset2;
import NPCs.Bug;
import NPCs.EnemyBasic;
import NPCs.Mine;
import NPCs.Sentry;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor2Room1 extends Map {
    public Floor2Room1() {
        super("Floor2Room1", new Tileset2());
            this.playerStartPosition = new Point(325, 370);
             this.setEnemyCount(3);
    }

    @Override
    protected ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }

    @Override
    protected ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        
        // Add mine NPC
        MapTile mineTile = getMapTile(5, 2);
        if (mineTile != null) 
            npcs.add(new Mine(1, mineTile.getLocation()));
         // Add mine NPC
        MapTile mineTile2 = getMapTile(9, 2);
        if (mineTile != null) 
            npcs.add(new Mine(1, mineTile2.getLocation()));
        
        // TEMP FIX: disable NPCs for Map Editor stability
        MapTile sentryTile1 = getMapTile(10, 7);  // Spawn at a different location
        if (sentryTile1 != null) {
            Sentry sentry = new Sentry(2, sentryTile1.getLocation().x, sentryTile1.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            sentry.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(sentry);
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