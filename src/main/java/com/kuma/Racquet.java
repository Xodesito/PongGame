package com.kuma;

import lombok.Data;

import java.awt.*;

@Data
public class Racquet {

    private int x, y, width, height, speed;
    private Color color;

    public Racquet(int x, int y, int width, int height, int speed, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed; // Speed of the racquet
        this.color = color;
    }

    public void moveUp() {
        if (y - speed >= 0) {
            y -= speed;
        }
    }

    public void moveDown() {
        if (y + height + speed <= PongGame.HEIGHT) {
            y += speed;
        }
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public void boostSpeed(int speed) {
        this.speed = speed;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}
