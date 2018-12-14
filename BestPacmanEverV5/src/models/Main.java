package models;



import controllers.SoundController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 * The main class is simply used to initialise the games Stage and Scene and trigger
 * I also use the main to play the initial Pacman intro music
 */
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