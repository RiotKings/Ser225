package Maps;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

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

import Engine.Item;
import GameObject.SpeedBoots;
import GameObject.ExtraHeart;
import GameObject.firerate;

import Utils.Point;
import GameObject.DoubleDamage;
import GameObject.Shield;

public class TreasureRoom extends Map{
    public TreasureRoom() {
        super("TreasureRoom.txt", new CommonTileset());
        this.playerStartPosition = new Point(400, 300);
        this.setEnemyCount(0);
    }
     @Override
    protected ArrayList<NPC> loadNPCs() {
        
        
        ArrayList<NPC> npcs = new ArrayList<>();
        spawnRandomItem(npcs);

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

    public void SpawItem(){
        
    }
     // Spawn a random item from a pool, similar to how changeMap chooses a random map
    private void spawnRandomItem(ArrayList<NPC> npcs) {
        // Choose which tile the item appears on in the TreasureRoom
        // (change these col/row to wherever you want the chest/loot to be)
        MapTile itemTile = getMapTile(7, 4);
        if (itemTile == null) {
            return;
        }

        float x = itemTile.getLocation().x;
        float y = itemTile.getLocation().y;

        // Build a pool of possible item NPCs.
        NPC[] pool = new NPC[] {
            new SpeedBoots(1000, x, y),
            new ExtraHeart(1,x,y),
            new DoubleDamage(2,x,y),
            new Shield(1003,x,y),
            new firerate(3, x, y)
        };

        int j = ThreadLocalRandom.current().nextInt(pool.length);
        NPC chosenItem = pool[j];

        npcs.add(chosenItem);
    }

}



