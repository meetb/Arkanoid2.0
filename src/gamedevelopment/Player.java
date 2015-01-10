package gamedevelopment;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.*;

// This class extends GameObject so it inherits the functionalitites of that parent class and it has to implement the abstract methods on its own.
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
    
    // Shape of object from the geometry class within awt.
    public Rectangle2D getPlatform() {
        return new Rectangle2D.Double(x, y, width, height);
    }
    
    // Checks for collision and it uses the function intersect from the imported class awt.
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
    
    // Takes care of the impacts so it stays within the panel.
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
