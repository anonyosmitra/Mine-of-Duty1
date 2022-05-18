import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Zombie {
    static ArrayList<Zombie> zombies = new ArrayList<>();
    static HashMap<Integer, ImageIcon> zombiePics = new HashMap<>();
    private JLabel entity;
    static int count = 0;
    int id;
    private JProgressBar health;
    private int speed;
    private Thread movement;
    private static ImageIcon zombie1 = null, zombie2 = null, zombie3 = null;
    static Thread zombieGenerator = new Thread();

    public static int randNum(int min, int max) {
        return ((int) ((Math.random() * (max+1 - min)) + min));
    }

    public static void getZombiePics() {
        try {
            for (int i = 1; i < 4; i++)
                zombiePics.put(i, new ImageIcon(ImageIO.read(new File("img/zombie"+i+".png"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Zombie() {
        int picNum=randNum(1, 3);
        entity = new JLabel(zombiePics.get(picNum));
        entity.setBounds(1200, 300, 180, 280);
        health = new JProgressBar(0, randNum(3, 9));
        health.setValue(health.getMaximum());
        health.setForeground(Color.GREEN);
        speed = randNum(20, 40);
        health.setBounds(1200, 280, 180, 20);
        health.setBackground(Color.WHITE);
        health.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Gun.mouseAction(0, e.getButton(), 0, 0);
            }
        });
        entity.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                health.setValue(Gun.mouseAction(0, e.getButton(), health.getValue(), health.getMaximum()));
                if (health.getValue() < 1) {
                    Gun.updateScore(1);
                    kill();
                }
            }
        });
        testWindow.screen.add(entity);
        testWindow.screen.add(health);
        testWindow.screen.revalidate();
        testWindow.screen.repaint();
        movement = new Thread() {
            public void run() {
                int cycleCount = 0;
                while (!isInterrupted() && (entity.getX() >= 0) && (health.getValue() > 0)) {
                    entity.setLocation(entity.getX() - speed, entity.getY());
                    health.setLocation(health.getX() - speed, health.getY());
                    if (entity.getX() < 0) {
                        Gun.hurt();
                        interrupt();
                    }
                    if (cycleCount % 10 == 0)
                        sounds.zombieGrowl(health.getValue() > (health.getMaximum() / 2) ? 2 : 1);
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
        id = count++;
    }

    public static void start() {
        zombieGenerator=new Thread(){
            public void run() {
                while (!interrupted() && Gun.health > 0) {
                    if (zombies.size() < 4)
                        new Zombie();
                    try {
                        Thread.sleep(randNum(2, 5) * 1000);
                    } catch (Exception e) {
                    }
                }
                killAll();
            }
        };
        zombieGenerator.start();
    }

    public void kill() {
        if (zombies.contains(this)) {
            this.health.setValue(0);
            testWindow.screen.remove(health);
            testWindow.screen.remove(entity);
            testWindow.screen.revalidate();
            testWindow.screen.repaint();
            this.movement.interrupt();
            zombies.remove(this);
        }
    }
        public static void killAll () {
            for (Object i : zombies.toArray())
                ((Zombie) i).kill();
        }
    }
