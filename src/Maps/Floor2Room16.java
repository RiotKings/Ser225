package Maps;

import Level.*;
import Tilesets.Tileset2;
import NPCs.Bug;
import NPCs.EnemyBasic;
import NPCs.Sentry;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor2Room16 extends Map {
    public Floor2Room16() {
        super("Floor2Room16", new Tileset2());
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
        
        MapTile sentryTile1 = getMapTile(11, 2);
        if (sentryTile1 != null) {
            Sentry sentry = new Sentry(2, sentryTile1.getLocation().x, sentryTile1.getLocation().y);
            sentry.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(sentry);
        }
        
        MapTile bugTile1 = getMapTile(1, 4);
        if (bugTile1 != null) {
            npcs.add(new Bug(1, bugTile1.getLocation()));
        }
        MapTile bugTile2 = getMapTile(7, 2);
        if (bugTile2 != null){
            npcs.add(new Bug(1, bugTile2.getLocation()));
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