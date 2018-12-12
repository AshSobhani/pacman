package controller;


import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Controller {

    public ColorPicker mazeColour;
    public ColorPicker backgroundColour;

    public void startClassic(ActionEvent e) throws IOException {
        Group playClassic = new Group();
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(playClassic);

        BarObstacle.setMazeColour(Color.rgb(0, 60, 183));
        GameManager gameManager = new GameManager(playClassic, "classicMap.txt");
        gameManager.drawBoard();

        theScene.addEventHandler(KeyEvent.KEY_PRESSED, event->gameManager.movePacman(event));
        theScene.addEventHandler(KeyEvent.KEY_RELEASED, event->gameManager.stopPacman(event));
        theScene.addEventHandler(KeyEvent.KEY_PRESSED, event->gameManager.restartGame(event));
    }

    public void customGame(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("customGame.fxml"));
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(root);
    }

    public void leaderboard(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("leaderboard.fxml"));
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(root);
    }

    public void backToMain(ActionEvent e) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(root);
    }

    public void mazeColour() {
        Color chosenColour = mazeColour.getValue();
        BarObstacle.setMazeColour(chosenColour);
    }

    public void funkyJunction(ActionEvent e) throws IOException {
        Group playCustom = new Group();
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(playCustom);
        theScene.setFill(backgroundColour.getValue());

        GameManager gameManager = new GameManager(playCustom, "funkyJunction.txt");
        gameManager.drawBoard();

        theScene.addEventHandler(KeyEvent.KEY_PRESSED, event->gameManager.movePacman(event));
        theScene.addEventHandler(KeyEvent.KEY_RELEASED, event->gameManager.stopPacman(event));
        theScene.addEventHandler(KeyEvent.KEY_PRESSED, event->gameManager.restartGame(event));
    }

    public void swirleyLane(ActionEvent e) throws IOException {
        Group playCustom = new Group();
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(playCustom);
        theScene.setFill(backgroundColour.getValue());

        GameManager gameManager = new GameManager(playCustom, "swirleyLane.txt");
        gameManager.drawBoard();

        theScene.addEventHandler(KeyEvent.KEY_PRESSED, event->gameManager.movePacman(event));
        theScene.addEventHandler(KeyEvent.KEY_RELEASED, event->gameManager.stopPacman(event));
        theScene.addEventHandler(KeyEvent.KEY_PRESSED, event->gameManager.restartGame(event));
    }

    public void twistedRoads(ActionEvent e) throws IOException {
        Group playCustom = new Group();
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(playCustom);
        theScene.setFill(backgroundColour.getValue());

        GameManager gameManager = new GameManager(playCustom, "twistedRoads.txt");
        gameManager.drawBoard();

        theScene.addEventHandler(KeyEvent.KEY_PRESSED, event->gameManager.movePacman(event));
        theScene.addEventHandler(KeyEvent.KEY_RELEASED, event->gameManager.stopPacman(event));
        theScene.addEventHandler(KeyEvent.KEY_PRESSED, event->gameManager.restartGame(event));
    }

    public void loopyAvenue(ActionEvent e) throws IOException {
        Group playCustom = new Group();
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(playCustom);
        theScene.setFill(backgroundColour.getValue());

        GameManager gameManager = new GameManager(playCustom, "loopyAvenue.txt");
        gameManager.drawBoard();

        theScene.addEventHandler(KeyEvent.KEY_PRESSED, event->gameManager.movePacman(event));
        theScene.addEventHandler(KeyEvent.KEY_RELEASED, event->gameManager.stopPacman(event));
        theScene.addEventHandler(KeyEvent.KEY_PRESSED, event->gameManager.restartGame(event));
    }
}

