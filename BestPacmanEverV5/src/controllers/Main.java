package controllers;



import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage theStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../views/menu.fxml"));
        theStage.setTitle( "Pacman" );
        theStage.setScene(new Scene (root,1225, 720));

        SoundController pacSound = new SoundController();
        pacSound.playPacIntro();

        theStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}