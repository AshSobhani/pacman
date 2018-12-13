package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class ScoreController {
    @FXML TextField uName;
    private BufferedWriter bw;
    private int score;
    private String username;

//    public void ScoreController(int Uscore) {
//        score = Uscore;
//        username = uName.getText();
//    }


    public void setScore(int score) {
        this.score = score;
        username = uName.getText();

        System.out.println("" + score + " - " + username);
    }

//    public void addUserScore() throws Exception {
//        bw = new BufferedWriter(new FileWriter("file: BestPacmanEverV5/src/resources/scores/cScores"));
//        bw.write(Integer.toString(score) + "-" + username);
//        bw.newLine();
//        bw.flush();
//    }
}

