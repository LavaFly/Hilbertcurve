import java.io.PrintWriter;
import java.util.*;
public class HilbertSort {
    private int[][] sortedArray;
    private int[] ODA = {1,2,0,3};
    
    public HilbertSort(){
    }
    public HilbertSort(int[][] unsortedArray){
        sortedArray = unsortedArray;
    }

    private int[][] rotateLeft(int[][] a) {//rotiert 2d Array um 90° nach links
        int[][] b = new int[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                b[i][j] = a[j][a.length - i - 1];
            }
        }
        return b;
    }
    private void addToArray(int[][] a, int x){//addiert auf einen 2d Array einen Wert
        for(int i = 0;i<a.length;i++){
            for(int j = 0;j<a[i].length;j++){
                a[i][j] += x;
            }
        }
        //return a;
    }
    private int[][] rotateRight(int[][] a){//3x links rotieren = 1x rechts rotieren
        return rotateLeft(rotateLeft(rotateLeft(a)));
    }
    private int[][] mirrorLDtoRU(int[][] a){//LD=left down,RU=right up, spiegelt entlang der diagonale
        return rotateLeft(mirrorHorizon(a));
    }
    private int[][] mirrorLUtoRD(int[][] a){//spiegelt entlang der anderen diagonale
        return rotateLeft(mirrorVertical(a));
    }
    private int[][] mirrorHorizon(int[][] a){//spiegelt mittig horizontal
        int[][] newA = new int[a.length][a[0].length];
        for(int i = 0;i<a.length;i++){
            for(int j = 0;j<a[i].length;j++){
                newA[i][j] = a[a[i].length-i-1][j];
            }
        }
        return newA;
    }
    private int[][] mirrorVertical(int[][] a){//spiegelt mittig vertikal
       int[][] newA = new int[a.length][a[0].length];
       for(int i = 0;i<a.length;i++){
           for(int j = 0;j<a[i].length;j++){
               newA[i][j] = a[i][a[j].length-j-1];
           }
       }
       return newA;
    }
    private int[][] fillsArray(int[][] a){//füllt Array mit 4 Werten
        for(int i = 0;i<a.length;i++){
            for(int j = 0;j<a[i].length;j++){
                a[i][j]+=i<a.length/2?(j>=a.length/2?ODA[1]:ODA[0]):(j>a.length/2-1?ODA[3]:ODA[2]);
            }
        }
        return a;
    }
    private void allTimesFour(int[][] a){//multipliziert nachdem ich die Werte aus ODA hinzugefügt hab
        for(int i = 0;i<a.length;i++){   //den Wert aus ODA mal 4
            for(int j = 0;j<a[i].length;j++){
                a[i][j] = (a[i][j]%4)*4; //eigentlich müsste es += sein
            }                            //ich kann mir nicht genau erklären wieso es nur so klappt
        }
    }
    private int[][] copySQRPart(int[][] a,int xPosStart,int xPosEnd,int yPos){//kopiert einen quadraischen Part aus einem 2dArray
        int[][] newA = new int[xPosEnd-xPosStart][xPosEnd-xPosStart];
        for(int i = yPos;i<yPos+(xPosEnd-xPosStart);i++){
            newA[i-yPos] = Arrays.copyOfRange(a[i],xPosStart,xPosEnd);
        }
        return newA;
    }
    private int[][] integrateArray(int[][] org,int[][] toIntegrate,int xPos,int yPos){//integriert in einen 2d Array einen anderen an einer beliebigen Position
        for(int i = 0;i<toIntegrate.length;i++){                                      //da die Methode private ist und nur ich sie ausführe, brauche ich mich nicht um fehlerhafte Parameter kümmern
            for(int j = 0;j<toIntegrate.length;j++){
                org[i+yPos][j+xPos] = toIntegrate[i][j];
            }
        }
        return org;
    }
    private int[][][] getQuaters(int[][] a){//teilt einen 2d Array in 4 Teile und packt diese in einen 3d array
        int aHalf = a.length/2;
        int[][][] quaters = new int[4][a.length][a.length];
        quaters[0] = copySQRPart(a,0,aHalf,0);
        quaters[1] = copySQRPart(a,aHalf,a.length,0);
        quaters[2] = copySQRPart(a,0,aHalf,aHalf);
        quaters[3] = copySQRPart(a,aHalf,a.length,aHalf);
        return quaters;
    }
    private void integrateQuaters(int[][][] toIntegrate,int[][] a){//integriert viertel wieder in einen orginal Array
        integrateArray(a,toIntegrate[0],0,0);
        integrateArray(a,toIntegrate[1],a.length/2,0);
        integrateArray(a,toIntegrate[2],0,a.length/2);
        integrateArray(a,toIntegrate[3],a.length/2,a.length/2);
    }
    private void integrateAndMirror(int[][][] toIntegrate,int[][] a){//integriert und spiegelt viertel
        integrateArray(a,toIntegrate[0],0,0);
        integrateArray(a,toIntegrate[1],a.length/2,0);
        integrateArray(a,mirrorLDtoRU(toIntegrate[2]),0,a.length/2);
        integrateArray(a,mirrorLUtoRD(toIntegrate[3]),a.length/2,a.length/2);
    }
    private void correctHilbetter(int[][] a, int c){//korrigiert falsche Hilbertkurven
        int[][][] newA = getQuaters(a);
        for(int i = 0;i<newA.length;i++){
            addToArray(newA[i],ODA[i]*16*((int)Math.pow(2,c)));
        }
        integrateQuaters(newA,a);
    }
    private double lb(double a){//lb(x)=log2(x) || logarithmus mit basis 2
        return Math.log(a)/Math.log(2.0);
    }
    //Proto-Methoden klappen nur für a.length<64
    private void doProtoHilbeta(int[][] a){
        fillsArray(a);
        if(a.length<=2){
        } else {
            int[][][] b = getQuaters(a);
            for(int i = 0;i<b.length;i++){
                allTimesFour(b[i]);
                doProtoHilbeta(b[i]);
            }
            integrateQuaters(b,a);
            if(a.length>7){
                correctHilbetter(a,a.length / 8 == 1 ? 0 : a.length / 8);
            }
        }
    }
    private void doProtoHilbetter(int[][] a){
        fillsArray(a);
        if(a.length<=2){
        }else {
            int[][][] b = getQuaters(a);
            for(int i = 0;i<b.length;i++){
                allTimesFour(b[i]);
                doProtoHilbetter(b[i]);
            }
            integrateAndMirror(b,a);
            if(a.length>7){
                correctHilbetter(a,a.length / 8 == 1 ? 0 : a.length / 8 );
            }
        }
    }

    public void doHilbeta(int[][] a) {
        if (a.length < 64) {
            doProtoHilbeta(a);
        } else {
            int[][][] b = getQuaters(a);
            for (int i = 0; i < b.length; i++) {
                doHilbeta(b[i]);
            }
            integrateQuaters(b,a);
            correctHilbetter(a,(int)( ( ( lb(a.length) - 8 ) + lb(a.length) ) + 2 ) );
        }
    }
    public void doHilberta(int[][] a){//Hauptmethode
        if(a.length<64){
            doProtoHilbetter(a);
        } else {
            int[][][] b = getQuaters(a);
            for(int i = 0;i<b.length;i++){
                doHilberta(b[i]);
            }
            integrateAndMirror(b,a);
            correctHilbetter(a,(int)( ( ( lb(a.length) - 8 ) + lb(a.length) ) + 2 ) );
        }
    }
    public void doAlphaBerta(int[][] a){//klappt noch nicht
        fillsArray(a);
        if(a.length<=2){
        } else {
            int[][][] b = getQuaters(a);
            for(int i = 0;i < b.length; i++){
                doAlphaBerta(b[i]);
            }
            integrateAndMirror(b,a);
            correctHilbetter(a, (int) ( ( ( lb(a.length) - 8 ) + lb(a.length) ) + 2 ) );
        }
    }

   //public
    public void setArray(int[][] unsortedArray){
        sortedArray = unsortedArray;
    }
    public int[][] getArray(){
        return sortedArray;
    }

    //mehrere Klassen müssen diese Methoden verwenden
    public static boolean setFlag(int a){//testet, ob die Zahl eine Poptenz von 2 ist
        for(int i =1;i<31;i++){
            if(a==Math.pow(2,i)) {
                return true;
            }
        }
        return false;
    }
    public static void fill0(ArrayList<SQR> a,int len){//füllt eine Arrayliste mit null elementen
        for(int i = 0;i<len;i++){
            a.add(null);
        }
    }

    //for debug
    public static void printArray(int[][] a){
        for(int i = 0;i<a.length;i++){
            for(int j = 0;j<a[i].length;j++){
                System.out.print(String.format("%" + ( String.valueOf(a[a.length - 1][a.length - 1]).length() + 1 ) + "d",a[i][j]));
            }
            System.out.print("\n");//System.out.println("") hat den gleichen Effekt
        }
    }
    public static void printArrayToFile(int[][] a,PrintWriter out){
        for(int i = 0;i<a.length;i++){
            for(int j = 0;j<a[i].length;j++){
                out.print(a[i][j] + " ");
            }
            out.print("\n");
        }
    }
    public static void printArrayToFile(ArrayList<Integer> a,PrintWriter out){
        for(int i = 0;i<a.size();i++){
            out.print(a.get(i) + " ");
        }
    }


}