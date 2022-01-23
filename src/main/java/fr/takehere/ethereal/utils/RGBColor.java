package fr.takehere.ethereal.utils;

import javafx.scene.paint.Color;

public class RGBColor {

    public Color color;

    public RGBColor(double r, double g, double b) {
        double xr = (r / 255);
        double xg = (g / 255);
        double xb = (b / 255);

        this.color = new Color(xr, xg, xb, 1);
    }
}
