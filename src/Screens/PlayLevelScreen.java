package Screens;

import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import Engine.GraphicsHandler;
import Engine.Mouse;
import Engine.Screen;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.*;

import Maps.Floor1BossRoomMap;
import Maps.Floor1Room5;
import Players.Alex;
import Utils.Direction;
import Utils.Point;
import Hud.GameHealthHUD;

import javax.sound.sampled.*;
import java.io.File;
import java.net.URL;

//Levels
import Maps.FirstRoom;
import Maps.Floor1Room0;
import Maps.Floor1Room1;
import Maps.Floor1Room2;
import Maps.Floor1Room3;
import Maps.Floor1Room4;
import Maps.Floor1Room6;
import Maps.Floor1Room7;
// new rooms 
import Maps.Floor1Room8;
import Maps.Floor1Room9;
import Maps.Floor1Room10;
import Maps.Floor1Room11;
import Maps.Floor1Room12;
import Maps.Floor1Room13;
import Maps.Floor1Room14;
import Maps.Floor1Room15;
import Maps.Floor1Room16;
import Maps.Floor1Room17;
import Maps.Floor1Room18;
import Maps.TreasureRoom;

// Knowledge system
import NPCs.PhantomEnemy;

// This class is for when the RPG game is actually being played
public class PlayLevelScreen extends Screen implements GameListener {
    protected ScreenCoordinator screenCoordinator;
    protected Map map;
    protected Player player;
    protected PlayLevelScreenState playLevelScreenState;
    protected WinScreen winScreen;
    protected GameOverScreen gameOverScreen;
    protected FlagManager flagManager;
    protected GameHealthHUD healthHUD;
    
    // Knowledge system
    protected KnowledgeSystem knowledgeSystem;
    private int lastPlayerHealth;

    private Clip bgmClip;
    private int loopStartFrame = -1;
    private int loopEndFrame   = -1;

    int MapCount = 0; 
    int lastIndex = -1;

    public PlayLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        // setup state
        flagManager = new FlagManager();
        flagManager.addFlag("hasLostBall");
        flagManager.addFlag("hasTalkedToWalrus");
        flagManager.addFlag("hasTalkedToBug");
        flagManager.addFlag("hasFoundBall");

        map = new FirstRoom(); // starting room
        map.setFlagManager(flagManager);

        // setup player
        player = new Alex(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        player.setMap(map);
        player.setHealth(6); // full health
        lastPlayerHealth = player.getHealth();

        // ADD THESE LINES FOR MOUSE SUPPORT:
        Mouse mouse = Mouse.getInstance();
        player.setMouse(mouse);

        // Initialize knowledge system
        knowledgeSystem = new KnowledgeSystem();
        player.setKnowledgeSystem(knowledgeSystem); // Pass knowledge system to player

        playLevelScreenState = PlayLevelScreenState.RUNNING;
        player.setFacingDirection(Direction.LEFT);

        map.setPlayer(player);

        // let map know which key is interact
        map.getTextbox().setInteractKey(player.getInteractKey());

        // add this screen as a listener
        map.addListener(this);

        // preload scripts
        map.preloadScripts();

        // initialize health HUD
        healthHUD = new GameHealthHUD(player);

        winScreen = new WinScreen(this);
        gameOverScreen = new GameOverScreen(this);

        startBackgroundMusic();
    }

    public void update() {
        // based on screen state, perform specific actions
        switch (playLevelScreenState) {
            // if level is "running" update player and map to keep game logic for the platformer level going
            case RUNNING:
                player.update();
                map.update(player);
                
                // Update knowledge system
                knowledgeSystem.update(player);
                
              // Check if player took damage and increase knowledge
int currentHealth = player.getHealth();
if (currentHealth < lastPlayerHealth) {
    int damageTaken = lastPlayerHealth - currentHealth;
    
    // Faster progression: reach 100% at 75% health lost (4.5 damage out of 6)
    // This lets players experience Tier 4 effects before dying
    float maxHealth = player.getMaxHealth();
    float knowledgePerDamage = 1.33f / maxHealth; // 1.33 makes it reach 100% at ~4.5 damage
    
    knowledgeSystem.addKnowledge(damageTaken * knowledgePerDamage);
    
    System.out.println("Player took " + damageTaken + " damage! Knowledge: " + 
        String.format("%.1f", knowledgeSystem.getKnowledgeLevel() * 100) + "% (Tier " + 
        knowledgeSystem.getKnowledgeTier() + ")");
}
lastPlayerHealth = currentHealth;
                
                if (player.getHealth() <= 0) {
                    onLose();
                }
                break;
            // if level has been completed, bring up level cleared screen
            case LEVEL_COMPLETED:
                winScreen.update();
                stopBackgroundMusic();
                break;
            // if player has lost, bring up game over screen
            case LEVEL_LOST:
                gameOverScreen.update();
                stopBackgroundMusic();
        }
    }

    @Override
    public void onWin() {
        // when this method is called within the game, it signals the game has been "won"
        playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
    }

