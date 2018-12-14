package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import models.sortLeaderboard;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class UserScoreController {
    @FXML TextField uName;
    public LeaderboardController PL = new LeaderboardController();

    public ArrayList<String> scoreArray = new ArrayList<>();
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

    public void writeNameScore() throws Exception {
        BufferedWriter writer;
        nameScore = username + ":" + Integer.toString(score);

        writer = new BufferedWriter(new FileWriter("BestPacmanEverV5/src/resources/scores/cScores", true));
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
        reader.close();
        return fileData;
    }

    public ArrayList<String> sortLeaderboard() throws Exception {
        ArrayList<String> sortedFile = readNameScore();
        Collections.sort(sortedFile, new sortLeaderboard().reversed());

        return sortedFile;
    }

    public void makeLeaderboard() throws Exception {
        writeNameScore();

        ArrayList<String> sorting = sortLeaderboard();

        BufferedWriter writer;
        writer = new BufferedWriter(new FileWriter("BestPacmanEverV5/src/resources/scores/cScores", false));

        int i = 0;
        for (String s: sorting) {
            if(i < 10) {
                writer.write(s);
                writer.newLine();
                writer.flush();
            }
            i++;
        }
        writer.close();
    }

    public void leaderboard(ActionEvent e) throws Exception {
        scoreArray = readNameScore();

        Parent root = FXMLLoader.load(getClass().getResource("../views/leaderboard.fxml"));
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(root);

        PL.printToBoard(scoreArray, root);
    }

    public void backToMain(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/menu.fxml"));
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(root);
    }
}

