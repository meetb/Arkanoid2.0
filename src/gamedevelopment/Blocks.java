package gamedevelopment;

import java.awt.*;
import java.awt.geom.*;

public class Blocks extends GameObject {

    private Color color;
    private Rectangle2D rectangle;
    private int tolerance;
    private PowerUp powerUp;

    public Blocks(int x, int y, int width, int height, Color color, int tolerance) {
        super(x, y, width, height, color);
        this.color = color;
        this.tolerance = tolerance;
        this.powerUp = powerUp;
        rectangle = new Rectangle2D.Double(x, y, width, height);
    }

    public void setColor(Color colour) {
        color = colour;
    }

    public void setTolerance(int number) {
        tolerance = number;
    }

    public int getTolerance() {
        return tolerance;
    }
    
    public Color getColour(){
        return color;
    }

    public Rectangle2D getPlatform() {
        return rectangle;
    }

    public void update(GameDevelopment panel) {
    }

    
    
    public void paintComponent(Graphics2D g2) {
        rectangle.setFrame(x, y, width, height);
        g2.setColor(color);
        g2.fill(rectangle);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));
        g2.draw(rectangle);
    }
}