/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.football.manager;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luis Rocha
 */
public class NewUserController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private void handleCancelButtonAction(ActionEvent event){
        Stage stage = (Stage)((Node)(event.getSource())).getScene().getWindow();
        stage.close();

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
