import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Gun {
    static String playerName;
    static int health,score;
    static JPanel pan=new JPanel(null);
    static JLabel scoreCount=new JLabel();
    static ImageIcon fullH,emptyH;
    static Gun current;
    static ArrayList<Gun> guns=new ArrayList<>();
    String name;
    Thread reloading=new Thread();
    int damage,bullets,reloadTime,hotkey,count;
    ImageIcon gunImg=null,bulletImg=null;
    private Gun(String name,int damage, int bullets,int reload,int hotkey){
        this.name=name;
        this.damage=damage;
        this.count=bullets;
        this.bullets=bullets;
        this.reloadTime=reload;
        this.hotkey=hotkey;
        Image g,b;
        try {
            g=ImageIO.read(new File("img/"+name+".png"));
            b=ImageIO.read(new File("img/"+name+"Bullet.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.gunImg=new ImageIcon(g.getScaledInstance(50,30, Image.SCALE_DEFAULT));
        this.bulletImg=new ImageIcon(b.getScaledInstance(30,150, Image.SCALE_DEFAULT));
        guns.add(this);
    }
    private void reload(){
        if(count==bullets || reloading.isAlive())
            return;
        reloading=new Thread(){
            public void run() {
                boolean Inter=false;
                try {
                    Thread.sleep(reloadTime);
                } catch (InterruptedException e) {
                    Inter=true;
                }
                if(!isInterrupted()&&!Inter){
                    count=bullets;
                    sounds.effects("reload");
                    makePanel();
                }
            }
        };
        reloading.start();
        makePanel();
    }
    public static void makePanel(){
        setCursors.target(current.name);
        pan.removeAll();
        JLabel gunPic = new JLabel(current.gunImg);
        gunPic.setBounds(pan.getWidth()/2,pan.getHeight()-100,100,100);
        JLabel bulletPic = new JLabel(current.bulletImg);
        bulletPic.setBounds(pan.getWidth()-50,0,50,200);
        JLabel bulletCount=new JLabel(current.count+"/∞");
        bulletCount.setFont(new Font(Font.SANS_SERIF,Font.BOLD,25));
        bulletCount.setForeground(Color.BLACK);
        bulletCount.setHorizontalAlignment(SwingConstants.RIGHT);
        bulletCount.setBounds(pan.getWidth()-110,pan.getHeight()-30,70,30);
        pan.add(gunPic);
        pan.add(bulletPic);
        pan.add(bulletCount);

        int x=100,y=pan.getHeight()-80;
        for(int i=1;i<4;i++){
            JLabel h;
            if(health>=i)
                h=new JLabel(fullH);
            else
                h=new JLabel(emptyH);
            h.setBounds(x,y,40,40);
            pan.add(h);
            x=x+50;
        }
        pan.revalidate();
        pan.repaint();
    }
    public static void hurt(){
        sounds.hurt();
        health--;
        makePanel();
        if(health==0){
            Zombie.zombieGenerator.interrupt();
            Zombie.killAll();
            new scores(playerName,score);
            Actions.scoreBoard();
        }
    }
    public static void updateScore(int add){
        score=score+add;
        scoreCount.setText(Integer.toString(score));
    }
    public static void makeGuns(){
        guns=new ArrayList<>();
        current=new Gun("pistol",3,8,2000,'1');
        new Gun("rifle",4,15,4000,'2');
        new Gun("shotgun",5,2,6000,'3');
    }
    public static void makeGame(){
        health=3;
        score=0;
        JLabel player=new JLabel(playerName);
        player.setFont(new Font(Font.SANS_SERIF,Font.BOLD,40));
        player.setForeground(Color.decode("#fc6f03"));
        player.setBounds(50,100,300,45);
        testWindow.screen.add(player);
        scoreCount.setFont(new Font(Font.SANS_SERIF,Font.BOLD,40));
        scoreCount.setForeground(Color.decode("#fc6f03"));
        scoreCount.setHorizontalAlignment(SwingConstants.RIGHT);
        scoreCount.setBounds(1000,100,300,45);
        updateScore(0);
        testWindow.screen.add(scoreCount);
        Image a,b;
        try {
            a=ImageIO.read(new File("img/heartFull.png"));
            b=ImageIO.read(new File("img/heartEmpty.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fullH=new ImageIcon(a.getScaledInstance(40,40, Image.SCALE_DEFAULT));
        emptyH=new ImageIcon(b.getScaledInstance(40,40, Image.SCALE_DEFAULT));
        makeGuns();
        pan=new JPanel(null);
        pan.setBounds(0,testWindow.screen.getHeight()-150,testWindow.screen.getWidth(),150);
        pan.setOpaque(false);
        testWindow.screen.add(pan);
        makePanel();
    }

    public static void keyPressed(char keyChar) {
        for(Gun g:guns)
            if(g.hotkey==keyChar)
                swapGun(g);
        if(keyChar=='r'){
            reloadCurrent();}
    }
    public static int mouseAction(int rot,int button,int v,int max){
        int c=guns.indexOf(current),ne=c;
        if(rot>0)
            ne++;
        else if(rot<0)
            ne--;
        if(ne>=guns.size())
            ne=0;
        else if (ne==-1)
            ne=guns.size()-1;
        if(c!=ne)
            swapGun(guns.get(ne));
        if(button==1)
            if(v==0)
                shot();
            else
                return shot(v,max);
        else if(button==3)
            reloadCurrent();
        return v;
    }
    public static void swapGun(Gun g){
        if(current.reloading.isAlive()){
            current.reloading.interrupt();}
        current=g;
        if(current.count==0)
            current.reload();
        makePanel();
    }
    public static void shot(){
        if(current.count>0) {
            if(current.reloading.isAlive())
                current.reloading.interrupt();
            current.count--;
            sounds.shot(current.name);
            makePanel();
        }
        if(current.count==0 && !current.reloading.isAlive())
            current.reload();
    }
    public static int shot(int v,int max){
        if(current.count>0) {
            if(current.reloading.isAlive())
                current.reloading.interrupt();
            current.count--;
            v= v - current.damage;
            sounds.shot(current.name);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(v<=0){
                sounds.effects("zombieHurt0");}
            else{
                sounds.effects("zombieHurt"+(v>(max/2)? 2 : 1));
                }
            makePanel();
        }
        if(current.count==0)
            current.reload();
        return v;
    }
    public static void reloadCurrent(){
        current.reload();
    }

}
