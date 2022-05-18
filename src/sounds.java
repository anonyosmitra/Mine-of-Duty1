import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.HashMap;

public class sounds {
    static HashMap<String,File> clips=new HashMap<>();
    public static void effects(String file){
        effects(file,false);
    }
    public static void effects(String file,boolean loop){

        Thread sound=new Thread(){
            public void run(){
                Clip clip = null;
                try{;
                    clip= AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(clips.get(file)));
                    clip.start();
                    Thread.sleep((int)clip.getMicrosecondLength()/500);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                clip.close();
                if(loop&&!interrupted())
                    effects(file,loop);
            }
        };
        sound.start();
    }
    public static void click(){
        effects("click");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void shot(String gun){
        effects(gun);}
    public static void zombieHurt(String gun,int i){
        effects(gun+"Hurt"+i);}
    public static void zombieGrowl(int i){
        effects("zombie"+i);}
    public static void hurt(){
        effects("hurt");}
    public static void background(){
        effects("Background",true);
    }

    public static void getClips() {
        String[] files={"click","hurt","zombie1","zombie2","shotgun","pistol","rifle","Background","zombieHurt0","zombieHurt1","zombieHurt2","reload"};
        for(String i:files)
            getClip(i);

    }
    private static void getClip(String name){
        File f = new File("sounds/"+name+".wav");
        clips.put(name,f);
    }
}
