package Screens;

import Engine.GraphicsHandler;
import Engine.Screen;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.*;
import Maps.TestMap;
import Players.Alex;
import Utils.Direction;
import Maps.TestingRoomMap;
import Hud.GameHealthHUD;

//Levels 

import Maps.FirstRoom;
import Maps.Floor1Room0;
import Maps.Floor1Room1;
import Maps.Floor1Room2;
import Maps.Floor1Room0;


// This class is for when the RPG game is actually being played
public class PlayLevelScreen extends Screen implements GameListener {
    protected ScreenCoordinator screenCoordinator;
    protected Map map;
    protected Player player;
    protected PlayLevelScreenState playLevelScreenState;
    protected WinScreen winScreen;
    protected FlagManager flagManager;
    protected GameHealthHUD healthHUD;

    int MapCount = 0; 

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

        map = new FirstRoom();   //pick room 
        map.setFlagManager(flagManager);

        // setup player
        player = new Alex(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        player.setMap(map);
        player.setHealth(6);


        playLevelScreenState = PlayLevelScreenState.RUNNING;
        player.setFacingDirection(Direction.LEFT);

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
    }

    public void update() {
        // based on screen state, perform specific actions
        switch (playLevelScreenState) {
            // if level is "running" update player and map to keep game logic for the platformer level going
            case RUNNING:
                player.update();
                map.update(player);
                break;
            // if level has been completed, bring up level cleared screen
            case LEVEL_COMPLETED:
                winScreen.update();
                break;
        }
    }

    @Override
    public void onWin() {
        // when this method is called within the game, it signals the game has been "won"
        playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
    }

    public void draw(GraphicsHandler graphicsHandler) {
        // based on screen state, draw appropriate graphics
        switch (playLevelScreenState) {
            case RUNNING:
                map.draw(player, graphicsHandler);
                healthHUD.draw(graphicsHandler);
                break;
            case LEVEL_COMPLETED:
                winScreen.draw(graphicsHandler);
                break;
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

    // This enum represents the different states this screen can be in
    private enum PlayLevelScreenState {
        RUNNING, LEVEL_COMPLETED
    }

    @Override
    public void changeMap() {
        
        int j = (int)(Math.random() * 3);
        Map[] maps = new Map[4];

        // Levels
        maps[0] = new Floor1Room0(); 
        maps[1] = new Floor1Room1(); 
        maps[2] = new Floor1Room2(); 
      
        if (MapCount == 5){
        //map = Floor1BossRoom

            map.setFlagManager(flagManager);
            player.setMap(map);
            map.setPlayer(player);
            map.addListener(this);
            map.getTextbox().setInteractKey(player.getInteractKey());
            map.preloadScripts();
          
            player.setLocation(325, 370);
            System.out.println("roomcount = " + MapCount);
       }else{
            map = maps[j];
            MapCount++;
          
            map.setFlagManager(flagManager);
            player.setMap(map);
            map.setPlayer(player);
            map.addListener(this);
            map.getTextbox().setInteractKey(player.getInteractKey());
            map.preloadScripts();
          
            player.setLocation(325, 370);
            System.out.println("roomcount = " + MapCount);
       } 

       
    }
}