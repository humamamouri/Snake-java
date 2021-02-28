import java.awt.Color;

import javax.swing.JFrame;

/**
 * Main
 */
// import javax.swing.JFrame

public class Main {

    public static void main(String[] args) {
        JFrame obj = new JFrame();  
        int width = 1500, height = 1000, size = 25;      
        GamePlay gp = new GamePlay(width, height, size);
        obj.setBounds(100,100,width,height);
        obj.setBackground(Color.black);
        obj.setVisible(true);
        obj.setResizable(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gp);
    }
}