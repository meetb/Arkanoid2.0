package gamedevelopment;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

// This class also extends the GameObject abstract parent class and it ipmlements the abstract functions.
public class PowerUp extends GameObject {

    private Rectangle2D rectangle;
    private Color colour;
    private double speed;
    private int place;
    private int type;
    private boolean collision;
    private String[] letters = {"E", "S", "F", "M", "L"};

    // type: 0 = Enlarge, 1 = slow, 2 = speed, 3 = multi, 4 = life; These are the types of PowerUps.
    public PowerUp(int x, int y, int width, int height, double speed, int place, int type, Color colour) {
        super(x, y, width, height, colour);
        this.speed = speed;
        this.place = place;
        this.type = type;
        this.collision = false;
        rectangle = new Rectangle2D.Double(x, y, width, height);
    }

    // Whenever the powerup is released from a block, it falls and collides with the playerBoard so this updates the position of the powerUp after it starts falling.
    public void update(GameDevelopment panel) {
        y += speed;
    }

    public int getType() {
        return type;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setCollision() {
        collision = true;
    }

    public int getPlace() {
        return place;
    }

    public Rectangle2D getPlatform() {
        return rectangle;
    }

    // Paints the component and it also adds the Type name in the last name on the powerUp for visuals for the player.
    public void paintComponent(Graphics2D g2) {
        rectangle.setFrame(x, y, width, height);
        g2.setColor(color);
        g2.fill(rectangle);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.draw(rectangle);
        if (collision) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.drawString(letters[type], x + 11, y + 11);
        }
    }
}
