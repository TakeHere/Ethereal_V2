package fr.takehere.ethereal.utils;

import fr.takehere.ethereal.components.Pawn;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class MathUtils {
    public static Random random = new Random();

    public static double mapDecimal(double val, double in_min, double in_max, double out_min, double out_max) {
        return (val - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    public static double invertRadianAngle(double angle){
        return (angle + Math.PI) % (2 * Math.PI);
    }

    public static int randomNumberBetween(int min, int max){
        return random.nextInt(max - min + 1) + min;
    }

    public static boolean isBetween(float value, float min, float max){
        return value < max && value > min;
    }

    public static Vector2 getCenterOfRectangle(Rectangle rectangle){
        return new Vector2(rectangle.location.x + rectangle.dimension.width/2, rectangle.location.y + rectangle.dimension.height/2);
    }

    public static boolean areRectangleSame(Rectangle rec1, Rectangle rec2){
        if (rec1.location.x == rec1.location.x && rec1.location.y == rec2.location.y && rec1.dimension.width == rec2.dimension.height && rec1.dimension.height == rec2.dimension.height){
            return true;
        }
        return false;
    }

    public static void drawImageCenter(Dimension zone, Image image, Dimension size, GraphicsContext gc){
        gc.drawImage(image, (zone.width/2) - (size.width/2), (zone.height/2) - (size.height/2), size.width, size.height);
    }

    public static Rectangle getRectangleOfPawn(Pawn pawn){
        return new Rectangle(pawn.location, new Dimension(pawn.dimension.width, pawn.dimension.height));
    }

    public static Vector2 getCenterOfPawn(Pawn pawn){
        return new Vector2(pawn.location.x + pawn.dimension.width/2, pawn.location.y + pawn.dimension.width/2);
    }

    public static Vector2 rotateVector(Vector2 vector,double angle) {
        vector = vector.normalize();
        angle = Math.toRadians(angle);

        float x1 = (float)(vector.x * Math.cos(angle) - vector.y * Math.sin(angle));

        float y1 = (float)(vector.x * Math.sin(angle) + vector.y * Math.cos(angle)) ;

        return new Vector2(x1, y1);

    }

    public static Vector2 getVectorFromRotation(double rotation){
        Vector2 forwardVector = new Vector2(
                Math.sin(Math.toRadians(rotation)),
                Math.cos(Math.toRadians(rotation))
        ).normalize();

        return forwardVector;
    }

    public static Vector2 randomDirection(){
        return new Vector2(randomNumberBetween(-1000,1000), randomNumberBetween(-1000,1000)).normalize();
    }

    public static boolean isColliding(Rectangle rec1, Rectangle rec2){
        return rec1.location.x < rec2.location.x + rec2.dimension.width &&
                rec1.location.x + rec1.dimension.width > rec2.location.x &&
                rec1.location.y < rec2.location.y + rec2.dimension.height &&
                rec1.location.y + rec1.dimension.height > rec2.location.y;
    }
}