    public void onLose(){
        playLevelScreenState = PlayLevelScreenState.LEVEL_LOST;
        // Clean up audio
        if (knowledgeSystem != null) {
            knowledgeSystem.cleanup();
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        // based on screen state, draw appropriate graphics
        switch (playLevelScreenState) {
            case RUNNING:
                map.draw(player, graphicsHandler);
                healthHUD.draw(graphicsHandler);
                
                // Apply knowledge visual effects on top of everything
                if (knowledgeSystem != null) {
                    knowledgeSystem.applyVisualEffects(graphicsHandler, 800, 600); // Use your screen dimensions
                }
                break;
            case LEVEL_COMPLETED:
                winScreen.draw(graphicsHandler);
                break;
            case LEVEL_LOST:
                gameOverScreen.draw(graphicsHandler);
        }
    }

    public PlayLevelScreenState getPlayLevelScreenState() {
        return playLevelScreenState;
    }

    public void resetLevel() {
        initialize();
    }

    public void goBackToMenu() {
        screenCoordinator.setGameState(GameState.MENU);
    }

    @Override
    public void onScreenSizeChanged() {
        // Update map camera and midpoints when screen size changes (e.g., fullscreen)
        if (map != null) {
            map.updateScreenSize();
        }
    }

    // This enum represents the different states this screen can be in
    private enum PlayLevelScreenState {
        RUNNING, LEVEL_COMPLETED, LEVEL_LOST;
    }


    @Override
    public void changeMap() {
        System.out.println("Attempting to change map. Enemy count: " + map.getEnemyCount());
        if (map.getEnemyCount() == 0){
        Map[] pool = new Map[] {
            new Floor1Room0(),
            new Floor1Room1(),
            new Floor1Room2(),
            new Floor1Room3(),
            new Floor1Room4(),
            new Floor1Room5(), 
            new Floor1Room6(),
            new Floor1Room7(),
            // new rooms
            new Floor1Room8(),
            new Floor1Room9(),
            new Floor1Room10(),
            new Floor1Room11(),
            new Floor1Room12(),
            new Floor1Room13(),
            new Floor1Room14(),
            new Floor1Room15(),
            new Floor1Room16(),
            new Floor1Room17(),
            new Floor1Room18(),
        
             
        };

        //clear player's bullets before changing maps
        if (player != null && player.getBullets() != null) {
            player.getBullets().clear();
        }

        // clear all NPCs (including bullets) from the old map
        if (map != null) {
            map.getNPCs().clear();
        }
                
                // Decide next map
        Map next;
        if (MapCount == 3) {
            next = new TreasureRoom(); // Treasure
        } else if (MapCount == 6) {
            next = new TreasureRoom(); // Treasure
        } else if (MapCount == 7) {
            next = new Floor1BossRoomMap(); // Boss
        } else {
            int j;
            do {
                j = java.util.concurrent.ThreadLocalRandom.current().nextInt(pool.length);
            } while (pool.length > 1 && j == lastIndex);  // avoid immediate repeat

            lastIndex = j;
            next = pool[j];
        }
                    MapCount++;

        map = next;
        map.setFlagManager(flagManager);
        player.setMap(map);
        map.setPlayer(player);
        map.addListener(this);
        map.getTextbox().setInteractKey(player.getInteractKey());
        map.preloadScripts();

        player.setLocation(325, 370);

        // NOW spawn phantom enemies AFTER the map is set up
        spawnPhantomEnemies();

        System.out.println("roomcount = " + MapCount);
    }
}
    
    // Spawn phantom enemies when entering a new room
    private void spawnPhantomEnemies() {
        if (knowledgeSystem != null && knowledgeSystem.shouldSpawnPhantom()) {
            int maxPhantoms = knowledgeSystem.getMaxPhantoms();
            int phantomsToSpawn = 1 + (int)(Math.random() * maxPhantoms);
            
            System.out.println("Spawning " + phantomsToSpawn + " phantom enemies!");
            
            for (int i = 0; i < phantomsToSpawn; i++) {
                // Random spawn positions around the room
                float spawnX = 200 + (float)(Math.random() * 400);
                float spawnY = 200 + (float)(Math.random() * 300);
                
                PhantomEnemy phantom = new PhantomEnemy(9000 + i, new Point(spawnX, spawnY));
                phantom.setMap(map);
                map.addNPC(phantom);
            }
        }
    }

        private void startBackgroundMusic() {
        try {
            URL url = new File("Resources/background_music.wav").toURI().toURL();
            AudioInputStream in = AudioSystem.getAudioInputStream(url);

            AudioFormat base = in.getFormat();
            AudioFormat decoded = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    base.getSampleRate(),
                    16,
                    base.getChannels(),
                    base.getChannels() * 2,
                    base.getSampleRate(),
                    false
            );
            AudioInputStream din = AudioSystem.getAudioInputStream(decoded, in);

            bgmClip = AudioSystem.getClip();
            bgmClip.open(din);

            float frameRate = decoded.getFrameRate();
            int totalFrames = (int) bgmClip.getFrameLength();
            int startMs = 0;
            int endMs   = 40000;
            loopStartFrame = Math.max(0, (int) (startMs / 1000f * frameRate));
            loopEndFrame   = Math.min(totalFrames - 1, (int) (endMs   / 1000f * frameRate));

            bgmClip.setLoopPoints(loopStartFrame, loopEndFrame);
            bgmClip.setFramePosition(loopStartFrame);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopBackgroundMusic() {
        try {
            if (bgmClip != null) {
                bgmClip.stop();
                bgmClip.flush();
                bgmClip.close();
            }
        } catch (Exception ignored) {}
        bgmClip = null;
    }
}