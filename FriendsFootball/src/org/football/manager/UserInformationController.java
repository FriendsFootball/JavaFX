/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.football.manager;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import org.framework.mysql.MySQL;

public class UserInformationController implements Initializable {

    void initData(String username) {
        this.username = username;
    }
    /**
     * Initializes the controller class.
     */
    String username;
    String password;

    // @FXML
    //private ProgressBar progressBar;
    @FXML
    private TextField firstName;

    @FXML
    private ImageView imageUser;

    @FXML
    private TextField surname;

    @FXML
    private DatePicker birthdate;

    @FXML
    private TextField telephone;

    @FXML
    private TextField email;

    @FXML
    private TextField username_TextField;

    @FXML
    private TextField filePath_TextField;

    @FXML
    private void Refresh() {
        try {
            MySQL database = new MySQL();

            ResultSet rs = database.userInformationQuery(this.username);

            if (rs.next()) {

                firstName.setText(rs.getString("first_name"));
                username_TextField.setText(rs.getString("username"));
                surname.setText(rs.getString("surname"));
                Date date = rs.getDate("birthdate");
                LocalDate ld = new java.sql.Date(date.getTime()).toLocalDate();

                birthdate.setValue(ld);

                email.setText(rs.getString("email"));
                telephone.setText(rs.getString("telephone"));

                Image image = (Image) new Image("http://lrocha3.no-ip.org/FriendsFootball/users/"+ rs.getString("id_player")+ ".jpg", true);
                imageUser.setImage(image);

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserInformationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
