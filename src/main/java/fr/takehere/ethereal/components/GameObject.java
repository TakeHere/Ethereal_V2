package fr.takehere.ethereal.components;

import fr.takehere.ethereal.Game;
import fr.takehere.ethereal.GameScene;

import java.util.ArrayList;
import java.util.List;

public class GameObject {

    public String name;
    public GameScene scene;

    public static List<GameObject> gameObjects = new ArrayList<>();

    public GameObject(String name, GameScene scene) {
        this.name = name;
        this.scene = scene;
    }

    public void destroy() {
        Game.runNextFrame(() -> gameObjects.remove(this));
    }
}
