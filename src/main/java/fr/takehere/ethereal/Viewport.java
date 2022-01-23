package fr.takehere.ethereal;

import fr.takehere.ethereal.components.Actor;
import fr.takehere.ethereal.components.Pawn;
import fr.takehere.ethereal.utils.*;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

import java.util.ArrayList;
import java.util.List;

public class Viewport extends Game{

    Pawn selectedPawn = null;
    public Label selectedPawnLabel;

    @Override
    public void init() {
        RessourcesManager.addImage("placeholder", "placeHolder.png");

        EventHandler<MouseEvent> eventHandler = e -> {
            List<Pawn> clickedPawns = new ArrayList<>();
            for (Pawn pawn : Pawn.pawns) {
                if (MathUtils.isColliding(pawn.boundingBox, new Rectangle(new Vector2(e.getX(),e.getY()),new Dimension(1,1)))){
                    clickedPawns.add(pawn);
                }
            }

            if (clickedPawns.size() != 0){
                selectedPawn = clickedPawns.get(clickedPawns.size()-1);
                tpPawnToMouse(new Vector2(e.getX(), e.getY()), selectedPawn);
            }else {
                selectedPawn = null;
            }
        };

        EventHandler<MouseEvent> dragHandler = mouseEvent -> {
            if (selectedPawn != null){
                tpPawnToMouse(new Vector2(mouseEvent.getX(), mouseEvent.getY()), selectedPawn);
            }
        };

        GameWindow.scene.setOnScroll(scrollEvent -> {
            GameCamera camera = GameCamera.get();
            camera.addZoom(scrollEvent.getDeltaY() / 4);
        });

        GameWindow.scene.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);
        GameWindow.scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, dragHandler);


        new Pawn("Pawn", new Vector2(100,100), new Dimension(100,100), ImageCreator.generateRandomColorImage(50,50), this);
        new Pawn("Pawn3", new Vector2(200,200), new Dimension(100,100), RessourcesManager.getImage("placeholder"), this);
        new Pawn("Pawn2", new Vector2(150,150), new Dimension(100,100), ImageCreator.generateRandomColorImage(50,50), this);
        new Actor("Actor", new Vector2(300,300), new Dimension(50,100), RessourcesManager.getImage("placeholder"), this).gravity = true;

        selectedPawnLabel = new Label("No Pawn Selected");
        selectedPawnLabel.setFont(new Font("Comic Sans MS", 30));

        GameWindow.frontUi.getChildren().add(selectedPawnLabel);
    }

    @Override
    public void gameLoop(double deltaTime) {
        if (selectedPawn != null){
            selectedPawnLabel.setText(selectedPawn.name);

            GraphicsContext gc = GameWindow.gc;

            Vector2 pawnCenter = MathUtils.getCenterOfPawn(selectedPawn);

            int xLength = selectedPawn.dimension.width/2 + 20;
            int yLength = selectedPawn.dimension.height/2 + 20;

            drawArrow(gc, (int) pawnCenter.x,(int) pawnCenter.y,(int) pawnCenter.x + xLength,(int) pawnCenter.y, new RGBColor(173, 42, 42).color);
            drawArrow(gc, (int) pawnCenter.x,(int) pawnCenter.y,(int) pawnCenter.x,(int) pawnCenter.y + yLength, new RGBColor(42, 173, 42).color);
        }else {
            selectedPawnLabel.setText("No Pawn Selected");
        }

        GameWindow.scene.setOnKeyPressed((KeyEvent e) -> {
            if (selectedPawn != null){
                KeyCode code = e.getCode();
                switch (code) {
                    case LEFT:
                        selectedPawn.location.x = selectedPawn.location.x - 5;
                        break;
                    case RIGHT:
                        selectedPawn.location.x = selectedPawn.location.x + 5;
                        break;
                    case UP:
                        selectedPawn.location.y = selectedPawn.location.y - 5;
                        break;
                    case DOWN:
                        selectedPawn.location.y = selectedPawn.location.y + 5;
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void tpPawnToMouse(Vector2 mousePos, Pawn pawn){
        int newX = (int) (mousePos.x - pawn.dimension.width/2);
        int newY = (int) (mousePos.y - pawn.dimension.height/2);

        selectedPawn.location = new Vector2(newX, newY);
    }

    public void drawArrow(GraphicsContext gc, int x1, int y1, int x2, int y2, Color color) {
        gc.save();
        gc.setFill(color);
        gc.setStroke(color);
        gc.setLineWidth(5);

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);

        Transform transform = Transform.translate(x1, y1);
        transform = transform.createConcatenation(Transform.rotate(Math.toDegrees(angle), 0, 0));
        gc.setTransform(new Affine(transform));

        gc.strokeLine(0, 0, len-10, 0);
        gc.fillPolygon(new double[]{len, len - 10, len - 10, len}, new double[]{0, -10, 10, 0},
                4);
        gc.restore();
    }

    public Viewport(String title, int width, int height) {
        super(title, width, height, true);
    }

    public static void main(String[] args) {
        new Viewport("Viewport", 500, 500).launch();
    }
}
