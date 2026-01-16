package main;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args){

        //WINDOW CONFIG
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Java RPG");

        GamePanel gamePanel = new GamePanel();  //implements screen/tile sizing, looping functionality
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null); //centers window
        window.setVisible(true);

        gamePanel.startGameThread(); //start loop
    }
}
