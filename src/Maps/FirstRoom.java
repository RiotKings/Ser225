package Maps;

import java.util.ArrayList;

import Level.EnhancedMapTile;
import Level.Map;
import Level.MapTile;
import Level.NPC;
import NPCs.Bug;
import NPCs.EnemyBasic;
import Tilesets.CommonTileset;

import Utils.Point;


public class FirstRoom extends Map {
    public FirstRoom() {
        super("Floor1Room0.txt", new CommonTileset());
        this.playerStartPosition = new Point(7, 4);
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
    protected void loadScripts() { }

}