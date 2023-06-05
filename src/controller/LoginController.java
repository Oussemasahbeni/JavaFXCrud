package controller;

import Dao.DaoLogin;
import classes.Users;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private StackPane s;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    @FXML
    private void initialize() {
        // Initialize the controller
    }

    @FXML
    private void login() {
        String userEmail = email.getText();
        String userPassword = password.getText();

        // Create a new Users object with the entered email and password
        Users user = new Users(userEmail, userPassword);

        // Call the login method from DaoLogin class to authenticate the user
        boolean isAuthenticated = DaoLogin.login(user);

        if (isAuthenticated) {
            try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLClient.fxml"));
                    Parent root = loader.load();
                    FXMLClientController clientController = loader.getController();

                    // Access the current stage
                    Stage currentStage = (Stage) s.getScene().getWindow();

                    // Replace the content of the current scene with the content of FXMLClient.fxml
                    Scene scene = new Scene(root);
                    currentStage.setScene(scene);
                    currentStage.setTitle("Gestion Client");
                    currentStage.show();
                } catch (IOException e) {
                    showAlert("Failed to load FXMLClient.fxml", Alert.AlertType.ERROR);
                    e.printStackTrace();
                }
            } else {
                showAlert("Email or password is incorrect.", Alert.AlertType.ERROR);
            }
        }
        
    

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public void goToSignUp(){
         try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLSignUp.fxml"));
                    Parent root = loader.load();
                    SignUpController signupController = loader.getController();

                    // Access the current stage
                    Stage currentStage = (Stage) s.getScene().getWindow();

                    // Replace the content of the current scene with the content of FXMLClient.fxml
                    Scene scene = new Scene(root);
                    currentStage.setScene(scene);
                    currentStage.setTitle("Gestion Client");
                    currentStage.show();
                } catch (IOException e) {
                    showAlert("Failed to load FXMLClient.fxml", Alert.AlertType.ERROR);
                    e.printStackTrace();
                }
        
    }
}
