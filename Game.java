// importing awt libraries  
import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.lang.Thread;

import java.util.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicBorders.RolloverButtonBorder;

import java.util.Random;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.lang.Thread;

public class Game extends JFrame implements KeyListener, ActionListener {    
    Snake daSnake = new Snake();
    
    JLabel score = new JLabel("Score: 0");

    JPanel bg = new JPanel();

    JLabel shit = new JLabel("Hejsan");

    JLabel frame = new JLabel();

    JPanel restartPanel = new JPanel();
    JLabel restartPanelLabel = new JLabel();
    JButton exitGameButton = new JButton();
    JButton restartGameButton = new JButton();


    // int bodyStartY = 0;

    Game() {    
        score.setBounds(30, 659, 100, 30);
        score.setForeground(Color.WHITE);
        score.setHorizontalAlignment(SwingConstants.LEFT);
        frame.add(score, 2, 0);

        restartPanel.setBackground(new Color(0,0,0));
        restartPanel.setBounds(150, 175, 374, 246);
        restartPanel.setLayout(null);

        exitGameButton.addActionListener(this);
        exitGameButton.setFocusable(false);

        restartGameButton.setText("Restart");
        restartGameButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        restartGameButton.addActionListener(this);
        restartGameButton.setBounds(60, 90, 254, 50);
        restartGameButton.setForeground(Color.WHITE);
        restartGameButton.setBackground(Color.BLACK);
        restartGameButton.setFocusable(false);
        restartGameButton.setFont(new Font("Arial", Font.BOLD, 30));
        restartPanel.add(restartGameButton);

        exitGameButton.setText("Exit");
        exitGameButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        exitGameButton.addActionListener(this);
        exitGameButton.setBounds(60, 160, 254, 50);
        exitGameButton.setForeground(Color.WHITE);
        exitGameButton.setBackground(Color.BLACK);
        exitGameButton.setFocusable(false);
        exitGameButton.setFont(new Font("Arial", Font.BOLD, 30));
        restartPanel.add(exitGameButton);

        restartPanelLabel.setFont(new Font("Arial", Font.BOLD, 40));
        restartPanelLabel.setForeground(Color.WHITE);
        restartPanelLabel.setText("Game Over");
        restartPanelLabel.setHorizontalAlignment(JLabel.CENTER);
        restartPanelLabel.setVerticalAlignment(JLabel.CENTER);
        restartPanelLabel.setBounds(0,0, 374, 70);

        restartPanel.add(restartPanelLabel);


        daSnake.start();

        for (int i = 0; i < daSnake.snakeBody.length; i++) {
            daSnake.snakeBody[i] = new JLabel(); 
            daSnake.snakeBody[i].setIcon(new ImageIcon("./img/snake.png"));
            daSnake.snakeBody[i].setBounds(300, i * 30 + 300, 30, 30);
            daSnake.bodyPartPos[i][0] = 10;
            daSnake.bodyPartPos[i][0] = 10;
            daSnake.bodyPartPos[i][1] = i * 1 + 10;
            // System.out.println("WWWWWAAAAAAAAAA");
            bg.add(daSnake.snakeBody[i]);
        }

        daSnake.headPos[0] = 10;
        daSnake.headPos[1] = 10;

        daSnake.apple.setIcon(new ImageIcon("./img/apple.png"));
        daSnake.GenerateApplePos();

        frame.setIcon(new ImageIcon("./img/frame1.png"));
        frame.setBounds(0,0, 660, 690);
        

        bg.add(daSnake.apple);

        bg.add(frame);

        // this.add(shit);

        bg.setLayout(null);
        bg.setBorder(new EmptyBorder(0, 125, 25, 100));
        bg.setBackground(new Color(150, 150, 150));
        bg.setPreferredSize(new Dimension(660, 690));
        bg.setBounds(0, 0, 674, 770);
   
        this.setTitle("Snake Game, eller typ");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.addKeyListener(this);
        this.setSize(674, 726);
        this.setPreferredSize(new Dimension(674, 726));
        this.getContentPane().setBackground(new Color(0, 0, 0));
        
        this.add(bg);

        this.pack();
        this.setVisible(true);
        
        Runnable r2 = () -> {  
            while (true) {
                try {
                    Thread.sleep(25);
                    if (daSnake.preformUpdate) {
                        score.setText("Score: " + (daSnake.snakeBody.length - 1));
                        daSnake.preformUpdate = false;
                        daSnake.Move();

                        if (daSnake.ateApple == true) {
                            // System.out.println("WOOWO");
                            bg.add(daSnake.snakeBody[daSnake.snakeBody.length - 1]);
                            daSnake.ateApple = false;
                        }

                        daSnake.checkApplePos();
                        this.revalidate();
                        this.repaint(); 
                    }

                    if (daSnake.dead || daSnake.bodyPartPos.length == 20 * 20) {
                        bg.add(restartPanel, 2, 0);

                        if (daSnake.bodyPartPos.length == 20 * 20) {
                            restartPanelLabel.setText("Well Done!");
                        } else {
                            restartPanelLabel.setText("Game Over");
                        }
                        
                        // this.removeKeyListener(this);
                        this.revalidate();
                        this.repaint();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // System.out.println(daSnake.testRunner);
            } 
        };
        Thread t = new Thread(r2);
        t.start();
    }    

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitGameButton) {
            System.exit(0);
        }

        if (e.getSource() == restartGameButton) {
            bg.remove(daSnake.apple);

            // System.out.println("AGIHUJOPKÅL");
            for (int i = 0; i < daSnake.snakeBody.length; i++) {
                bg.remove(daSnake.snakeBody[i]);
            }

            daSnake = new Snake();
            // daSnake.start();
            daSnake.running = false;
            daSnake.preformUpdate = false;
            daSnake.dead = false;

            for (int i = 0; i < daSnake.snakeBody.length; i++) {
                daSnake.snakeBody[i] = new JLabel(); 
                daSnake.snakeBody[i].setIcon(new ImageIcon("./img/snake.png"));
                daSnake.snakeBody[i].setBounds(300, i * 30 + 300, 30, 30);
                daSnake.bodyPartPos[i][0] = 10;
                daSnake.bodyPartPos[i][0] = 10;
                daSnake.bodyPartPos[i][1] = i * 1 + 10;
                // System.out.println("WWWWWAAAAAAAAAA");
                bg.add(daSnake.snakeBody[i]);
            }

            daSnake.headPos[0] = 10;
            daSnake.headPos[1] = 10;

            daSnake.running = false;
            daSnake.preformUpdate = false;
            daSnake.dead = false;

            daSnake.apple.setIcon(new ImageIcon("./img/apple.png"));
            daSnake.GenerateApplePos();

           
            bg.add(daSnake.apple);

            // daSnake.start();
            System.out.println(daSnake.currentDirection);
            daSnake.currentDirection = 0;

            bg.remove(restartPanel);

            addKeyListener(this);
            bg.addKeyListener(this);
            restartPanel.addKeyListener(this);
            
            

            this.revalidate();
            this.repaint();
            // daSnake = new Snake();
        }
    }   
 
    public void keyPressed (KeyEvent e) {  
        System.out.println("Vaaa"); 
        // System.out.println("någon knapp"); 
        if (e.getKeyCode() == KeyEvent.VK_UP && daSnake.currentDirection != 2) {
            daSnake.running = true;
            System.out.println("upp!");
            daSnake.direction = 0; // 0 = Up
        }  

        if (e.getKeyCode() == KeyEvent.VK_RIGHT && daSnake.currentDirection != 3) {
            daSnake.running = true;
            System.out.println("Höger");
            daSnake.direction = 1; // 1 = right
        }  

        if (e.getKeyCode() == KeyEvent.VK_DOWN && daSnake.currentDirection != 0) {
            daSnake.running = true;
            System.out.println("Ner!");
            daSnake.direction = 2; // 2 = Down
        }  

        if (e.getKeyCode() == KeyEvent.VK_LEFT && daSnake.currentDirection != 1) {
            daSnake.running = true;
            System.out.println("Vänster!");
            daSnake.direction = 3; // 0 = Left
        }  
    }    

    public void keyReleased (KeyEvent e) {    
    
    }    

    public void keyTyped (KeyEvent e) {    
          
    }    

    // public void run() {

    // }
 
}   