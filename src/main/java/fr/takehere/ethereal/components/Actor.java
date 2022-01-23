package fr.takehere.ethereal.components;

import fr.takehere.ethereal.Game;
import fr.takehere.ethereal.GameScene;
import fr.takehere.ethereal.utils.Dimension;
import fr.takehere.ethereal.utils.Vector2;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Actor extends Pawn{

    public boolean gravity = false;
    public Vector2 velocity = new Vector2(0,0);

    public static List<Actor> actors = new ArrayList<>();

    public Actor(String name, Vector2 location, Dimension dimension, Image texture, GameScene scene) {
        super(name, location, dimension, texture, scene);

        actors.add(this);
    }

    public void destroy(){
        Game.runNextFrame(() -> actors.remove(this));
        super.destroy();
    }
}