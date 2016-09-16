package org.football.manager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

/**
 *
 * @author Luis Rocha
 */
public class FootballManager extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Friends Footbal");

        stage.getIcons().add((Image) new Image(getClass().getResource("icon.png").toExternalForm()));

        //Set minimum size
        stage.setMinHeight(523);
        stage.setMinWidth(774);
        
       
        stage.setScene(scene);

        stage.show();
    }

  
    public static void main(String[] args) {
        launch(args);
    }

}
