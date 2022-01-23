package fr.takehere.ethereal;

import fr.takehere.ethereal.components.Actor;
import fr.takehere.ethereal.components.Pawn;
import fr.takehere.ethereal.utils.Dimension;
import fr.takehere.ethereal.utils.Rectangle;
import fr.takehere.ethereal.utils.Vector2;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class GameWindow extends Application{

    public static GraphicsContext gc;
    public static Scene scene;
    public static Stage stage;
    public static StackPane frontUi;
    public static double deltaTime = 1;
    public static int lastFps = 60;

    public static List<Integer> pressedKeys = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.UNDECORATED);

        Camera camera = GameCamera.get().getCamera();

        frontUi = new StackPane();

        scene = new Scene(frontUi);
        scene.setCamera(camera);

        stage.setScene(scene);
        Canvas canvas = new Canvas(Game.width, Game.height);
        frontUi.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();

        Game.currentScene.init();
        gameLoop();

        stage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });

        scene.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (!pressedKeys.contains(ke.getCode().getCode()))
                pressedKeys.add(ke.getCode().getCode());
        });

        scene.addEventFilter(KeyEvent.KEY_RELEASED, ke -> {
            pressedKeys.remove(Integer.valueOf(ke.getCode().getCode()));
        });

        stage.widthProperty().addListener((obs, oldVal, newVal) -> Game.width = newVal.intValue());
        stage.heightProperty().addListener((obs, oldVal, newVal) -> Game.height = newVal.intValue());

        stage.show();

        this.stage = stage;
    }

    public void gameLoop(){
        new AnimationTimer() {
            int targetFps = 60;
            int fps;
            float deltaTime = 1;
            long lastTime = System.currentTimeMillis();

            public void handle(long currentNanoTime){
                gc.clearRect(0, 0, Game.width, Game.height);

                Game.currentScene.gameLoop(deltaTime);
                GameWindow.deltaTime = deltaTime;

                try {
                    Game.runNextFrame = new ArrayList<>(Game.addNextFrame);
                    for (Runnable runnable : Game.runNextFrame) {
                        if (runnable != null){
                            runnable.run();
                        }
                    }
                    Game.runNextFrame.clear();
                    Game.addNextFrame.clear();
                    actorMovements();
                    pawnRendering();
                }catch (ConcurrentModificationException e){e.printStackTrace();}

                //--------< Display FPS >--------
                fps++;
                if (System.currentTimeMillis() - lastTime > 1000){
                    lastFps = fps;
                    stage.setTitle(Game.title + " | fps: " + fps);
                    lastTime += 1000;
                    deltaTime = (float) targetFps / fps;
                    fps = 0;
                }
            }
        }.start();
    }

    private void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    public void pawnRendering(){
        GraphicsContext gc = GameWindow.gc;

        for (Pawn pawn : Pawn.pawns) {
            if (pawn != null){
                if (Game.currentScene.equals(pawn.scene)){
                    //Draw pawn
                    double anchorX = pawn.location.x + pawn.dimension.width / 2;
                    double anchorY = pawn.location.y + pawn.dimension.height / 2;

                    if (pawn.visible){
                        gc.save();
                        rotate(gc, pawn.getRotation(), anchorX, anchorY);
                        gc.drawImage(pawn.texture, pawn.location.x, pawn.location.y, pawn.dimension.width, pawn.dimension.height);
                        gc.restore();
                    }

                    //Get Rotated Points
                    Rotate r = new Rotate(pawn.getRotation(), anchorX, anchorY);

                    Point2D[] basePoints = {
                            new Point2D(pawn.location.x, pawn.location.y),
                            new Point2D(pawn.location.x + pawn.dimension.width, pawn.location.y),
                            new Point2D(pawn.location.x, pawn.location.y + pawn.dimension.height),
                            new Point2D(pawn.location.x + pawn.dimension.width, pawn.location.y + pawn.dimension.width)
                    };

                    List<Point2D> transformedPoints = new ArrayList<>();

                    for (Point2D basePoint : basePoints) {
                        Point2D transformedPoint = r.transform(basePoint);
                        transformedPoints.add(transformedPoint);
                    }

                    //Get bounding box
                    Vector2 tl = new Vector2(transformedPoints.get(0).getX(), transformedPoints.get(0).getY());
                    Vector2 tr = new Vector2(transformedPoints.get(1).getX(), transformedPoints.get(1).getY());
                    Vector2 bl = new Vector2(transformedPoints.get(2).getX(), transformedPoints.get(2).getY());
                    Vector2 br = new Vector2(transformedPoints.get(3).getX(), transformedPoints.get(3).getY());

                    float minX = (float) Math.min(tl.x, Math.min(tr.x, Math.min(bl.x, br.x)));
                    float maxX = (float) Math.max(tl.x, Math.max(tr.x, Math.max(bl.x, br.x)));
                    float minY = (float) Math.min(tl.y, Math.min(tr.y, Math.min(bl.y, br.y)));
                    float maxY = (float) Math.max(tl.y, Math.max(tr.y, Math.max(bl.y, br.y)));
                    Vector2 min = new Vector2(minX, minY);
                    Vector2 max = new Vector2(maxX, maxY);

                    pawn.boundingBox = new Rectangle(min, new Dimension((int) (max.x - min.x),(int) (max.y - min.y)));
                }
            }
        }
    }

    public void actorMovements(){
        for (Actor actor : Actor.actors) {
            if (actor != null){
                if (actor.scene.equals(Game.currentScene)){
                    //------< Apply velocity >------
                    if (actor.gravity){
                        actor.velocity = actor.velocity.add(new Vector2(0, Game.gravity));
                    }

                    actor.location = actor.location.add(actor.velocity);
                }
            }
        }
    }
}