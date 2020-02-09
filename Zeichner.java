import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.*;

public class Zeichner extends JPanel {
    public Zeichner() {
        super();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        if(Visual.aLength != 0) {//zeichnet zuerst alle quadrate
            for(int i = 0; i < Visual.sqrs.length; i++) {
                for(int j = 0; j < Visual.sqrs[i].length; j++) {
                    g.drawRect(Visual.sqrs[i][j].xPos, Visual.sqrs[i][j].yPos, Visual.sqrs[i][j].dim, Visual.sqrs[i][j].dim);
                }
            }
        }
        g.setColor(Color.ORANGE);
        if (Visual.ORDER.size() != 0) {//zeichnet anschließend die Kurve
            for (int j = 0; j < Visual.ORDER.size(); j++) {
                if (j != Visual.ORDER.size() - 1 && Visual.ORDER.get(j+1)!=null) {
                    try {
                        g.drawLine(Visual.ORDER.get(j).getcY(), Visual.ORDER.get(j).getcX(), Visual.ORDER.get(j + 1).getcY(), Visual.ORDER.get(j + 1).getcX());
                    } catch(Exception e){
                        //bestes Error-handeling
                    }
                }
            }
        }
    }
}