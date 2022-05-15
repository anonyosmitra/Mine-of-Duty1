import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Actions {
    static KeyListener kls=new KeyListener() {
        @Override
        public void keyTyped(KeyEvent keyEvent) {
        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            Gun.keyPressed(keyEvent.getKeyChar());
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
        }
    };
    static MouseWheelListener mwl=new MouseWheelListener() {
        @Override
        public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
            Gun.mouseAction(mouseWheelEvent.getWheelRotation(),0,0,0);
        }
    };
    static MouseListener msl=new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            Gun.mouseAction(0,mouseEvent.getButton(),0,0);
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    };
    public static void mainMenu(){
        testWindow.screen.removeAll();
        setCursors.normal();
        JButton play=new JButton("Play");
        play.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
        play.setBounds(testWindow.window.getWidth()/2-(300/2),50,300,40);
        play.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sounds.click();
                startGame();
            }
        });
        testWindow.screen.add(play);
        JButton scores=new JButton("Score Board");
        scores.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sounds.click();
                scoreBoard();
            }
        });
        scores.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
        scores.setBounds(testWindow.window.getWidth()/2-(300/2),100,300,40);
        testWindow.screen.add(scores);
        testWindow.screen.revalidate();
        testWindow.screen.repaint();
    }
    public static void scoreBoard(){
        clearScreen();
        setCursors.normal();
        JButton back=new JButton("Back");
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sounds.click();
                mainMenu();
            }
        });
        back.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
        back.setBounds(testWindow.window.getWidth()/12-(200/12),10,200,40);
        testWindow.screen.add(back);
        testWindow.screen.revalidate();
        testWindow.screen.repaint();
    }
    public static void startGame(){
        clearScreen();
        JButton back=new JButton("Back");
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Zombie.killAll();
                sounds.click();
                mainMenu();
                testWindow.screen.removeMouseListener(msl);
                testWindow.screen.removeKeyListener(kls);
                testWindow.screen.removeMouseWheelListener(mwl);
            }
        });
        back.setBounds(testWindow.window.getWidth()/12-(200/12),10,200,40);
        testWindow.screen.add(back);
        Gun.makeGame();
        testWindow.screen.addMouseListener(msl);
        testWindow.screen.addKeyListener(kls);
        testWindow.screen.addMouseWheelListener(mwl);

        testWindow.screen.revalidate();
        testWindow.screen.repaint();
        new Zombie();
    }
    public static void clearScreen(){
        testWindow.screen.removeAll();
        testWindow.screen.revalidate();
        testWindow.screen.repaint();
    }

}
