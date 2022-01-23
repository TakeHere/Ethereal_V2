package fr.takehere.ethereal.utils;

public class Rectangle {

    public Vector2 location;
    public Dimension dimension;

    public Rectangle(Vector2 location, Dimension dimension) {
        this.location = location;
        this.dimension = dimension;
    }

    public Rectangle(double x, double y, int width, int height) {
        this.location = new Vector2(x,y);
        this.dimension = new Dimension(width, height);
    }
}
