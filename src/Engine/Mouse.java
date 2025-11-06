package Engine;
import java.awt.event.*;

public class Mouse implements MouseListener, MouseMotionListener {
    private int mouseX = 0;
    private int mouseY = 0;
    private boolean mousedown = false;

    // Store the last time the position was printed
    private long lastUpdateTime = 0;
    private final long updateInterval = 500;  // Update every 500 milliseconds

    // Static instance of Mouse for Singleton pattern
    private static Mouse instance;

    // Private constructor to prevent instantiation from outside
    private Mouse() {
        // Initialization (if needed)
    }

    // Static method to get the single instance of the Mouse class
    public static Mouse getInstance() {
        if (instance == null) {
            instance = new Mouse();  // Create a new instance if it doesn't exist
        }
        return instance;
    }

    // MouseListener methods
    @Override
    public void mousePressed(MouseEvent e) {
        // You can leave this empty if you don't need to handle clicks
        System.out.println("Mouse pressed at: (" + e.getX() + ", " + e.getY() + ")");
        mousedown = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // You can leave this empty if you don't need to handle releases
        mousedown = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Handle mouse entering the component (optional)
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Handle mouse exiting the component (optional)
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // This method must be implemented. You can leave it empty if you don't need to handle clicks.
        System.out.println("Mouse clicked at: (" + e.getX() + ", " + e.getY() + ")");
    }

    // MouseMotionListener methods
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        // Print the mouse position to the terminal every couple of seconds
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime >= updateInterval) {
            //System.out.println("Mouse moved to: (" + mouseX + ", " + mouseY + ")");
            lastUpdateTime = currentTime;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
         mousedown = true; // stays true during drag
         updatePosAndMaybeLog(e.getX(), e.getY(), "dragged");
    }

      private void updatePosAndMaybeLog(int x, int y, String kind) {
        mouseX = x;
        mouseY = y;

        long now = System.currentTimeMillis();
        if (now - lastUpdateTime >= updateInterval) {
            System.out.println("Mouse " + kind + " to: (" + mouseX + ", " + mouseY + ")");
            lastUpdateTime = now;
        }
    }

    // Getter methods to retrieve mouse coordinates
    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }
    
    public boolean isMouseDown() {
    return mousedown;
    }



    
}
