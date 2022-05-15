import java.awt.*;
public class setCursors {
    public static void target(String gun) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("img/"+gun+"Target.png");
        //Image image = toolkit.getImage("img/target.png");
        Cursor c = toolkit.createCustomCursor(image, new Point(50,50), "img");
        testWindow.window.setCursor(c);
    }
    public static void normal(){
        testWindow.window.setCursor(Cursor.getDefaultCursor());
    }
}
