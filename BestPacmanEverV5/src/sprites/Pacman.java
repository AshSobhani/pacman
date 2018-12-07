package sprites;

import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;

import java.awt.*;

public class Pacman extends Circle {

    public Pacman(double x, double y) {
        Image pacPic = new Image("file:BestPacmanEverV5/resources/pacman.png");

        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(25);
        this.setFill(new ImagePattern(pacPic, 0, 0, 1, 1, true));
    }
}
