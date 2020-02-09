import java.awt.*;
public class SQR {
    public int xPos;
    public int yPos;
    public int dim;
    private int cX = xPos+(dim/2);
    private int cY = yPos+(dim/2);
    public int index = 0;
    private int value;
    private boolean col = false;
    private Color c;

    public SQR(){//verschiedene Konstruktoren für die einzenlen Qaudrate
        dim = 50;
        xPos = 0;
        yPos = 0;
        Visual.ORDER.add(this);
    }
    public SQR(int d,int x,int y, boolean b, Color color,int val){//Konstruktor für alles
        dim = d;
        xPos = x;
        yPos = y;
        if(b && color!=null){
            c = color;
        }
        value = val;
    }
    public SQR(int x,int y){
        dim = 50;
        xPos = x;
        yPos = y;
        Visual.ORDER.add(this);
    }
    public SQR(int x,int y,int dimn){
        dim = dimn;
        xPos = x;
        yPos = y;
        //Visual.ORDER.add(this);
    }
    public SQR(int x,int y,int ind,int dimn){
        dim = dimn;
        xPos = x;
        yPos = y;
        index = ind;
    }
    public SQR(int ind){
        dim = 50;
        xPos = 0;
        yPos = 0;
        index = ind;
    }
    public int getcX(){//typische get und set Methoden
        cX = xPos+(dim/2);
        return cX;
    }
    public int getcY(){
        cY = yPos+(dim/2);
        return cY;
    }
    public void setIndex(int a){
        //this.index = Visual.all.indexOf(this);
        this.index = a;
    }
    public void setColor(Color a){
        c = a;//new Color(0, 0, 0)
    }
}
