package org.football.manager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import org.framework.mysql.MySQL;

/**
 *
 * @author Luis Rocha
 */
public class LoginController implements Initializable {

    @FXML
    private TextField email_TextField;
    @FXML
    private PasswordField password_PasswordField;

    @FXML
    private Button login_button;

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String query;
        MySQL database = new MySQL();

        query = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";

        try {
            database.new_PrepareStatement(query);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            database.SetPreparedStatementArg(1, email_TextField.getText());
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            database.SetPreparedStatementArg(2, password_PasswordField.getText());
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ResultSet rs = database.new_ExecutePreparedQuery();

        long result = database.new_getCount(rs);

        if (result == 1) {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserInformation.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();

                
                UserInformationController controller = (UserInformationController) fxmlLoader.getController();
                controller.initData(email_TextField.getText());
                
                Stage stage = (Stage) login_button.getScene().getWindow();

                stage.setTitle("User Information");
                stage.getIcons().add((Image) new Image(getClass().getResource("user_information.png").toExternalForm()));

                //Set minimum size
                stage.setMinHeight(400);
                stage.setMinWidth(650);

                stage.setScene(new Scene(root1));

                

                stage.show();

                // Center Stage in the middle of the screen
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

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
            stage.getIcons().add((Image) new Image(getClass().getResource("new.png").toExternalForm()));

            //Set minimum size
            stage.setMinHeight(400);
            stage.setMinWidth(650);

            stage.setScene(new Scene(root1));
            stage.show();

            // Center Stage in the middle of the screen
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
