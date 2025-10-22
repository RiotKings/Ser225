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


public class FirstRoom extends Map {
    public FirstRoom() {
        super("Floor1Room0.txt", new CommonTileset());
        this.playerStartPosition = new Point(325, 200);
    }
     @Override
    protected ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        
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