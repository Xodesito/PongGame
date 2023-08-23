package com.kuma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PongGame extends JPanel implements ActionListener {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static final int BALL_DIAMETER = 20;
    private static final int RACKET_WIDTH = 10;
    private static final int RACKET_HEIGHT = 100;
    private static int RACKET_SPEED = 5;
    private double RACKET_SPEED_BOOST = 1.0;
    private static int POINTS = 0;
    private static int LIVES = 5;
    private static int POINTS_TO_BOOST = 1;

    private final Ball ball;
    private final Racquet racquetPlayer;
    private final Racquet racquetComputer;
    private final Timer timer;

    public PongGame() {
        ball = new Ball(WIDTH / 2 - BALL_DIAMETER / 2, HEIGHT / 2 - BALL_DIAMETER / 2, BALL_DIAMETER, 3, 1, Color.BLACK);
        racquetPlayer = new Racquet(0, HEIGHT / 2 - RACKET_HEIGHT / 2, RACKET_WIDTH, RACKET_HEIGHT, RACKET_SPEED, Color.BLACK);
        racquetComputer = new Racquet(WIDTH - RACKET_WIDTH, HEIGHT / 2 - RACKET_HEIGHT / 2, RACKET_WIDTH, RACKET_HEIGHT, RACKET_SPEED, Color.BLACK);

        setupKeyBindings();

        timer = new Timer(10, this);
        timer.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        ball.move();
        if (ball.getX() < 0 || ball.getX() > WIDTH - BALL_DIAMETER) {
            ball.reverseXSpeed();
            System.out.println("Player lives: " + LIVES);
            LIVES--;
        }
        if (ball.getY() < 0 || ball.getY() > HEIGHT - BALL_DIAMETER) {
            ball.reverseYSpeed();
        }

        if (ball.getBounds().intersects(racquetPlayer.getBounds())) {
            ball.reverseXSpeed();
            POINTS++;
            if (POINTS % POINTS_TO_BOOST == 0) {
                RACKET_SPEED = (int) (RACKET_SPEED * RACKET_SPEED_BOOST);
                POINTS_TO_BOOST *= 2;
                RACKET_SPEED_BOOST += 0.5;
                racquetPlayer.boostSpeed(RACKET_SPEED);

            }
        }

        if (ball.getBounds().intersects(racquetComputer.getBounds())) {
            ball.reverseXSpeed();
        }

        if (racquetComputer.getY() + RACKET_HEIGHT / 2 < ball.getY()) {
            racquetComputer.moveDown();
        } else {
            racquetComputer.moveUp();
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ball.draw(g);
        racquetPlayer.draw(g);
        racquetComputer.draw(g);
        g.drawString("Points: " + POINTS, 20, 20);
        g.drawString("Lives: " + LIVES, 20, 40);
        g.drawString("Racket Speed: x" + RACKET_SPEED_BOOST, 20, 60);
        if (LIVES == 0) {
            // show a cool title notifying the player that the game is over
            g.drawString("Game Over!", WIDTH / 2 - 50, HEIGHT / 2 - 50);
            // stop the timer
            timer.stop();
        }
    }

    private void setupKeyBindings() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("UP"), "moveUp");
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                racquetPlayer.moveUp();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                racquetPlayer.moveDown();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "exit");
        actionMap.put("exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game");
        PongGame game = new PongGame();
        frame.add(game);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setVisible(true);
    }


}