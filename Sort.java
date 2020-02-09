import java.util.*;
import java.lang.reflect.Method;
public class Sort{
    private int xPos = 0;
    private int yPos = 0;
    private int value = 1;
    private int maxVal = 2;
    private int[][] all;
    private int view = 0;
    //wird für snailSort benötigt
    public Sort(){
    }
    public Sort(int[][] a){
        setMaxVal(a);
        all = a;
    }
    public void setMaxVal(int[][] a){
        maxVal = a!=null?(int)Math.pow(a.length,2)+1:42;
    }
    public boolean checkVal(){
        return value<maxVal?true:false;
    }
    public void setView(){
        if(view==0){
            if(xPos==all.length-1||all[xPos+1][yPos]!=0){view+=1;}
        }
        else if(view==1){
            if(yPos==all[0].length-1||all[xPos][yPos+1]!=0){view+=1;}
        }
        else if(view==2){
            if(xPos==0||all[xPos-1][yPos]!=0){view+=1;}
        }
        else if(view==3){
            if(yPos==0||all[xPos][yPos-1]!=0){view=0;}
        }
        else{
            System.out.println("Error in setView...");
        }
    }
    public void setPos(){
        if(view==0){
            xPos+=1;
        } else if(view==1){
            yPos+=1;
        } else if(view==2){
            xPos-=1;
        } else if(view==3){
            yPos-=1;
        } else {
            System.out.println("Error int setPos");
        }
    }
    //ich habe praktisch meine Ameise umgeschrieben
    public void snailSort(){
        while(checkVal()){
            all[xPos][yPos]=value;
            setView();
            setPos();
            value++;
        }
    }
    /**
     * falls dieser Kommentar noch steht, habe ich den oberen Part ungeändert eingereicht. Zu meiner Verteidigung, er ist zwar hässlich, funktioniert aber trotzdem
     * Für die "Schneckensortierung" brauchte ich als einziges ein tatsächliches Objekt zum durchnummerieren, weil mir mathematisch nichts eingefallen ist, um diese Anordung zu beschreiben
     * Ich habe den Code auf meinem Handy im Zug geschrieben
     */
    //ein shellsort für meine ArrayList der nach dem Indexattribut der SQRs sortiert
    //shellsort ist zwar langsam, aber ausreichend
    public static void sortArrayList(ArrayList<SQR> all){
        for(int i = 0;i<all.size();i++){
            if(all.get(i)==null){
                all.remove(i);
            }
        }
        int n = all.size();
        for(int gap = n/2;gap>0;gap/=2){
            for(int i = gap;i<n;i++){
                if(all.get(i)!=null) {
                    SQR temp = all.get(i);
                    int tempI = temp.index;
                    int j;
                    for (j = i; j >= gap && all.get(j - gap).index > tempI; j -= gap) {
                        all.set(j, all.get(j - gap));
                    }
                    all.set(j, temp);
                }
            }
        }
    }
    //test für shellsort
    public static void test123(){
        ArrayList<SQR> all = new ArrayList<>();
        all.add(new SQR(4));
        all.add(new SQR(5));
        all.add(new SQR(2));
        all.add(new SQR(1));
        all.add(new SQR(3));
        all.add(new SQR(6));
        all.add(new SQR(7));
        for(int i = 0;i<all.size();i++){
            System.out.println(all.get(i) + " " + all.get(i).index);
        }
        sortArrayList(all);
        for(int i = 0;i<all.size();i++){
            System.out.println(all.get(i) + " " + all.get(i).index);
        }
    }

    //rowSort wäre die standart Nummerierung
    public static void rowSort(int[][] a){
        int count = 0;
        for(int i = 0;i<a.length;i++){
            for(int j = 0;j<a.length;j++){
                a[i][j] = count;
                count++;
            }
        }
    }
    //wie die Bewegung einer Schlange
    public static void snakeSort(int[][] a){
        for(int i = 0;i<a.length;i++){
            for(int j = 0;j<a[i].length;j++){
                a[i][j] = i%2==0?i*a.length+j:i*a.length-j-1+a.length;
            }
        }
    }
    //selbsterklärend
    public static void random(int[][] a,int count){//ich sortiere normal und tausche einfach zufällig zwei Werte, das ganze rekursiv mehrfach
        rowSort(a);
        int cache;
        int rnd;
        for(int i = 0;i<a.length;i++){
            for(int j = 0;j<a[i].length;j++){
                if(Math.random()<0.5){
                    cache = a[i][j];
                    rnd = (int)(Math.random()*(a[i].length/2));
                    try{
                        a[i][j] = a[i][j+rnd];
                        a[i][j+rnd] = cache;
                    } catch(Exception e){
                        a[i][j] = a[i][j-rnd];
                        a[i][j-rnd] = cache;
                    }
                } else {
                    cache = a[i][j];
                    rnd = (int)(Math.random()*(a[i].length/2));
                    try{
                        a[i][j] = a[i+rnd][j];
                        a[i+rnd][j] = cache;
                    } catch(Exception e){
                        a[i][j] = a[i-rnd][j];
                        a[i-rnd][j] = cache;
                    }
                }
            }
        }
        if(count>=0){
            random(a,count-1);
        }
    }
    public static void test2(){
        Sort s = new Sort();
        try{
            Method m = s.getClass().getMethod("rndm",String.class);
            m.invoke(s,"s");
        } catch(Exception e){
            System.out.println(e);
        }
    }
    //sortiert einen gegebenen 2d Array nach einer gegebenen Methode
    public static void inputToSort(int[][] a, String str){
        HilbertSort hs = new HilbertSort(a);
        Sort s = new Sort(a);
        switch (str){// auch wenn ich switch case wirklich nicht mag, fiel mir keine bessere Möglichkeit ein
            case "HilbetaSort":
                hs.doHilbeta(a);
                break;
            case "HilbertSort":
                hs.doHilberta(a);
                break;
            case "snailSort":
                s.snailSort();
                break;
            case "snakeSort":
                Sort.snakeSort(a);
                break;
            case "rndm":
                Sort.random(a,12);
                break;
            default:
                Sort.rowSort(a);
                break;
        }
    }
    //noch nicht geschrieben
    public static int[] diagonalSort(int[][]a){
        int[] x = new int[a[0].length*a.length];
        return x;
    }
}
