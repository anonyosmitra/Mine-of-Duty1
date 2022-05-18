import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        scores.readScores();
        Zombie.getZombiePics();
        sounds.getClips();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new testWindow();
                sounds.background();
            }
        });
    }

}
