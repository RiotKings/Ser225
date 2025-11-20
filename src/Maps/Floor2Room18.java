package Maps;

import Level.*;
import Tilesets.Tileset2;
import NPCs.Bug;
import NPCs.EnemyBasic;
import NPCs.Sentry;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor2Room18 extends Map {
    public Floor2Room18() {
        super("Floor2Room18", new Tileset2());
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
        
        MapTile sentryTile1 = getMapTile(2, 2);
        if (sentryTile1 != null) {
            Sentry sentry = new Sentry(2, sentryTile1.getLocation().x, sentryTile1.getLocation().y);
            sentry.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(sentry);
        }
        MapTile sentryTile2 = getMapTile(2, 7);
        if (sentryTile2 != null) {
            Sentry sentry = new Sentry(2, sentryTile2.getLocation().x, sentryTile2.getLocation().y);
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