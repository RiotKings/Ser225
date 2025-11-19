package Maps;

import Level.*;
import Tilesets.Tileset2;
import NPCs.Bug;
import NPCs.EnemyBasic;
import NPCs.Sentry;
import NPCs.Zombie;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor2Room3 extends Map {
    public Floor2Room3() {
        super("Floor2Room3", new Tileset2());
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
        MapTile sentryTile1 = getMapTile(12, 4);  // Spawn at a different location
        if (sentryTile1 != null) {
            Sentry sentry = new Sentry(2, sentryTile1.getLocation().x, sentryTile1.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            sentry.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(sentry);
        }

        // Add Zombie NPC
        MapTile zombieTile0 = getMapTile(7, 1);
        if (zombieTile0 != null)
            npcs.add(new Zombie(1, zombieTile0.getLocation()));
        // Add Zombie NPC
        MapTile zombieTile1 = getMapTile(2, 5);
        if (zombieTile1 != null)
            npcs.add(new Zombie(1, zombieTile1.getLocation()));
       
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