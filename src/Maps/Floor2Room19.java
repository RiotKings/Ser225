package Maps;

import Level.*;
import Tilesets.Tileset2;
import NPCs.Bug;
import NPCs.EnemyBasic;
import NPCs.Zombie;
import Scripts.DoorScript;
import Utils.Point;
import java.util.ArrayList;

public class Floor2Room19 extends Map {
    public Floor2Room19() {
        super("Floor2Room19", new Tileset2());
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
        
        MapTile enemyTile0 = getMapTile(12, 2);
        if (enemyTile0 != null) {
            EnemyBasic enemy = new EnemyBasic(2, enemyTile0.getLocation().x, enemyTile0.getLocation().y);
            enemy.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(enemy);
        }

        MapTile bugTile1 = getMapTile(1, 4);
        if (bugTile1 != null) {
            npcs.add(new Bug(1, bugTile1.getLocation()));
        }
        MapTile bugTile2 = getMapTile(7, 2);
        if (bugTile2 != null){
            npcs.add(new Bug(1, bugTile2.getLocation()));
        } 
        MapTile bugTile3 = getMapTile(2, 4);
        if (bugTile3 != null) {
            npcs.add(new Bug(1, bugTile3.getLocation()));
        }
        MapTile bugTile4 = getMapTile(7, 3);
        if (bugTile4 != null){
            npcs.add(new Bug(1, bugTile4.getLocation()));
        }  

        MapTile zombieTile1 = getMapTile(3, 3);
        if (zombieTile1 != null) {
            npcs.add(new Zombie(1, zombieTile1.getLocation()));
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