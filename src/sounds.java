import javax.annotation.processing.SupportedSourceVersion;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class sounds {
    static HashMap<String,File> clips=new HashMap<>();
    public static void effects(String file){
        effects(file,2000);
    }
    public static void effects(String file,int time){

        Thread sound=new Thread(){
            public void run(){
                try{
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(clips.get(file)));
                    clip.start();
                    Thread.sleep(time);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
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
    public static void blankShot(String gun){
        effects(gun,1000);}
    public static void zombieHurt(String gun,int i){
        effects(gun+"Hurt"+i);}
    public static void zombieGrowl(int i){
        effects("zombie"+i);}
    public static void hurt(){
        effects("hurt");}

    public static void getClips() {
        String[] files={"click","hurt","zombie1","zombie2","shotgun","shotgunHurt1","shotgunHurt2","shotgunHurt0","pistol","pistolHurt1","pistolHurt2","pistolHurt0","rifle","rifleHurt1","rifleHurt2","rifleHurt0"};
        for(String i:files)
            getClip(i);

    }
    private static void getClip(String name){
        File f = new File("sounds/"+name+".wav");
        clips.put(name,f);
    }
}
