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

/**
 *
 * @author Luis Rocha
 */
public class LoginController implements Initializable {

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        //openNextNewWindow();
    }
    
    @FXML
    private void handleCreateAccountAction() {
        openNewUserWindow();
    }
    
    
    public void openNewUserWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewUser.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
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
