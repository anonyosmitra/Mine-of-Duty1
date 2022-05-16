import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Zombie {
    static ArrayList<Zombie> zombies=new ArrayList<>();
    private JLabel entity;
    private JProgressBar health;
    private int speed;
    private Thread movement;
    private static ImageIcon zombie1 = null;
    public static void getZombiePics(){
        try {
            zombie1 = new ImageIcon(ImageIO.read(new File("img/zombie1.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Zombie(){
        entity = new JLabel(zombie1);
        entity.setBounds(1200,300,180,280);
        health=new JProgressBar(0,9);
        health.setValue(9);
        health.setForeground(Color.GREEN);
        speed=20;
        health.setBounds(1200,280,180,20);
        health.setBackground(Color.WHITE);
        health.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                Gun.mouseAction(0,e.getButton(),0,0);
            }
        });
        entity.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                health.setValue(Gun.mouseAction(0,e.getButton(),health.getValue(),health.getMaximum()));
                if(health.getValue()<1)
                {
                    Gun.updateScore(1);
                    kill();
                }
            }
        });
        testWindow.screen.add(entity);
        testWindow.screen.add(health);
        testWindow.screen.revalidate();
        testWindow.screen.repaint();
        movement=new Thread(){
            public void run(){
                int cycleCount=0;
                while(!isInterrupted()&&(entity.getX()>=0)&&(health.getValue()>0)){
                    entity.setLocation(entity.getX()-speed,entity.getY());
                    health.setLocation(health.getX()-speed,health.getY());
                    if(entity.getX()<0){
                        Gun.hurt();
                        System.out.println("defeat!");
                        interrupt();
                    }
                    if(cycleCount%10==0)
                        sounds.zombieGrowl(health.getValue()>(health.getMaximum()/2)? 2 : 1);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                    }
                    cycleCount++;
                }
                kill();
            }
        };
        movement.start();
        zombies.add(this);
    }
    public void kill(){
        if(zombies.contains(this))
        {this.health.setValue(0);
        testWindow.screen.remove(health);
        testWindow.screen.remove(entity);
        testWindow.screen.revalidate();
        testWindow.screen.repaint();
        this.movement.interrupt();
        zombies.remove(this);}
    }
    public static void killAll(){
        try{
        for(Zombie i:zombies)
            i.kill();}
        catch (Exception e){}
    }
}
