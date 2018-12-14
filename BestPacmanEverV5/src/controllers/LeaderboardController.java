package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;

public class LeaderboardController {
    @FXML TextField uName;

    private String username, nameScore;
    private static int score;


    @FXML public void getUsername() {
        uName.textProperty().addListener(observable -> {
            username = uName.getCharacters().toString();
        });
    }

    public void getScore(int gameScore) {
        score = gameScore;
    }

//    public void mergeNameScore() {
//        nameScore = username + "|" + Integer.toString(score);
//        System.out.println(nameScore);
//    }

    public void writeNameScore() throws Exception {
        BufferedWriter writer;
        nameScore = username + "|" + Integer.toString(score);

        writer = new BufferedWriter(new FileWriter("BestPacmanEverV5/src/resources/scores/cScores"));
        writer.write(nameScore);
        writer.newLine();
        writer.flush();
        writer.close();
    }

    public ArrayList<String> readNameScore() throws Exception {
        BufferedReader reader;
        String tempLine;

        ArrayList<String> fileData = new ArrayList<>();
        reader = new BufferedReader(new FileReader("BestPacmanEverV5/src/resources/scores/cScores"));

        while ((tempLine = reader.readLine()) != null) {
            fileData.add(tempLine);
        }

        return fileData;
    }

    public void backToMain(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/menu.fxml"));
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(root);
    }

    public void exitGame() {
        Platform.exit();
        System.exit(0);

    }
}

