// This is an abstract parent class with two abstract functions - update and paintComponent.

package gamedevelopment;

import java.awt.*;
import java.awt.geom.*;

public abstract class GameObject {
    // Protected instance variables so player cannot change them outside of this class.
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Color color;

    public GameObject(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    // Various functions to get the instance variables and to set them to a different value.
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    // Abstract functions for the child classes to implement.
    public abstract void update(GameDevelopment panel);

    public abstract void paintComponent(Graphics2D g2);

}
