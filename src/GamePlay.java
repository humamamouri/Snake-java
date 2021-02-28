import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageInputStreamImpl;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.text.AttributeSet.ColorAttribute;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener{

    private int width, height;
    private int[] lengthX, lengthY;
    private int lengthSnake = 3;
    private int containerW, containerH, titleH, rW,rH;

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private boolean gameOver = false;

    private int appleX, appleY, score = 0;

    private Timer timer;
    private Random rand;
    private int delay = 200;
    public GamePlay(int width, int height, int size)
    {
        this.width = width;
        this.height = height;
        this.titleH = height/10;
        this.containerW = width;
        this.containerH = height - titleH;
        this.rW = containerW/size;
        this.rH = containerH/size;
        this.rand = new Random();
        this.appleX = rand.nextInt(containerW/rW-1);
        this.appleY = rand.nextInt(containerH/rH-1) ;
        lengthX = new int [containerW/rW]; lengthY = new int [containerH/rH];
        lengthX[2] = 0; lengthX[1] = 1; lengthX[0] = 2;
        lengthY[0] = lengthY[1] = lengthY[2] = 0;

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();

    }

    public void paint(Graphics g)
    {
        
        // draw title image region
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width, titleH);

        g.setColor(Color.white);
        g.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        g.drawString("Score: " + score, 3*width/4, titleH/2);
        g.setColor(Color.green);
        g.setFont(new Font(Font.SERIF, Font.BOLD, 70));
        g.drawString("Snake", width/3, titleH);

        // draw background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0,  titleH, containerW, containerH);

        // draw snake
        g.setColor(Color.green);
        g.fillOval(lengthX[0]*rW, lengthY[0]*rH + titleH, rW, rH);
        for (int i = 1; i < lengthSnake; i++) {
            g.fillOval(lengthX[i]*rW, lengthY[i]*rH + titleH, rW, rH);
            if (lengthX[0] == lengthX[i] && lengthY[0] == lengthY[i] || (lengthX[0] ==  width/rW || lengthY[0] == height/rH || lengthY[0] < 0 || lengthX[0] < 0)){
                score = 0;
                right = left = up = down = false;
                gameOver = true;
                
                g.setColor(Color.red);
                g.setFont(new Font(Font.SERIF, Font.BOLD, 100));
                
                g.drawString("GAME OVER", width/6, height/3);
            }
        }

        // spawn apple
        if ( lengthX[0] == appleX && lengthY[0] == appleY){
            lengthSnake++;
            delay *=0.9;
            timer.setDelay(delay);
            score++;
            appleX = rand.nextInt(containerW/rW-1);
            appleY = rand.nextInt(containerH/rH-1);
        }
        
        g.setColor(Color.orange);
        g.fillOval(appleX*rW ,appleY*rH + titleH, rW, rH);

        g.dispose();
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        timer.start();
        if (right)
        {
            for (int i = lengthSnake-1; i >= 0; i--) {
                lengthY[i+1] = lengthY[i];
                if (i == 0) lengthX[i] ++;
                else lengthX[i] = lengthX[i-1];
                
                // if(lengthX[i] >= width/rW ) lengthX[i] = 0;
            }
        }
        if (left)
        {
            for (int i = lengthSnake-1; i >= 0; i--) {
                lengthY[i+1] = lengthY[i];
                if (i == 0) lengthX[i]--;
                else lengthX[i] = lengthX[i-1];
                // if(lengthX[i] <= 0) lengthX[i] = width/rW;
            }
        }
        if (up)
        {
            for (int i = lengthSnake-1; i >= 0; i--) {
                lengthX[i+1] = lengthX[i];
                if (i == 0) lengthY[i]--;
                else lengthY[i] = lengthY[i-1];
                // if(lengthY[i] <=  0) lengthY[i] = containerH/rW;
            }
        }
        if (down)
        {
            for (int i = lengthSnake-1; i >= 0; i--) {
                lengthX[i+1] = lengthX[i];
                if (i == 0) lengthY[i]++;
                else lengthY[i] = lengthY[i-1];
                // if(lengthY[i] >= containerH/rH ) lengthY[i] =  0;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    } 

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            lengthSnake = 3;
            lengthX[2] = 0;
            lengthX[1] = 1;
            lengthX[0] = 2;
        
            lengthY[0] = lengthY[1] = lengthY[2] = 0;
            delay = 200;
            timer.setDelay(delay);
            gameOver = false;
        }
        if (!gameOver){

            if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            {
                right = true;
                left = false;
                up = false;
                down = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT)
            {

                right = false;
                left = true;
                up = false;
                down = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP)
            {

                right = false;
                left = false;
                up = true;
                down = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN)
            {

                right = false;
                left = false;
                up = false;
                down = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }

}
