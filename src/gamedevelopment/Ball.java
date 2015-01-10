package gamedevelopment;

import java.awt.*;
import java.awt.geom.*;

public class Ball extends GameObject {

    private int radius;
    private int xSpeed;
    private int ySpeed;
    private Ellipse2D circle;
    private boolean death;

    public Ball(int x, int y, int radius, Color color, int xSpeed, int ySpeed) {
        super(x, y, radius, radius, color);
        this.radius = radius;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.death = false;
        this.circle = new Ellipse2D.Double(x, y, radius, radius);
    }

    public void reverseXSpeed() {
        xSpeed = xSpeed * -1;
    }

    public void reverseYSpeed() {
        ySpeed = ySpeed * -1;
    }

    public void setYSpeed(int speed) {
        ySpeed = speed;
    }

    public int getYSpeed() {
        return ySpeed;
    }

    public void setXSpeed(int speed) {
        xSpeed = speed;
    }

    public int getXSpeed() {
        return xSpeed;
    }

    public boolean getDeath() {
        return death;
    }

    public void setDeath(boolean death) {
        this.death = death;
    }

    public boolean checkCollisionBlock(Blocks platform) {
        return circle.intersects(platform.getPlatform());
    }

    public boolean checkCollisionPlayer(Player platform) {
        return circle.intersects(platform.getPlatform());
    }

    public boolean checkCollisionPowerUp(PowerUp powerUp) {
        return circle.intersects(powerUp.getPlatform());
    }

    public void collisions(GameObject obj, int dChange) {
        //bottom
        if (circle.intersects(new Rectangle2D.Double(obj.x, obj.y + obj.height - 1, obj.width, 1))) {
            y = obj.y + obj.height + dChange;
        }
        //top
        else if (circle.intersects(new Rectangle2D.Double(obj.x, obj.y, obj.width, 1))) {
            y = obj.y - dChange;
        }
        //left side
        else if (circle.intersects(new Rectangle2D.Double(obj.x, obj.y + 1, 1, obj.height - 1))) {            
            reverseXSpeed();
            x = obj.getX() - dChange;
        }
        //right side
        else if (circle.intersects(new Rectangle2D.Double(obj.x + obj.width - 1, obj.y + 1, 1, obj.height - 1))){
            reverseXSpeed();
            x = obj.getX() + obj.getWidth() + dChange;            
        }
    }

    public void update(GameDevelopment panel) {
        if (x + xSpeed < 16) {
            x = 16;
            reverseXSpeed();
        } else if (x + xSpeed > panel.getWidth() - radius - 16) {
            x = panel.getWidth() - radius - 16;
            reverseXSpeed();
        } else {
            x += xSpeed;
        }
        if (y + ySpeed < 0) {
            y = 0;
            reverseYSpeed();
        } else if (y + ySpeed > panel.getHeight() - radius - 76) {
            y = panel.getHeight() - radius - 1;
            reverseYSpeed();
            death = true;
        } else {
            y += ySpeed;
        }
    }

    public void paintComponent(Graphics2D g2) {
        circle.setFrame(x, y, radius, radius);
        g2.setColor(color);
        g2.fill(circle);
        g2.draw(circle);
    }
}
