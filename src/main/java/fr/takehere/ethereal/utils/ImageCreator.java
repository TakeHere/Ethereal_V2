package fr.takehere.ethereal.utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.util.Arrays;

public class ImageCreator {

    public static Image generateImage(int width, int height, double red, double green, double blue, double opacity) {
        WritableImage img = new WritableImage(width, height);
        PixelWriter pw = img.getPixelWriter();

        int alpha = (int) (opacity * 255) ;
        int r = (int) (red);
        int g = (int) (green);
        int b = (int) (blue);

        int pixel = (alpha << 24) | (r << 16) | (g << 8) | b ;
        int[] pixels = new int[width * height];
        Arrays.fill(pixels, pixel);

        pw.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixels, 0, width);
        return img ;
    }

    public static Image generateRandomColorImage(int width, int height){
        int r = MathUtils.randomNumberBetween(0,255);
        int g = MathUtils.randomNumberBetween(0,255);
        int b = MathUtils.randomNumberBetween(0,255);

        return generateImage(width,height,r,g,b,1);
    }
}
