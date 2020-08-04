package models;



import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BarObstacle extends Rectangle {

    public static Color mazeColour = Color.rgb(153, 179, 255);
    public static double THICKNESS = 25;
    /**
     *
     * @param x Sets the X Coordinate of the Bar Obstacle
     * @param y Sets the y Coordinate of the Bar Obstacle
     * @param orientation - horizontal or vertical
     * @param length - the length of the bar (1 == 100px)
     */
    public BarObstacle(double x, double y, String orientation, double length) {
        this.setX(x);
        this.setY(y);
        if (orientation.equals("horizontal")) {
            this.setHeight(BarObstacle.THICKNESS);
            this.setWidth(length * BarObstacle.THICKNESS);
        } else {
            this.setHeight(length * BarObstacle.THICKNESS);
            this.setWidth(BarObstacle.THICKNESS);
        }
        this.setFill(mazeColour);
        this.setStrokeWidth(3);
    }

    /**
     *
     * @param chosenColour setter to set Maze colour
     */
    public static void setMazeColour(Color chosenColour) {
        mazeColour = chosenColour;
    }
}
