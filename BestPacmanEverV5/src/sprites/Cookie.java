package sprites;



import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Cookie extends Circle {

    private int value;

    public Cookie(double x, double y) {
        this.value = 5;
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(9);
        this.setFill(Color.rgb(214, 190, 36));
    }

    public int getValue() {
        return value;
    }

    public void hide() {
        this.setVisible(false);
    }

    public void show() {
        this.setVisible(true);
    }

}