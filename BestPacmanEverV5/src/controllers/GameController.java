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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import sprites.Cookie;

public class GameController {
    public ColorPicker mazeColour;
    public ColorPicker backgroundColour;
    public ColorPicker cookieColour;

    public void startClassic(ActionEvent e) throws IOException {
        Group playClassic = new Group();
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(playClassic);

        BarObstacle.setMazeColour(Color.rgb(0, 60, 183));
        GameManager gameManager = new GameManager(playClassic, "classicMap.txt");
        gameManager.drawBoard();

        theScene.addEventHandler(KeyEvent.KEY_PRESSED, gameManager::movePacman);
        theScene.addEventHandler(KeyEvent.KEY_PRESSED, gameManager::restartGame);
    }

    public void customGame(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/customGame.fxml"));
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(root);
    }

    public void leaderboard(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/leaderboard.fxml"));
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(root);
    }

    public void exitGame() {
        Platform.exit();
        System.exit(0);

    }

    public void backToMain(KeyEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/menu.fxml"));
        Scene theScene = (Scene) e.getSource();
        theScene.setRoot(root);
    }

    public void backToMain(ActionEvent e) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../views/menu.fxml"));
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(root);
    }

    public void funkyJunction(ActionEvent e) {
        loadMap(e, "funkyJunction.txt");
    }

    public void swirleyLane(ActionEvent e) {
        loadMap(e, "swirleyLane.txt");
    }

    public void twistedRoads(ActionEvent e) {
        loadMap(e, "twistedRoads.txt");
    }

    public void loopyAvenue(ActionEvent e) {
        loadMap(e, "loopyAvenue.txt");
    }

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
        theScene.addEventHandler(KeyEvent.KEY_PRESSED, gameManager::restartGame);
    }
}

