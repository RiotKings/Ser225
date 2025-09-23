package Engine;
import java.awt.event.*;

public class Mouse implements MouseListener, MouseMotionListener {
    private int mouseX = 0;
    private int mouseY = 0;

     // Store the last time the position was printed
    private long lastUpdateTime = 0;
    private final long updateInterval = 500;  // Update every 2 seconds (2000 milliseconds)

    // Constructor
    public Mouse() {
        // Initialization (if needed)
    }

    // MouseListener methods
    @Override
    public void mousePressed(MouseEvent e) {
        // You can leave this empty if you don't need to handle clicks
        System.out.println("Mouse pressed at: (" + e.getX() + ", " + e.getY() + ")");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // You can leave this empty if you don't need to handle releases 
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
            System.out.println("Mouse moved to: (" + mouseX + ", " + mouseY + ")");
            lastUpdateTime = currentTime;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Handle mouse dragging if needed
    }

    // Getter methods to retrieve mouse coordinates
    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }
}
