package com.kuma;

import lombok.Data;

import java.awt.*;

@Data
public class Ball {

    private int x, y, diameter, speedX, speedY;
    private Color color;

    public Ball(int x, int y, int diameter, int speedX, int speedY, Color color) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.speedX = speedX;
        this.speedY = speedY;
        this.color = color;
    }

    public void move() {
        x += speedX;
        y += speedY;
    }

    public void reverseXSpeed() {
        speedX = -speedX;
    }

    public void reverseYSpeed() {
        speedY = -speedY;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, diameter, diameter);
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, diameter, diameter);
    }

}
