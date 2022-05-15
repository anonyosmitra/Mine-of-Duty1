import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        Zombie.getZombiePics();
        sounds.getClips();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new testWindow();
            }
        });
    }
}
