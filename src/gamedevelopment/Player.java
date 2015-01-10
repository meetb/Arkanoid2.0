package gamedevelopment;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.*;

public class Player extends GameObject {

    private double xSpeed;
    private RoundRectangle2D rectangle;
    private int xBend;
    private int yBend;
    private int lives;
    private int pUp;

    public Player(int x, int y, int width, int height, int xBend, int yBend, Color color, double xSpeed, int lives, int pUp) {
        super(x, y, width, height, color);
        getHeight();
        this.xSpeed = xSpeed;
        this.xBend = xBend;
        this.yBend = yBend;
        this.lives = lives;
        this.pUp = pUp;
        rectangle = new RoundRectangle2D.Double(x, y, width, height, xBend, yBend);
    }

    public Rectangle2D getPlatform() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    public boolean checkCollisionPowerUp(PowerUp powerUp) {
        return rectangle.intersects(powerUp.getPlatform());
    }
    
    public int getPUp() {
        return pUp;
    }
    
    public void setPUp(int pUp) {
        this.pUp = pUp;
    }

    public double getXSpeed() {
        return xSpeed;
    }

    public void setXSpeed(double speed) {
        xSpeed = speed;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void update(GameDevelopment panel) {
        // Left wall impact
        if (x + xSpeed < 15) {
            xSpeed = 0;
        } // Right wall impact
        else if (x + xSpeed > panel.getWidth() - width - 16) {
            xSpeed = 0;
        } // No horizontal impact
        else {
            x += xSpeed;
        }
    }

    public void paintComponent(Graphics2D g2) {
        rectangle.setFrame(x, y, width, height);
        g2.setColor(color);
        g2.fill(rectangle);
        g2.draw(rectangle);
    }
}
