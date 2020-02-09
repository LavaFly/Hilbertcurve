import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args){
        ManageClass mgC = new ManageClass();
        /**
        int[][] b = new int[16][16];
        Sort.random(b,30);
        HilbertSort.printArray(b);

        for(int i = 0;i<HilbertSort.class.getDeclaredMethods().length;i++){
            System.out.println(HilbertSort.class.getDeclaredMethods()[i].getName());
        }
        Sort.test2();**/
        int[][] a = new int[8][8];
        HilbertSort b = new HilbertSort();

        b.doHilbeta(a);
        b.printArray(a);
    }
}
