import java.awt.*;
import java.util.*;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
public class Visual extends JFrame{
    public static ArrayList<SQR> ORDER = new ArrayList<>();
    public static SQR[][] sqrs;
    public static int[][] order;
    public static int aLength = 0;
    private Zeichner p = new Zeichner();
    public int width = 1000;
    public int height = 1000;
    private Scanner in = new Scanner(System.in);
    public int dim = 50;
    public Visual(){
        super();
        setSize(width+16,height+38);
        setTitle("Test123");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//beendet Programm mit schließen des Windows
        Container c = getContentPane();
        setResizable(false);
        p.setVisible(true);
        p.setBackground(Color.BLACK);
        c.add(p);
        setVisible(true);
        firstOrder();
        firstSortTest();
        remove0(ORDER);
        Sort.sortArrayList(ORDER);
        p.repaint();
    }
    public Visual(String sort, int size){
        super();
        setSize(width+16,height+38);
        setTitle("Test123");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        setResizable(false);
        p.setVisible(true);
        p.setBackground(Color.BLACK);
        c.add(p);
        setVisible(true);
        centreCreate(size);
        updateLength();
        Sort.inputToSort(order,sort);
        updateLength();
        HilbertSort.fill0(ORDER,aLength);
        for(int i = 0;i<order.length;i++){
            for(int j = 0;j<order[i].length;j++){
                sqrs[i][j].index = order[i][j];
                ORDER.add(sqrs[i][j].index,sqrs[i][j]);
            }
        }
        remove0(ORDER);
        remove0(ORDER);//kann man nie zu oft aufrufen
        Sort.sortArrayList(ORDER);
        p.repaint();
    }
    public void loop(){//wird noch nicht gebraucht
        while(true) {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch(Exception e){
                System.out.println(e);
            }
           p.repaint();
        }
    }
    public void centreCreate(int a){//erstellt 2d Arrays fpr sqrs und ints
        sqrs = new SQR[2*a][2*a];
        order = new int[2*a][2*a];
        createSqrs(sqrs);
    }
    public void firstOrder(){//nur für testzwecke
        System.out.println("how many rows from centre?");
        int num = Integer.parseInt(in.nextLine());
        centreCreate(num);
        updateLength();
    }
    public void createSqrs(SQR[][] a){//füllt den Array mit sqrs und setzt Koordinaten
        int dim = (width-100)/a.length;
        for(int i = 0;i<a.length;i++){
            for(int j = 0;j<a[i].length;j++) {
                a[i][j] = new SQR((i*dim)+50,(j*dim)+50,dim);
            }
        }
    }
    public void updateLength(){
        aLength = Visual.sqrs.length*Visual.sqrs.length;
    }
    public void firstSortTest(){//nur für testzwecke
        HilbertSort xx = new HilbertSort(order);
        xx.doHilberta(order);
        HilbertSort.fill0(ORDER,aLength);
        for(int i = 0;i<order.length;i++){
            for(int j = 0;j<order[i].length;j++){
                sqrs[i][j].index = order[i][j];
                ORDER.add(sqrs[i][j].index,sqrs[i][j]);
            }
        }
        remove0(ORDER);
    }
    public void printThing(){//nur für testzwecke
        for(int i = 0;i<sqrs.length;i++){
            for(int j = 0;j<sqrs[i].length;j++){
                System.out.print(String.format("%"+(String.valueOf(sqrs[sqrs.length-1][sqrs.length-1].index).length()+1)+"d",sqrs[i][j].index));
            }
            System.out.print("\n");
        }
    }
    public static void remove0(ArrayList<SQR> all){//removed alle null Elemente aus einer Arraylist
        for(int i = 0;i<all.size();i++){
            if(all.get(i) == null){
                all.remove(all.get(i));
            }
        }
    }
}