package Screens;

import java.io.File;
import java.net.URL;

import Engine.GraphicsHandler;
import Engine.Mouse;
import Engine.Screen;
import Engine.SoundEffect;
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

//Levels
import Maps.FirstRoom;
import Maps.Floor1Room0;
import Maps.Floor1Room1;
import Maps.Floor1Room2;
import Maps.Floor1Room3;
import Maps.Floor1Room4;
import Maps.Floor1Room6;
import Maps.Floor1Room7;
import Maps.Floor1Room8;
import Maps.Floor1Room9;
import Maps.Floor2Room0;
import Maps.Floor2Room1;
import Maps.Floor2Room10;
import Maps.Floor2Room11;
import Maps.Floor2Room12;
import Maps.Floor2Room13;
import Maps.Floor2Room14;
import Maps.Floor2Room15;
import Maps.Floor2Room16;
import Maps.Floor2Room17;
import Maps.Floor2Room18;
import Maps.Floor2Room19;
import Maps.Floor2Room2;
import Maps.Floor2Room20;
import Maps.Floor2Room21;
import Maps.Floor2Room22;
import Maps.Floor2Room23;
import Maps.Floor2Room24;
import Maps.Floor2Room3;
import Maps.Floor2Room4;
import Maps.Floor2Room5;
import Maps.Floor2Room6;
import Maps.Floor2Room7;
import Maps.Floor2Room8;
import Maps.Floor2Room9;
import Maps.TestMap;
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
import Maps.FinalBossRoomMap;

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

        map = new Floor1Room1(); // starting room
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
                
                float maxHealth = player.getMaxHealth();
                float knowledgePerDamage = 1.33f / maxHealth;
                
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
            break;
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
        MapCount = 0;
        lastIndex = -1;

        if (player != null) {
            player.setHasSpeedBoots(false);
            player.setHasExtraHeart(false);
            player.setHasDoubleDamage(false);
            player.setHasbulletfire(false); 

            player.deactivateShield();

            player.getBullets().clear();
        }

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
    System.out.println("Attempting to change map. Enemy count: " + map.getEnemyCount() + ", MapCount: " + MapCount);
    if (map.getEnemyCount() == 0){
        
        // sound affect here
        SoundEffect.play("Resources/door_open.wav");

        Map[] poolF1 = new Map[] {
            new Floor1Room0(),
            new Floor1Room1(),
            new Floor1Room2(),
            new Floor1Room3(),
            new Floor1Room4(),
            new Floor1Room5(), 
            new Floor1Room6(),
            new Floor1Room7(),
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
        
        Map[] poolF2 = new Map[] { 
            new Floor2Room0(), 
            new Floor2Room1(),
            new Floor2Room2(),
            new Floor2Room3(),
            new Floor2Room4(),
            new Floor2Room5(),
            new Floor2Room6(),
            new Floor2Room7(),
            new Floor2Room8(),
            new Floor2Room9(),
            new Floor2Room10(),
            new Floor2Room11(),
            new Floor2Room12(),
            new Floor2Room13(),
            new Floor2Room14(),
            new Floor2Room15(),
            new Floor2Room16(),
            new Floor2Room17(),
            new Floor2Room18(),
            new Floor2Room19(),
            new Floor2Room20(),
            new Floor2Room21(),
            new Floor2Room22(),
            new Floor2Room23(),
            new Floor2Room24()
        };

        // clear player's bullets before changing maps
        if (player != null && player.getBullets() != null) {
            player.getBullets().clear();
        }

        // clear all NPCs (including bullets) from the old map
        if (map != null) {
            map.getNPCs().clear();
        }

        // Decide next map
        Map next = null;

        if (MapCount == 5) {
            next = new TreasureRoom();
        } else if (MapCount == 10) {
            next = new TreasureRoom();
        } else if (MapCount == 11) {
            next = new Floor1BossRoomMap();
        } else if (MapCount == 15) {
            next = new TreasureRoom();
        } else if (MapCount == 20) {
            next = new TreasureRoom();
        } else if (MapCount == 21) {
            // next = new Floor2BossRoomMap();
            // Fall through to default behavior if boss room not implemented
            
        } else if (MapCount == 22) {
           next = new FinalBossRoomMap();
            // Fall through to default behavior if boss room not implemented
            int j;
            do {
                j = java.util.concurrent.ThreadLocalRandom.current().nextInt(poolF2.length);
            } while (poolF2.length > 1 && j == lastIndex);
            lastIndex = j;
            next = poolF2[j];
        } else if (MapCount < 10) {

            // --- NO SAME ROOM TWICE (F1) ---
            int j;
            do {
                j = java.util.concurrent.ThreadLocalRandom.current().nextInt(poolF1.length);
            } while (poolF1.length > 1 && j == lastIndex);

            lastIndex = j;
            next = poolF1[j];

        } else { // MapCount >= 10 and not special cases

            // --- NO SAME ROOM TWICE (F2) ---
            int j;
            do {
                j = java.util.concurrent.ThreadLocalRandom.current().nextInt(poolF2.length);
            } while (poolF2.length > 1 && j == lastIndex);

            lastIndex = j;
            next = poolF2[j];
        }

        if (next == null) {
            System.err.println("ERROR: next map is null! MapCount: " + MapCount);
            return;
        }

        MapCount++;
        System.out.println("Changing to new map. MapCount: " + MapCount);
        map = next;
        map.setFlagManager(flagManager);
        player.setMap(map);
        map.setPlayer(player);
        map.addListener(this);
        map.getTextbox().setInteractKey(player.getInteractKey());
        map.preloadScripts();

        // New room entered – refresh shield so it can block one hit again
        player.resetShieldForNewRoom();

        var pos = map.getPlayerStartPosition();
        player.setLocation(pos.x, pos.y);

           

        // spawn phantom enemies AFTER map loads
        spawnPhantomEnemies();

        System.out.println("roomcount = " + MapCount);
    } else {
        System.out.println("Cannot change map: enemy count is " + map.getEnemyCount() + " (must be 0)");
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
        URL url = new File("Resources/tobehere.wav").toURI().toURL();
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
        
        // Set volume AFTER starting the clip
        try {
            if (bgmClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
                
                // Adjust volume here: 0.3f = 30% volume
                float volume = 0.3f; // Change this to make it louder or quieter (0.1 to 1.0)
                float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                gainControl.setValue(dB);
                
                System.out.println("Background music volume set to " + (volume * 100) + "%");
            }
        } catch (Exception volumeError) {
            System.out.println("Could not set volume, playing at default level");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    private void stopBackgroundMusic() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
            bgmClip.close();
        }
    }
}