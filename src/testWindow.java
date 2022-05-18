import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

public class testWindow extends JFrame {
    public static JPanel screen;
    public static JFrame window;
    testWindow(){
        window=this;
        JPanel jp=new JPanel(null){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(new ImageIcon("img/bg2.jpg").getImage(), 0, 0, 1400,800,null);
            }
        };
        setTitle("Mine Of Duty");
        setSize(1400,800);
        JLabel title = new JLabel("Mine Of Duty");
        title.setFont(new Font(Font.SANS_SERIF,Font.BOLD,50));
        title.setBounds(getWidth()/2-(375/2),20,375,70);
        title.setVisible(true);
        jp.add(title);
        screen=new JPanel(null);
        screen.setBounds(15,100,getWidth()-30,getHeight()-150);
        window.setFocusable(true);
        jp.add(screen);
        add(jp);
        screen.setOpaque(false);
        setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                scores.writeScores();
                System.exit(0);
            }
        });
        setResizable(false);
        setVisible(true);
        Actions.mainMenu();
    }
}
