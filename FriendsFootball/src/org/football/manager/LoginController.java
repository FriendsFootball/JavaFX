package org.football.manager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;

/**
 *
 * @author Luis Rocha
 */
public class LoginController implements Initializable {

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {

    }

    @FXML
    private void handleCreateAccountAction() {
        openNewUserWindow();
    }

    public void openNewUserWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewUser.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            // METHOD 1
            // # This methos allows to use the same stage and only change the scene.
            // Way better in terms of transitions of environments.
            // Declarar acima:     private Button login_button; login_button é o id no fxml
            // Stage stage = (Stage) login_button.getScene().getWindow();
            // METHOD 2 
            // Stage stage = (Stage)((Node)(event.getSource())).getScene().getWindow();
            // Tem que se ter ActionEvent event como argumento.
            // METHOD 3
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // This new windows won't allow editing the window behind it (the one that created the new one).
            stage.setTitle("New User");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
