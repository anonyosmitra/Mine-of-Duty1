import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Actions {
    private static JButton makeButton(String text){
        JButton butt=new JButton(text);
        butt.setBackground(Color.decode("#6c6c6c"));
        butt.setForeground(Color.WHITE);
        butt.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
        return butt;
    }
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
    public static void enterName(){
        testWindow.screen.removeAll();
        setCursors.normal();
        JTextField namef= new JTextField(1);
        namef.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if(namef.getText().length()>25)
                    namef.setText(namef.getText().substring(0,24));
                if(keyEvent.getKeyCode()==10&&namef.getText().length()>0){
                    Gun.playerName=namef.getText();
                    startGame();
                }

            }
        });
        namef.setFocusable(true);
        testWindow.screen.setFocusable(true);
        namef.setFont(new Font(Font.SERIF,Font.PLAIN,30));
        namef.setBounds(testWindow.window.getWidth()/2-(500/2),50,500,40);
        testWindow.screen.add(namef);
        namef.setBackground(Color.LIGHT_GRAY);
        namef.setForeground(Color.WHITE);
        JButton play=makeButton("Play");
        play.setBounds(testWindow.window.getWidth()/2-(500/2),100,220,40);
        play.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sounds.click();
                if(namef.getText().length()>0){
                    Gun.playerName=namef.getText();
                    startGame();
                }
            }
        });
        testWindow.screen.add(play);
        JButton back=makeButton("Back");
        back.setBounds(testWindow.window.getWidth()/2-(500/2)+280,100,220,40);
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sounds.click();
                mainMenu();
            }
        });
        testWindow.screen.add(back);
        testWindow.screen.revalidate();
        testWindow.screen.repaint();
        namef.grabFocus();
    }
    public static void mainMenu(){
        testWindow.screen.removeAll();
        setCursors.normal();
        JButton play=makeButton("PLAY");
        play.setBounds(testWindow.window.getWidth()/2-(500/2),50,500,40);
        play.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sounds.click();
                enterName();
            }
        });
        testWindow.screen.add(play);
        JButton scores=makeButton("SCOREBOARD");
        scores.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sounds.click();
                scoreBoard();
            }
        });
        scores.setBounds(testWindow.window.getWidth()/2-(500/2),100,500,40);
        testWindow.screen.add(scores);
        JButton howTO=makeButton("GUIDE");
        howTO.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sounds.click();
                howToPlay();
            }
        });
        howTO.setBounds(testWindow.window.getWidth()/2-(500/2),150,220,40);
        testWindow.screen.add(howTO);
        JButton exit=makeButton("EXIT");
        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sounds.click();
                testWindow.window.dispatchEvent(new WindowEvent(testWindow.window, WindowEvent.WINDOW_CLOSING));
            }
        });
        exit.setBounds(testWindow.window.getWidth()/2-(500/2)+280,150,220,40);
        testWindow.screen.add(exit);
        testWindow.screen.revalidate();
        testWindow.screen.repaint();
    }
    public static void scoreBoard(){
        clearScreen();
        setCursors.normal();
        JButton back=makeButton("Back");
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
    public static void howToPlay(){
        clearScreen();
        setCursors.normal();
        JButton back=makeButton("Back");
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
        testWindow.screen.add(howToLabs("Objective: Kill zombies before they reach the left end of the screen.",100,60));
        testWindow.screen.add(howToLabs("Controls:",100,100));
        testWindow.screen.add(howToLabs("Use Left Click to shoot",100,140));
        testWindow.screen.add(howToLabs("Use Mouse scroll or 1, 2, 3 to switch weapons",100,180));
        testWindow.screen.add(howToLabs("Use R or Right Click to reload",100,220));
        Gun.makeGuns();
        testWindow.screen.add(howToLabs("Guns:",100,260));
        int y=260;
        for(int i=0;i<Gun.guns.size();i++){
            y=y+40;
            testWindow.screen.add(howToLabs(i+1+": "+Gun.guns.get(i).name+" Damage:"+Gun.guns.get(i).damage+" Bullets:"+Gun.guns.get(i).bullets+" Reload time:"+Gun.guns.get(i).reloadTime/1000+" sec",100,y));
            JLabel gunPic = new JLabel(Gun.guns.get(i).gunImg);
            gunPic.setBounds(950,y-20,100,100);
            testWindow.screen.add(gunPic);
        }
        testWindow.screen.revalidate();
        testWindow.screen.repaint();
    }
    private static JLabel howToLabs(String val,int x,int y){
        JLabel lab=new JLabel(val);
        lab.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
        lab.setForeground(Color.BLACK);
        lab.setBounds(x,y,1200,40);
        return lab;
    }
    public static void startGame(){
        clearScreen();
        JButton back=makeButton("Back");
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
