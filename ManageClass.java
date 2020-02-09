import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class ManageClass {
    private Scanner s = new Scanner(System.in);
    public ManageClass() {
        String[] msg = {"Test123, help me im Stuck in a Monitor",
                "Insert description", "You can always write 'help' + Qnum (help1) for examples",
                "any error in the input will not result in a default value\n"};
        String[] qst = {"What do you want to do?(Qnum = 1)\n",
                "Which curve do you want to use?(Qnum = 2)\n",
                "(if required)Set your size?(Qnum = 3)\n",
                "(if required)choose a Path(Qnum = 4)\n",
                "(if required)set your upper and lower Limit for the resulting Sound(Qnum = 5)\n"};
        String[] ans = new String[qst.length];
        //String[] ans = {"o/Vis", "snailSort", "32", "Hund2.ppm", "200-4000"};//Beispiel
        for (int i = 0; i < msg.length; i++) {
            //printDelay("\n" + msg[i], 50);
        }
        for (int i = 0; i < ans.length; i++) {
            printDelay("\n" + qst[i],50);
            ans[i] = help(s.nextLine());
        }
        handleInputs(ans);
    }
    private void handleInputs(String[] ans) {
        if (ans[0].compareToIgnoreCase("o/Vis") == 0) {
            Visual vis = new Visual(ans[1], Integer.parseInt(ans[2]));
        } else if (ans[0].compareToIgnoreCase("o/Sound") == 0) {
            int[][] Hz = new int[Integer.parseInt(ans[2])][Integer.parseInt(ans[2])];
            int[][] db = new int[Hz.length][Hz.length];
            Sort.inputToSort(Hz, ans[1]);
            //Sort.random(db,12);
            db[0][db.length/2] = 200;
            db[db.length-1][db.length/2] = 200;
            SoundManager SM = new SoundManager(Hz, db, 100, 4000);
            SM.createSound();
            SM.startSound();
            System.out.print("This sound will stop in ");
            for (int i = 7; i >= 0; i--) {
                System.out.print(i + "..\t");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                }
            }
            SM.stopSound();
        } else if (ans[0].compareToIgnoreCase("Vis&Sound") == 0) {
            Visual vis = new Visual(ans[1], Integer.parseInt(ans[2]));
            int[][] Hz = new int[Integer.parseInt(ans[2])][Integer.parseInt(ans[2])];
            int[][] db = new int[Hz.length][Hz.length];
            Sort.inputToSort(Hz, ans[1]);
            Sort.random(db, 12);
            String[] x = ans[4].split("-");
            SoundManager SM = new SoundManager(Hz, db, Integer.parseInt(x[0]), Integer.parseInt(x[1]));
            SM.createSound();
            SM.startSound();
            System.out.print("This sound will stop in ");
            for (int i = 7; i >= 0; i--) {
                System.out.print(i + "..\t");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                }
            }
            SM.stopSound();
        } else if(ans[0].compareToIgnoreCase("getPic") == 0){//nicht zu empfehlen, Bilder klingen nicht gut
            ReadWritePic rwp = new ReadWritePic(ans[3]);
            int[][] db = rwp.returnArray();//new int[460][550];//
            int[][] Hz = new int[db.length][db[0].length];
            Sort.inputToSort(Hz,ans[1]);
            String[] x = ans[4].split("-");
            SoundManager SM = new SoundManager(Hz, db, Integer.parseInt(x[0]), Integer.parseInt(x[1]));
            SM.createSound();
            SM.startSound();
            System.out.println(SM.flag());
            System.out.print("This sound will stop in ");
            for (int i = 7; i >= 0; i--) {
                System.out.print(i + "..\t");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                }
            }
            SM.stopSound();
        }else {
            printDelay("Error, try again", 50);
        }
    }
    private String help(String a){
        switch (a){
            case "help1":
                printDelay("options are:\n o/Vis \n o/Sound \n Vis&Sound \n getPic" + "\n",50);
                break;
            case "help2":
                printDelay("options are:\n HilbertSort \t HilbetaSort \n snakeSort \t snailSort \n rowSort \t rndm" + "\n",50);
                break;
            case "help3":
                printDelay("(only for Hilbeta&Hilbert)has to be a power of 2 and result in a square(w/o 0),2x2 4x4 8x8... " + "\n",50);
                break;
            case "help4":
                printDelay("both absolut or relative are possible\n (for testing use either 'Hund2.ppm' or 'tst2,ppm'(doesnt work with Hilbert!))\n pics do not sound pleasant",50);
                break;
            case "help5":
                printDelay("only positive Numbers, anything over 8000Hz will eventually cause headaches, \n input like lower-upper" + "\n",50);
                break;
            default:
                return a;
        }
        return s.nextLine();
    }
    private void printDelay(String str, int d){//klappt nicht richtig
        for(int i = 0;i<str.length();i++){
            System.out.print(str.charAt(i));
            try{
                TimeUnit.MILLISECONDS.sleep(d);
            } catch(Exception e){}
        }
    }
}