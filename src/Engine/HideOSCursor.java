package Engine;
import java.awt.*;
import javax.swing.*;

public class HideOSCursor {
    public static void hideCursorOn(Component component) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        // Create a 1x1 transparent image
        Image blankImage = toolkit.createImage(new byte[0]);
        Cursor invisibleCursor = toolkit.createCustomCursor(
                blankImage,
                new Point(0, 0),
                "invisibleCursor"
        );
        component.setCursor(invisibleCursor);
    }
}
