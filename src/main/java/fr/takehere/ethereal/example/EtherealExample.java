package fr.takehere.ethereal.example;

import fr.takehere.ethereal.Game;
import fr.takehere.ethereal.GameCamera;
import fr.takehere.ethereal.GameWindow;
import fr.takehere.ethereal.components.Actor;
import fr.takehere.ethereal.components.Pawn;
import fr.takehere.ethereal.utils.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EtherealExample extends Game {

    Pawn pawn;
    ParticleGenerator particleGenerator;
    Actor actor;

    @Override
    public void init() {
        RessourcesManager.addImage("placeholder", "placeholder.png");
        RessourcesManager.addSound("music", "/music/song.mp3", getClass());

        pawn = new Pawn("Pawn", new Vector2(100,100), new Dimension(100,100), RessourcesManager.getImage("placeholder"), this);
        particleGenerator = new ParticleGenerator(new Vector2(500,100), new Dimension(5,5), null, true, 100, 1,5, 1000, this);
        actor = new Actor("Actor", new Vector2(200,200), new Dimension(50,50), RessourcesManager.getImage("placeholder"), this);
        actor.gravity = true;

        //SoundUtils.playSound("music", 0.3f, true);

        GameCamera camera = GameCamera.get();

        GameWindow.scene.setOnKeyPressed((KeyEvent e) -> {
            KeyCode code = e.getCode();
            switch (code) {
                case LEFT:
                    camera.addRotationZ(10);
                    break;
                case RIGHT:
                    camera.addRotationZ(-10);
                    break;
                case UP:
                    camera.addTranslateY(10);
                    break;
                case DOWN:
                    camera.addTranslateY(-10);
                    break;
                case Z:
                    camera.addZoom(10);
                    break;
                case S:
                    camera.addZoom(-10);
                    break;
                case HOME:
                    camera.setTranslateX(0);
                    camera.setTranslateY(0);
                    camera.setZoom(0);
                    camera.setRotationZ(0);
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void gameLoop(double deltaTime) {
        GraphicsContext gc = GameWindow.gc;
        //particleGenerator.location = MathUtils.randomDirection().multiply(200).add(new Vector2(500,500));
        particleGenerator.generate();
    }

    public EtherealExample(String title, int width, int height) {
        super(title, width, height, true);
    }
    public static void main(String[] args) {
        new EtherealExample("EtherealFX Example", 1000,1000).launch();
    }
}
