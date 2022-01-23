package fr.takehere.ethereal;

import fr.takehere.ethereal.utils.ConsoleColor;
import javafx.application.Application;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public abstract class Game extends GameScene{
    public static float ETHEREAl_VERSION = 1.3f;

    public static String title;
    public static int height, width;
    public static GameScene currentScene;
    public static float gravity = 0.1f;
    public static List<Runnable> runNextFrame = new ArrayList<>();
    public static List<Runnable> addNextFrame =  new ArrayList<>();
    public static boolean titleBar;

    public Game(String title, int width, int height, boolean titleBar) {
        System.out.println(ConsoleColor.YELLOW + "------------< Ethereal Engine v" + ETHEREAl_VERSION + " >------------");
        System.out.println("");
        System.out.println(ConsoleColor.RED + "This game is made with Ethereal Engine, a game framework for javafx");
        System.out.println("Made by https://github.com/TakeHere");
        System.out.println("");
        System.out.println(ConsoleColor.YELLOW + "------------< Ethereal Engine v" + ETHEREAl_VERSION + " >------------" + ConsoleColor.RESET);

        this.title = title;
        this.width = width;
        this.height = height;
        this.titleBar = titleBar;

        currentScene = this;
    }

    public void launch(){
        Application.launch(GameWindow.class, null);
    }

    public void setCurrentScene(GameScene currentScene) {
        Game.currentScene = currentScene;
        currentScene.init();
    }

    public static void runNextFrame(Runnable run){
        addNextFrame.add(run);
    }

    public boolean isPressed(int keyCode){
        return GameWindow.pressedKeys.contains(keyCode);
    }

    public GraphicsContext getGc(){
        return GameWindow.gc;
    }
}
