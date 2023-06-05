package controller;

import Dao.DaoClient;
import Dao.LaConnexion;
import classes.Client;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLClientController implements Initializable {
    
    @FXML
    TableView<Client> tv;
     
    @FXML
    TextField codeCli;
    
    @FXML
    TextField nomCli;
    
    @FXML
    TextField adrCli;
    
    @FXML
    TextField emailCli;
    
    @FXML
    TableColumn<Client, String> colCode;
    
    @FXML
    TableColumn<Client, String> colNom;
    
    @FXML
    TableColumn<Client, String> colAdresse;
    
    @FXML
    TableColumn<Client, String> colEmail;
    
    @FXML
    AnchorPane s;
    
    static Connection cn = LaConnexion.seConnecter();

    ObservableList<Client> observableList;

    public void lister() {
        tv.getItems().clear();
        try {
            ResultSet rs = cn.createStatement().executeQuery("select * from client");
            while (rs.next()) {
                observableList.add(new Client(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tv.setItems(observableList);
    }

    public void ajouter() {
        if (codeCli.getText().isEmpty() || nomCli.getText().isEmpty() || adrCli.getText().isEmpty() || emailCli.getText().isEmpty()) {
            Alert dia3 = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs", ButtonType.OK);
            dia3.setHeaderText("Attention");
            dia3.setTitle("Facture");
            dia3.show();
       
        } else {
            String code = codeCli.getText();
            String nom = nomCli.getText();
            String adr = adrCli.getText();
            String email = emailCli.getText();
            Client c = new Client(code, nom, adr, email);
            DaoClient.ajouter(c);
            lister();
            clearFields();
        }
    }

    public void modifier(ActionEvent event) {
        Client selectedClient = tv.getSelectionModel().getSelectedItem();

        if (selectedClient != null) {
            String newNom = nomCli.getText();
            String newAddress = adrCli.getText();
            String newEmail = emailCli.getText();

            selectedClient.setNomCli(newNom);
            selectedClient.setAdrCli(newAddress);
            selectedClient.setEmailCli(newEmail);

            // Call the DAO method to update the client in the database
            if (DaoClient.modifier(selectedClient)) {
                // Update the table view with the modified client
                tv.refresh();
                clearFields();
                System.out.println("Modification réussie");
            } else {
                System.out.println("La modification a échoué");
            }
        } else {
            System.out.println("Aucun client sélectionné");
        }
        lister();
    }


@FXML
void FillForm(MouseEvent event) {
     System.out.println("getItems method called");
    Client selectedClient = tv.getSelectionModel().getSelectedItem();
    if (selectedClient != null) {
        codeCli.setText(selectedClient.getCodeCli());
        nomCli.setText(selectedClient.getNomCli());
        adrCli.setText(selectedClient.getAdrCli());
        emailCli.setText(selectedClient.getEmailCli());
    }
}

public void supprimer() {
    Client selectedClient = tv.getSelectionModel().getSelectedItem();

    if (selectedClient != null) {
        // Show a confirmation dialog
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Supprimer le client "+ selectedClient.getCodeCli());
        confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer ce client ?");
        confirmationDialog.showAndWait();

        if (confirmationDialog.getResult() == ButtonType.OK) {
            // Call the DAO method to delete the client from the database
            if (DaoClient.supprimer(selectedClient)) {
                // Remove the client from the table view
                tv.getItems().remove(selectedClient);
                clearFields();
                System.out.println("Suppression réussie");
            } else {
                System.out.println("La suppression a échoué");
            }
        } else {
            System.out.println("Suppression annulée par l'utilisateur");
        }
    } else {
        System.out.println("Aucun client sélectionné");
    }
    lister();
}

    private void clearFields() {
        codeCli.clear();
        nomCli.clear();
        adrCli.clear();
        emailCli.clear();
    }
    @FXML

public void onGestionFactureClicked(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLFacture.fxml"));
        Parent root = loader.load();
        
        // Access the current stage
        Stage currentStage = (Stage) tv.getScene().getWindow();

        // Replace the content of the current scene with the content of FXMLFacture.fxml
        Scene scene = new Scene(root);
        currentStage.setScene(scene);
        currentStage.setTitle("Gestion Facture");
        currentStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
@FXML
public void Logout(){
    try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLLogin.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();

            // Access the current stage
            Stage currentStage = (Stage) s.getScene().getWindow();

            // Replace the content of the current scene with the content of FXMLLogin.fxml
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Login");
            currentStage.show();
        } catch (IOException e) {
            showAlert("Failed to load FXMLLogin.fxml", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

}
  private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setHeaderText("Attention");
        alert.setTitle("Facture");
        alert.show();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        observableList = FXCollections.observableArrayList();
        colCode.setCellValueFactory(new PropertyValueFactory<>("codeCli"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nomCli"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<>("adrCli"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("emailCli"));
         tv.setItems(observableList); 
        lister();
    }  
}
