package fr.takehere.ethereal;

import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;

public class GameCamera {

    private static GameCamera instance;

    private Camera camera;

    private Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
    private Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
    private Rotate zRotate = new Rotate(0, Game.width/2,Game.height/2,0, Rotate.Z_AXIS);

    private GameCamera() {
        camera = new PerspectiveCamera();
        camera.getTransforms().addAll(xRotate, yRotate, zRotate);
    }

    public static GameCamera get(){
        if(instance == null)
            instance = new GameCamera();
        return instance;
    }

    public void addTranslateX(double translate){
        camera.setTranslateX(camera.getTranslateX() + translate);
    }

    public void addTranslateY(double translate){
        camera.setTranslateY(camera.getTranslateY() + translate);
    }

    public void addZoom(double translate){
        camera.setTranslateZ(camera.getTranslateZ() + translate);
    }

    public void addRotationZ(double rotation){
        zRotate.setAngle(zRotate.getAngle() + rotation);
    }


    public void setTranslateX(double translate){
        camera.setTranslateX(translate);
    }

    public void setTranslateY(double translate){
        camera.setTranslateY(translate);
    }

    public void setZoom(double translate){
        camera.setTranslateZ(translate);
    }

    public void setRotationZ(double rotation){
        zRotate.setAngle(rotation);
    }

    public Camera getCamera() {
        return camera;
    }
}
