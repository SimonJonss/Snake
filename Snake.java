import java.lang.Thread;

import javax.print.event.PrintJobListener;
import javax.swing.*;
import java.util.Arrays;
import java.util.Random;

public class Snake extends Thread{
    Random rand = new Random();
                           
    public int direction;
    public int currentDirection;
    public int[] applePos = new int[2];  
    public int[][] bodyPartPos = new int[1][2];
    public JLabel[] snakeBody = new JLabel[1];                    
    public int testRunner = 0;
    public static boolean running = false;
    public static boolean dead = false;
    public static boolean preformUpdate = false;
    public boolean ateApple = false;                          
    public JLabel apple = new JLabel();
    public int[] headPos = new int[2]; 
    public int[] endPos = new int[2];

    Snake () {
        direction = 0;
    }

    public void Move() {
        if (running) {
            if (direction == 0) {
                headPos[1]--;
            }
    
            if (direction == 1) {
                headPos[0]++;
            }
    
            if (direction == 2) {
                headPos[1]++;
            }
    
            if (direction == 3) {
                headPos[0]--;
            }
            currentDirection = direction;
        }

        checkForCollision();

        if(!dead && running) {

            endPos[0] = bodyPartPos[bodyPartPos.length - 1][0];
            endPos[1] = bodyPartPos[bodyPartPos.length - 1][1];
    
            for (int j = bodyPartPos.length - 1; 0 < j; j--) {
                bodyPartPos[j][0] = bodyPartPos[j - 1][0];
                bodyPartPos[j][1] = bodyPartPos[j - 1][1];
    
                snakeBody[j].setBounds(bodyPartPos[j][0] * 30, bodyPartPos[j][1] * 30, 30, 30);
            }
    
    
    
            bodyPartPos[0][0] = headPos[0];
            bodyPartPos[0][1] = headPos[1];
            snakeBody[0].setBounds(bodyPartPos[0][0] * 30, bodyPartPos[0][1] * 30, 30, 30);
    
    
            // Eats an apple
            if (headPos[0] == applePos[0] && headPos[1] == applePos[1]) {
    
                JLabel[] b = Arrays.copyOf(snakeBody, snakeBody.length + 1);
                b[b.length - 1] = new JLabel();
                b[b.length - 1].setIcon(new ImageIcon("./img/snake.png"));
                b[b.length - 1].setBounds(endPos[0] * 30, endPos[1] * 30, 30, 30);
                snakeBody = b;
    
                int[][] p = Arrays.copyOf(bodyPartPos, bodyPartPos.length + 1);
                p[p.length - 1] = new int[2];
                p[p.length -1][0] = endPos[0];
                p[p.length -1][1] = endPos[1];
                
                bodyPartPos = p;
                
                ateApple = true;
    
                GenerateApplePos();
            }

        }
        
    }

    public void checkForCollision() {
        if (headPos[0] > 20 || headPos[0] < 1 || headPos[1] > 20 || headPos[1] < 1) {
            dead = true;
        }

        for (int h = 2; h < bodyPartPos.length; h++) {
            if ( bodyPartPos[h][0] == headPos[0] && bodyPartPos[h][1] == headPos[1] ) {
                dead = true;
            }
        }
    }

    public void GenerateApplePos() {
        boolean emptySquare;
        do {
            int selectedX = rand.nextInt(20) + 1;
            int selectedY = rand.nextInt(20) + 1;
   
            emptySquare = true;
            for (int j = bodyPartPos.length - 1; 0 < j; j--) {
                if (bodyPartPos[j][0] == selectedX && bodyPartPos[j][1] == selectedY) {
                    emptySquare = false;
                }
            }
    
            if (emptySquare) {
                applePos[0] = selectedX;
                applePos[1] = selectedY;
    
                apple.setBounds(selectedX * 30, selectedY * 30, 30, 30);
            } else {
                System.out.println("Check Failed");
            }
            
        } while (!emptySquare);
        
    }

    public void checkApplePos() {
        for (int h = 2; h < bodyPartPos.length; h++) {
            if ( bodyPartPos[h][0] == applePos[0] && bodyPartPos[h][1] == applePos[1] ) {
                GenerateApplePos();
            }
        }
    }

    public void run() {
        while (true) {
            if (running) {
                try {
                    Thread.sleep(150);
                    preformUpdate = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
}
