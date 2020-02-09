import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.io.JavaSoundAudioIO;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.ugens.WavePlayer;
import net.beadsproject.beads.ugens.Gain;

public class SoundManager {
    //mithilfe von
    //http://evanxmerz.com/soundsynthjava/Sound_Synth_Java.html
    //https://discourse.processing.org/t/solution-beads-audio-library-error-java-lang-illegalargumentexception-line-unsupported/9454

    private int lowerLimit;
    private int upperLimit;
    private JavaSoundAudioIO jsaIO = new JavaSoundAudioIO();
    private int[][] Hz;
    private int[][] db;
    private WavePlayer[][] wpArray;
    private Gain[][] gArray;
    private AudioContext ac;
    private boolean flag = false;

    /**
    public static void main(String[] args){
        JavaSoundAudioIO jsaIO = new JavaSoundAudioIO();
        JavaSoundAudioIO.printMixerInfo();
    }**/
    public SoundManager(int[][] a, int[][] b, int x, int y){//Haputkonstruktor
        setArrays(a,b);
        lowerLimit = x;//50Hz
        upperLimit = y;//8kHz
        jsaIO.selectMixer(1);//Haputfehlerquelle, wenn der Default Mixer keinen Ton ausgibt, muss ein anderer geawähtl werden
        wpArray = new WavePlayer[Hz.length][Hz[0].length];
        gArray = new Gain[Hz.length][Hz[0].length];
    }
    public void setArrays(int[][] a, int[][] b){//setzt die angegebenen Parameter
        Hz = a;
        db = b;
    }
    public void createSound(){
        double d;
        float f;
        ac = new AudioContext(jsaIO);
        for(int i = 0;i<Hz.length;i++){
            for(int j = 0;j<Hz[i].length;j++){
                d = ((double)Hz[i][j]/(Hz.length*Hz[0].length)*(upperLimit-lowerLimit))+lowerLimit;
                wpArray[i][j] = new WavePlayer(ac,(float)d,Buffer.SINE);
                if(db[i][j]>0 && (db[i][j]/255)<1){
                    f = (float)db[i][j]/255;
                    gArray[i][j] = new Gain(ac,1,f);
                    gArray[i][j].addInput(wpArray[i][j]);
                    ac.out.addInput(gArray[i][j]);
                    flag = true;
                }
            }
        }
    }
    public void startSound(){//selbsterklärend
        ac.start();
    }
    public void stopSound(){
        ac.stop();
    }
    //zum Debug
    //testet ob überhaupt ein Ton ausgegeben wird
    public boolean flag(){
        return flag;
    }
}