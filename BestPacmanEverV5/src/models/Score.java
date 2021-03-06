package models;



import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.BarObstacle;

public class Score {

    public Text score;
    public Text lifes;

    /**
     * This is what shows the scores and lives left, I have changed the font proporties to fit my game Style
     * @param root As mentioned before this is like an anchor
     */
    public Score(Group root) {
        this.score = new Text(BarObstacle.THICKNESS * 4, BarObstacle.THICKNESS * 28, "Score: 0");
        this.lifes = new Text(BarObstacle.THICKNESS * 20, BarObstacle.THICKNESS * 28,"Lifes: 3");
        score.setFill(Color.MAGENTA);
        this.score.setFont(Font.font("Avenir Next Heavy", 30));

        lifes.setFill(Color.MAROON);
        this.lifes.setFont(Font.font("Avenir Next Heavy", 30));

        root.getChildren().add(score);
        root.getChildren().add(lifes);
    }
}
