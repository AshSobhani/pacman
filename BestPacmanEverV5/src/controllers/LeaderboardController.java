package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.sortLeaderboard;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class LeaderboardController {
    @FXML GridPane theBoard;

    public void printToBoard(ArrayList<String> scoreArray, Parent root) {
        System.out.println("Hello");
        GridPane scoreBoard = (GridPane) root.lookup("#theBoard");

        for (int i = 0; i < scoreBoard.getRowCount() - 1 && i < scoreArray.size(); i++) {
            Label userName = new Label();

            userName.setText(scoreArray.get(i).split(":")[1]);
            userName.setFont(Font.font("Avenir Next Heavy", 30));
            userName.setTextFill(Color.CYAN);

            GridPane.setHalignment(userName, HPos.LEFT);

            scoreBoard.add(userName, 1, i+1 , 1, 1);
        }

        for (int i = 0; i < scoreBoard.getRowCount() - 1 && i < scoreArray.size(); i++) {
            Label topScore = new Label();

            topScore.setText(scoreArray.get(i).split(":")[0]);
            topScore.setFont(Font.font("Avenir Next Heavy", 30));
            topScore.setTextFill(Color.CYAN);

            GridPane.setHalignment(topScore, HPos.LEFT);

            scoreBoard.add(topScore, 0, i+1, 1, 1);
        }
    }



    public void backToMain(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/menu.fxml"));
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(root);
    }
}

