package controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ColorPicker;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import models.BarObstacle;
import models.GameManager;
import sprites.Cookie;

public class GameController {
    public ColorPicker mazeColour;
    public ColorPicker backgroundColour;
    public ColorPicker cookieColour;
    public LeaderboardController PL = new LeaderboardController();
    public UserScoreController SC = new UserScoreController();

    /**
     *
     * @param e This is an action event waiting for input by the user to run this method
     * @throws IOException Incase of an error it handles it
     * This creates a new scene in which the classic game of pacman begins
     */
    public void startClassic(ActionEvent e) throws IOException {
        Group playClassic = new Group();
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(playClassic);

        BarObstacle.setMazeColour(Color.rgb(0, 60, 183));
        GameManager gameManager = new GameManager(playClassic, "classicMap.txt");
        gameManager.drawBoard();

        theScene.addEventHandler(KeyEvent.KEY_PRESSED, gameManager::movePacman);
        theScene.addEventHandler(KeyEvent.KEY_PRESSED, gameManager::restartGameOrMenu);
    }

    /**
     * This takes the user to a custom game scene where they can chose colours to play with and can choose one of 4 maps
     * @param e This is an action event waiting for input by the user to run this method
     * @throws IOException Incase of an error it handles it
     */
    public void customGame(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/customGame.fxml"));
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(root);
    }

    /**
     * This method takes the Names and Scores from UserScoreController and prints them to a board which is positioned on a leaderboard scene
     * @param e This is an action event waiting for input by the user to run this method
     * @throws Exception Incase of an error it handles it
     */
    public void leaderboard(ActionEvent e) throws Exception {
        SC.scoreArray = SC.readNameScore();
        Parent root = FXMLLoader.load(getClass().getResource("../views/leaderboard.fxml"));
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(root);

        PL.printToBoard(SC.scoreArray, root);
    }

    /**
     * This method take the user to a scene where they can input there name to be placed on the leaderboard given that they had enough points to be on the top ten
     * @param e This is an action event waiting for input by the user to run this method
     * @throws IOException Incase of an error it handles it
     */
    public static void newUserScore(KeyEvent e) throws IOException {
        Parent root = FXMLLoader.load(GameController.class.getResource("../views/newUserScore.fxml"));
        Scene theScene = (Scene) e.getSource();
        theScene.setRoot(root);
    }

    /**
     * This method allows the user to quit the game from within the UI (By pressing a button)
     */
    public void exitGame() {
        Platform.exit();
        System.exit(0);

    }

    /**
     * This method is a variation of backToMain as the original one took a mouse input whereas this one takes a keyboard input
     * @param e This is an action event waiting for input by the user to run this method
     * @throws IOException Incase of an error it handles it
     */
    public void backToMain(KeyEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/menu.fxml"));
        Scene theScene = (Scene) e.getSource();
        theScene.setRoot(root);
    }

    /**
     * This method is the original backToMain method that is triggered by the mouse in the menu screens
     * @param e This is an action event waiting for input by the user to run this method
     * @throws IOException Incase of an error it handles it
     */
    public void backToMainB(ActionEvent e) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../views/menu.fxml"));
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(root);
    }

    /**
     * All this method does is take in a map and run the loadMap method given a map input accordingly
     * @param e This is an action event waiting for input by the user to run this method
     */
    public void funkyJunction(ActionEvent e) {
        loadMap(e, "funkyJunction.txt");
    }

    /**
     * All this method does is take in a map and run the loadMap method given a map input accordingly
     * @param e This is an action event waiting for input by the user to run this method
     */
    public void swirleyLane(ActionEvent e) {
        loadMap(e, "swirleyLane.txt");
    }

    /**
     * All this method does is take in a map and run the loadMap method given a map input accordingly
     * @param e This is an action event waiting for input by the user to run this method
     */
    public void twistedRoads(ActionEvent e) {
        loadMap(e, "twistedRoads.txt");
    }

    /**
     * All this method does is take in a map and run the loadMap method given a map input accordingly
     * @param e This is an action event waiting for input by the user to run this method
     */
    public void loopyAvenue(ActionEvent e) {
        loadMap(e, "loopyAvenue.txt");
    }

    /**
     * This method creates a new scene for a custom map, it then sets the custom colours, finally it draws the map and adds the event handlers
     * @param e This is an action event waiting for input by the user to run this method
     * @param fileName The file name is important as it is the map structure we will build on
     */
    private void loadMap(ActionEvent e, String fileName) {
        Group playCustom = new Group();
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(playCustom);

        theScene.setFill(backgroundColour.getValue());
        Cookie.cookieColour = cookieColour.getValue();
        BarObstacle.mazeColour = mazeColour.getValue();

        GameManager gameManager = new GameManager(playCustom, fileName);
        gameManager.drawBoard();

        theScene.addEventHandler(KeyEvent.KEY_PRESSED, gameManager::movePacman);
        theScene.addEventHandler(KeyEvent.KEY_PRESSED, gameManager::restartGameOrMenu);
    }
}

