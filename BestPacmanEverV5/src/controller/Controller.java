package controller;


import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Controller {

    public void startClassic(ActionEvent e) throws IOException {
        Group playClassic = new Group();
        Node source = (Node) e.getSource();
        Scene theScene = source.getScene();
        theScene.setRoot(playClassic);

        GameManager gameManager = new GameManager(playClassic);
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
}

