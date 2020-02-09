import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;

public class ReadWritePic {
    private Scanner AllIn;// = new Scanner(System.in);
    private PrintWriter AllOut;
    private int picSizeX;
    private int picSizeY;
    private int picSize;
    private int[][] all;
    private ArrayList<Integer> allMore;
    //http://www.imagemagick.org/Usage/formats/#pbmplus
    //convert jpg.ppm -compress none foo.ppm
    //bash command zum konvertieren von jedem Bildtyp zu ppm
    public ReadWritePic(String path) {
        all = getAndConvertPic(path);
    }
    public int[][] returnArray(){
        return all;
    }
    private void toArray(ArrayList<Integer> b) {//Nimmt jedes Element aus der ArrayListe und packt es in einen 2dArray
        for (int i = 0; i < picSizeX; i++) {    //klappt nur bei quadratischen Bildern, da ich sowieso nur mit diesen arbeite
            for (int j = 0; j < picSizeX; j++) {
                all[i][j] = b.get(j + (picSizeX * i - (i > 0 ? 1 : 0)));
            }
        }
    }
    private void getPic() {//Sucht das Bild mit Frage nach dem Path
        System.out.println("Name of pic");
        try {
            AllIn = new Scanner(new File("Hund3.ppm"));//"/home/linux/.local/share/wallpapers/bl3new.ppm"
            System.out.println("Got the pic");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private void getPic(String b){//Sucht das Bild, Path ist Parameter
        try{
            AllIn = new Scanner(new File(b));
        } catch(Exception e){
            System.out.println("File not found");
            System.out.println(e);
        }
    }
    private void convertPic() {//schreibt jede Zahl in eine ArrayListe
        useHeader();
        all = new int[picSizeX][picSizeY];
        allMore = new ArrayList<>();
        //System.out.println("start");
        while (AllIn.hasNext()) {
            allMore.add(AllIn.nextInt());
        }
    }
    private void useHeader() {//holt die Länge und Breite des Bildes aus dem Header
        String trashcan = AllIn.next();
        if (true) {//Testclass.setFlag(AllIn.nextInt())
            picSizeX = AllIn.nextInt();
            System.out.println(picSizeX);
            picSizeY = AllIn.nextInt();
            System.out.println(picSizeY);
        }
        int twofiftyfive = AllIn.nextInt();
    }
    private ArrayList<Integer> thrice() {//addiert jeden dritten Wert(r,g,b) Wird zu einem durchschnittswert
        ArrayList<Integer> a = new ArrayList<>();
        for (int i = 0; i < allMore.size(); i += 3) {
            if (i < allMore.size() - 3) {
                a.add((allMore.get(i) + allMore.get(i + 1) + allMore.get(i + 2))/3);
            }
        }
        return a;

    }
    private int[][] getAndConvertPic(String b){//
        getPic(b);
        convertPic();
        toArray(thrice());
        return all;
    }

    //deubug, wird nicht mehr benötigt
    public void writePic(int size,int r, int g, int b){
        String a = "Test_r"+r+"_g"+g+"_b"+b+".ppm";
        try {
            AllOut = new PrintWriter(a);
            AllOut.println("P3 \n" + size +" "+ size + "\n255");
            for(int i = 0;i<Math.pow(size,2);i++){
                AllOut.print(r + " " + g + " " + b + " ");
            }
        }catch(Exception e){

        }
    }
    public void writeNewPic(int size, ArrayList<Integer> all) {
        try {
            AllOut = new PrintWriter("Hundnewnewnewnew.ppm");
            AllOut.print("P3\n" + picSizeX + " " + picSizeX + "\n" + 255 + "\n");
            HilbertSort.printArrayToFile(allMore, AllOut);
        } catch (Exception e) {

        }
        AllOut.close();
        System.out.println("DOne");
    }
    public void writeNewPic(int size, int[][] all) {
        try {
            AllOut = new PrintWriter("Hundnewnewnewnew4.ppm");
            AllOut.print("P3\n" + picSizeX + " " + picSizeX + "\n" + 255 + "\n");
            HilbertSort.printArrayToFile(all, AllOut);
        } catch (Exception e) {

        }
        AllOut.close();
        System.out.println("DOne");
    }
}