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


public class UserScoreController implements ControllerInterface {
    @FXML TextField uName;
    public LeaderboardController PL = new LeaderboardController();

    public ArrayList<String> scoreArray = new ArrayList<>();
    private String username, nameScore;
    private static int score;


    /**
     * This method gets the username from the input by actively listening and by putting it into a local variable
     */
    @FXML public void getUsername() {
        uName.textProperty().addListener(observable -> {
            username = uName.getCharacters().toString();
        });
    }

    /**
     * This method gets the score from the users most recent game and stores it into a variable
     * @param gameScore Its a getter for gameScore
     */
    public void getScore(int gameScore) {
        score = gameScore;
    }

    /**
     * This is a file writer which adds users to the score file so it can be read by the board
     * @throws Exception Incase of an error it handles it
     */
    public void writeNameScore() throws Exception {
        BufferedWriter writer;
        nameScore = username + ":" + Integer.toString(score);

        writer = new BufferedWriter(new FileWriter("BestPacmanEverV5/src/resources/scores/cScores", true));
        writer.write(nameScore);
        writer.newLine();
        writer.flush();
        writer.close();
    }

    /**
     *
     * @return This method is a readfile and is used to create an array of all the users and their scores ready to be sorted
     * @throws Exception Incase of an error it handles it
     */
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

    /**
     * This method adds the most recent users score and name to the list and then goes through it and sorts them in terms of points.
     * @throws Exception Incase of an error it handles it
     */
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

    /**
     * This method takes the Names and Scores from UserScoreController and prints them to a board which is positioned on a leaderboard scene
     * @param e This is an action event waiting for input by the user to run this method
     * @throws Exception Incase of an error it handles it
     */
    public void leaderboard(ActionEvent e) throws Exception {
        scoreArray = readNameScore();

        Parent root = FXMLLoader.load(getClass().getResource("../views/leaderboard.fxml"));
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(root);

        PL.printToBoard(scoreArray, root);
    }

    /**
     * This method is the original backToMainK method that is triggared by the mouse in the menu screens
     * @param e This is an action event waiting for input by the user to run this method
     * @throws IOException Incase of an error it handles it
     */
    public void backToMain(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/menu.fxml"));
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(root);
    }
}

