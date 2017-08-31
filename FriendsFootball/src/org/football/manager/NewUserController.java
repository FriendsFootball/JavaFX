/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.football.manager;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.framework.mysql.MySQL;

/*
SCP or SFTP
 */
import com.jcraft.jsch.*;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.DatePicker;

public class NewUserController implements Initializable {

    /**
     * Initializes the controller class.
     */
    String username;
    String password;

    /*
	@FXML
    private ProgressBar progressBar;
    */
	
	@FXML
    private TextField firstName;

    @FXML
    private TextField surname;

    @FXML
    private DatePicker birthdate;

    @FXML
    private TextField telephone;

    @FXML
    private TextField email;

    @FXML
    private ImageView imageUser;

    @FXML
    private TextField username_TextField;

    @FXML
    private PasswordField password_PasswordField;

    @FXML
    private TextField filePath_TextField;

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleUploadButtonAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        fileChooser.setTitle("Select a Picture...");
        File file = fileChooser.showOpenDialog(stage);

        filePath_TextField.setText(file.getAbsolutePath());

        // Set Image using absolute path
        Image image = (Image) new Image(file.toURI().toString());
        imageUser.setImage(image);
        //load photo on the image only

    }

    /*
	public void updateProgressBar(long value) {
        progressBar.setProgress(value);
    }
    */
	
	String getFileExtension(String filePath) {
        //TODO no extensions files has to be rejected
        String extension = "";

        int i = filePath.lastIndexOf('.');
        if (i > 0) {
            extension = filePath.substring(i + 1);
        }
        return extension;
    }

    private boolean UploadUserImageToServer(File file, long id) {

        String filePath = file.getAbsolutePath();
        String extension = file.getName();

        // Using JSch library to send files to a server (SFTP)
        try {
            JSch ssh = new JSch();
            Session session = ssh.getSession("dev", "lrocha3.no-ip.org", 22);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword("development");

            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();

            ChannelSftp sftp = (ChannelSftp) channel;

            String fileName = Long.toString(id);

			// Coomand to give set a folder accessible to an linux user (in this case dev): sudo setfacl -m u:dev:r FriendsFootball/
            sftp.put(filePath, "/var/www/FriendsFootball/users/" + fileName + "." + getFileExtension(file.getName()), new ProgressMonitor(this));

            Boolean success = true;

            if (success) {
	            System.out.println(" The file has been uploaded succesfully");
            }
			else
			{
				System.out.println("Error: The file has not been uploaded succesfully");
			}	

            channel.disconnect();
            session.disconnect();
        } catch (JSchException | SftpException e) {
            System.out.println(e.toString());
        }

        return false;
    }

    //filters to show only images
    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {

        username = this.username_TextField.getText();
        password = this.password_PasswordField.getText();

        MySQL database = new MySQL();

        if (database.verifyUniqueUsername(username)) {

            /*
            If everything is good call the function bellow.
             */
            long id = -1;
            boolean result = database.createNewUser(username, password);

            if (result) {
                System.out.println("User Created.");
            } else {
                System.out.println("User not Created.");

            }

            database.createNewUserInformation(firstName.getText(), surname.getText(), birthdate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), email.getText(), "defense (todo)", telephone.getText(), database.GetID());
            System.out.println("Information of the user added!");

        } else {
            System.out.println("Already exists this username!");
        }

        File file = new File(filePath_TextField.getText());

        UploadUserImageToServer(file,database.GetID());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

      
    }

}
