package Maps;


import Level.*;
import Tilesets.CommonTileset;
import NPCs.Bug;
import NPCs.EnemyBasic;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;
import NPCs.Sentry;

public class Floor1Room1 extends Map {
    
    public Floor1Room1() {
        super("Floor1Room1", new CommonTileset());
        this.playerStartPosition = new Point(325, 370);
        this.setEnemyCount(2);
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

        
      

        MapTile sentryTile1 = getMapTile(10, 7);  // Spawn at a different location
        if (sentryTile1 != null) {
            Sentry sentry = new Sentry(2, sentryTile1.getLocation().x, sentryTile1.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            sentry.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(sentry);
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
