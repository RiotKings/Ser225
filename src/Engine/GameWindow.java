package Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/*
 * The JFrame that holds the GamePanel
 * Just does some setup and exposes the gamePanel's screenManager to allow an external class to setup their own content and attach it to this engine.
 */
public class GameWindow {
	private JFrame gameWindow;
	private GamePanel gamePanel;
	private boolean isFullscreen = false;
	private GraphicsDevice device;
	private KeyLocker f11KeyLocker = new KeyLocker();
	
	public GameWindow() {
		gameWindow = new JFrame("Game");
		gamePanel = new GamePanel();
		gamePanel.setFocusable(true);
		gamePanel.requestFocusInWindow();
		gameWindow.setContentPane(gamePanel);
		gameWindow.setResizable(true); // Enable resizing for macOS fullscreen button
		gameWindow.setSize(Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT);
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setVisible(true);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set GameWindow reference in GamePanel for fullscreen support
		gamePanel.setGameWindow(this);
		
		// Get graphics device for fullscreen support
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = ge.getDefaultScreenDevice();
		
		// Add listener to handle window resize and fullscreen changes
		gameWindow.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				// Update screen bounds when window is resized (including fullscreen)
				gamePanel.updateScreenBounds();
			}
		});
		
		gamePanel.setupGame();
	}
	
	/**
	 * Toggles fullscreen mode using F11 key
	 * Works on all platforms (Windows, Linux, macOS)
	 */
	public void updateFullscreen() {
		// Check for F11 key press
		if (Keyboard.isKeyDown(Key.F11) && !f11KeyLocker.isKeyLocked(Key.F11)) {
			f11KeyLocker.lockKey(Key.F11);
			toggleFullscreen();
		}
		
		// Unlock F11 when released
		if (Keyboard.isKeyUp(Key.F11)) {
			f11KeyLocker.unlockKey(Key.F11);
		}
	}
	
	/**
	 * Toggles fullscreen mode
	 */
	private void toggleFullscreen() {
		if (device.isFullScreenSupported()) {
			isFullscreen = !isFullscreen;
			
			if (isFullscreen) {
				// Enter fullscreen
				gameWindow.dispose(); // Dispose frame before fullscreen
				gameWindow.setUndecorated(true);
				device.setFullScreenWindow(gameWindow);
				gameWindow.setVisible(true);
			} else {
				// Exit fullscreen
				device.setFullScreenWindow(null);
				gameWindow.dispose(); // Dispose frame before restoring
				gameWindow.setUndecorated(false);
				gameWindow.setSize(Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT);
				gameWindow.setLocationRelativeTo(null);
				gameWindow.setVisible(true);
			}
			
			// Update screen bounds after fullscreen change
			gamePanel.updateScreenBounds();
		}
	}

	// triggers the game loop to start as defined in the GamePanel class
	public void startGame() {
		gamePanel.startGame();
		// Initialize Mouse and add it to the GamePanel using the Singleton Mouse instance
		Mouse mouse = Mouse.getInstance();  // Use Singleton instance
		gamePanel.addMouseListener(mouse);   // Add MouseListener
		gamePanel.addMouseMotionListener(mouse);  // Add MouseMotionListener
	}

	public Mouse getMouse() {
		return Mouse.getInstance();  // Get the Singleton instance
	}

	public ScreenManager getScreenManager() {
		return gamePanel.getScreenManager();
	}
}
