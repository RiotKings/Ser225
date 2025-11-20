package Maps;

import Level.*;
import NPCs.Bug;
import NPCs.EnemyBasic;
import Tilesets.CommonTileset;
import Utils.Point;
import java.util.ArrayList;

import GameObject.DoubleDamage;
import GameObject.SpeedBoots;
import GameObject.ExtraHeart;
import GameObject.firerate;
import GameObject.Shield;



public class TestMap extends Map {

    public TestMap() {
        super("test_map.txt", new CommonTileset());

        // Safe spawn setup
        MapTile spawn = getMapTile(2, 2);   // pick a valid tile inside your map
        if (spawn != null) {
            this.playerStartPosition = spawn.getLocation();
        } else {
            this.playerStartPosition = new Point(0, 0); // fallback so editor won't crash
        }
    }

    @Override
    protected ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }

    @Override
    protected ArrayList<NPC> loadNPCs() {
      
          ArrayList<NPC> npcs = new ArrayList<>();
        
        // Add Bug NPC
        MapTile bugTile = getMapTile(11, 7);
        if (bugTile != null) 
            npcs.add(new Bug(1, bugTile.getLocation()));
        
        // Add EnemyBasic NPC
        MapTile enemyTile = getMapTile(15, 7);  // Spawn at a different location
        if (enemyTile != null) {
            EnemyBasic enemy = new EnemyBasic(2, enemyTile.getLocation().x, enemyTile.getLocation().y);
            // Set bounds for the enemy to wander within (whole map bounds)
            enemy.setBounds(0, 0, getWidthPixels(), getHeightPixels());
            npcs.add(enemy);





        }
        
        

            DoubleDamage doubleDamage = new DoubleDamage(2, 500, 500);
            npcs.add(doubleDamage);

        SpeedBoots speedBoots = new SpeedBoots(2, 550, 500);
        npcs.add(speedBoots);

        ExtraHeart extraHeart = new ExtraHeart(2, 600, 500);
        npcs.add(extraHeart);

        firerate fireRate = new firerate(2, 650, 500);
        npcs.add(fireRate);

        Shield shield = new Shield(2, 700, 500);
        npcs.add(shield);

        return npcs;
        // TEMP FIX: disable NPCs for Map Editor stability
        
    }

    @Override
    protected ArrayList<Trigger> loadTriggers() {
        // TEMP FIX: no triggers so editor doesn't crash
        return new ArrayList<>();
    }

    @Override
    protected void loadScripts() {
        // Add dialogue/triggers here later if needed
    }
}
