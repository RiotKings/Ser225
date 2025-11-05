package Engine;

import GameObject.Rectangle;
import SpriteFont.SpriteFont;
import Utils.Colors;
import javax.swing.*;
import java.awt.*;

/*
 * This is where the game loop process and render back buffer is setup
 */
public class GamePanel extends JPanel {
	// loads Screens on to the JPanel
	// each screen has its own update and draw methods defined to handle a "section" of the game.
	private ScreenManager screenManager;

	// used to draw graphics to the panel
	private GraphicsHandler graphicsHandler;

	private boolean isGamePaused = false;
	private SpriteFont pauseLabel;
	private KeyLocker keyLocker = new KeyLocker();
	private final Key pauseKey = Key.P;
	private Thread gameLoopProcess;
	
	private Mouse mouse;
	private Hud.Crosshair crosshair;
	
	// Reference to GameWindow for fullscreen toggle
	private GameWindow gameWindow;

	private Key showFPSKey = Key.G;
	private SpriteFont fpsDisplayLabel;
	private boolean showFPS = false;
	private int currentFPS;
	private boolean doPaint;

	// The JPanel and various important class instances are setup here
	public GamePanel() {
		super();
		this.setDoubleBuffered(true);

		// attaches Keyboard class's keyListener to this JPanel
		this.addKeyListener(Keyboard.getKeyListener());
		
	// accesses the Mouse singleton instance and adds MouseListener and MouseMotionListener
		mouse = Mouse.getInstance();  // Get Singleton instance
		this.addMouseListener(mouse);   // Add MouseListener
		this.addMouseMotionListener(mouse);  // Add MouseMotionListener

		crosshair = new Hud.Crosshair(0, 0); // HUD object
		
		hideOsCursor();
		

		// Initialize the Crosshair object here, passing the Mouse object
        


		graphicsHandler = new GraphicsHandler();

		screenManager = new ScreenManager();

		pauseLabel = new SpriteFont("PAUSE", 365, 280, "Arial", 24, Color.white);
		pauseLabel.setOutlineColor(Color.black);
		pauseLabel.setOutlineThickness(2.0f);

		fpsDisplayLabel = new SpriteFont("FPS", 4, 3, "Arial", 12, Color.black);

		currentFPS = Config.TARGET_FPS;

		// this game loop code will run in a separate thread from the rest of the program
		// will continually update the game's logic and repaint the game's graphics
		GameLoop gameLoop = new GameLoop(this);
		gameLoopProcess = new Thread(gameLoop.getGameLoopProcess());
	}
	
	/**
	 * Sets the reference to GameWindow for fullscreen toggle support
	 */
	public void setGameWindow(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
	}

	// this is called later after instantiation, and will initialize screenManager
	public void setupGame() {
		setBackground(Color.black); // Changed from CORNFLOWER_BLUE to black
		// Keep screen manager at original game dimensions for consistent rendering
		screenManager.initialize(new Rectangle(0, 0, Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT));
	}

	// this starts the timer (the game loop is started here)
	public void startGame() {
		gameLoopProcess.start();
	}
   /** Make sure we get focus when added to a window */
    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }

    /** Hides the OS cursor on this panel */
    private void hideOsCursor() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        // 1x1 transparent image works well
        Image blank = tk.createImage(new byte[0]);
        Cursor invisible = tk.createCustomCursor(blank, new Point(0, 0), "invisible");
        setCursor(invisible);
    }

	public ScreenManager getScreenManager() {
		return screenManager;
	}

	public void setCurrentFPS(int currentFPS) {
		this.currentFPS = currentFPS;
	}

	public void setDoPaint(boolean doPaint) {
		this.doPaint = doPaint;
	}

	/**
	 * Updates screen bounds based on current panel size
	 * Called when window is resized or enters/exits fullscreen
	 * Note: We keep the game rendering at original dimensions but center it on screen
	 */
	public void updateScreenBounds() {
		// Don't update screen bounds - keep game at original dimensions for centering
		// The screen will be centered in the draw method instead
		// But update window dimensions for offset calculations
		ScreenManager.setWindowDimensions(getWidth(), getHeight());
	}

	public void update() {
		// Check for fullscreen toggle (F11 key)
		if (gameWindow != null) {
			gameWindow.updateFullscreen();
		}
		
		updatePauseState();
		updateShowFPSState();

		if (!isGamePaused) {
			screenManager.update();
			if (crosshair != null) crosshair.update();
		}
	}

	private void updatePauseState() {
		if (Keyboard.isKeyDown(pauseKey) && !keyLocker.isKeyLocked(pauseKey)) {
			isGamePaused = !isGamePaused;
			keyLocker.lockKey(pauseKey);
		}

		if (Keyboard.isKeyUp(pauseKey)) {
			keyLocker.unlockKey(pauseKey);
		}
	}

	private void updateShowFPSState() {
		if (Keyboard.isKeyDown(showFPSKey) && !keyLocker.isKeyLocked(showFPSKey)) {
			showFPS = !showFPS;
			keyLocker.lockKey(showFPSKey);
		}

		if (Keyboard.isKeyUp(showFPSKey)) {
			keyLocker.unlockKey(showFPSKey);
		}

		fpsDisplayLabel.setText("FPS: " + currentFPS);
	}

	public void draw() {			
		// Calculate center offset to center the game content on screen
		int gameWidth = Config.GAME_WINDOW_WIDTH;
		int gameHeight = Config.GAME_WINDOW_HEIGHT;
		int windowWidth = getWidth();
		int windowHeight = getHeight();
		
		// Store window dimensions in ScreenManager for offset calculations
		ScreenManager.setWindowDimensions(windowWidth, windowHeight);
		
		int offsetX = (windowWidth - gameWidth) / 2;
		int offsetY = (windowHeight - gameHeight) / 2;
		
		// Draw black background for entire window
		Graphics2D g2d = (Graphics2D) graphicsHandler.getGraphics();
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, windowWidth, windowHeight);
		
		// Translate graphics context to center the game content
		g2d.translate(offsetX, offsetY);
		
		// Draw game content at original dimensions (centered)
		screenManager.draw(graphicsHandler);

		// if game is paused, draw pause gfx over Screen gfx
		if (isGamePaused) {
			pauseLabel.draw(graphicsHandler);
			graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), new Color(0, 0, 0, 100));
		}

		if (showFPS) {
			fpsDisplayLabel.draw(graphicsHandler);
		}
		
		// Reset translation before drawing HUD elements like crosshair
		g2d.translate(-offsetX, -offsetY);
		
		// draw crosshair LAST so it's above map/player/overlays
		// Crosshair should be drawn in window coordinates, not translated coordinates
   		if (crosshair != null) crosshair.draw(graphicsHandler);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (doPaint) {
			// every repaint call will schedule this method to be called
			// when called, it will setup the graphics handler and then call this class's draw method
			graphicsHandler.setGraphics((Graphics2D) g);
			draw();
		}
	}
}
