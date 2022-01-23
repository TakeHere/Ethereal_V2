package fr.takehere.ethereal.components;

import fr.takehere.ethereal.Game;
import fr.takehere.ethereal.GameScene;
import fr.takehere.ethereal.utils.Dimension;
import fr.takehere.ethereal.utils.Rectangle;
import fr.takehere.ethereal.utils.Vector2;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends GameObject{

    public Vector2 location;
    public Dimension dimension;
    public Image texture;
    private double rotation = 0;
    public Rectangle boundingBox;
    public boolean visible = true;

    public static List<Pawn> pawns = new ArrayList<>();


    public Pawn(String name, Vector2 location, Dimension dimension, Image texture, GameScene scene) {
        super(name, scene);
        this.location = location;
        this.dimension = dimension;
        this.texture = texture;
        this.boundingBox = new Rectangle(location, dimension);

        pawns.add(this);
    }

    public void destroy(){
        Game.runNextFrame(() -> pawns.remove(this));
        super.destroy();
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = (rotation+36000)%360 ;
    }

    public void addRotation(double rotation){
        setRotation(getRotation() + rotation);
    }
}
