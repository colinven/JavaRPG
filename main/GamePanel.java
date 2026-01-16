package main;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3;

    final int tileSize = originalTileSize * scale; //48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; //768 pixels wide
    final int screenHeight = tileSize * maxScreenRow; //576 pixels wide

    int fps = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    //SET DEFAULT PLAYER POSITION
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;


    //CONSTRUCTOR
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 /fps; //0.01666 seconds
        double nextDrawTime =  System.nanoTime() + drawInterval; //current time + 0.01666 seconds

        while (gameThread != null) {

            //1. UPDATE: update information on character coordinates
            update();
            //2. PAINT: paint component on screen with updated coordinates
            repaint(); //calls paintComponent() method

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;

                if(remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void update() {
        if(keyH.upPressed){
            playerY -= playerSpeed;
        }
        else if(keyH.downPressed){
            playerY += playerSpeed;
        }
        else if(keyH.rightPressed){
            playerX += playerSpeed;
        }
        else if(keyH.leftPressed){
            playerX -= playerSpeed;
        }
    }
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.WHITE);
        g2. fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose();
    }
}
