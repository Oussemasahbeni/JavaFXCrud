package controller;

import Dao.DaoFacture;
import Dao.LaConnexion;
import classes.Facture;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLFactureController implements Initializable {

    @FXML
    TableView<Facture> tv;

    @FXML
    TextField numFacture;

    @FXML
    DatePicker dateFacture;

    @FXML
    TextField total;

    @FXML
    ComboBox<String>  refCli;

    @FXML
    TableColumn<Facture, String> colNumFacture;

    @FXML
    TableColumn<Facture, Date> colDateFacture;

    @FXML
    TableColumn<Facture, Float> colTotal;

    @FXML
    TableColumn<Facture, String> colRefCli;
    @FXML
    AnchorPane s;

    static Connection cn = LaConnexion.seConnecter();

    ObservableList<Facture> observableList;

    public void lister() {
        observableList = FXCollections.observableArrayList();
        tv.getItems().clear();
        try {
            ResultSet rs = cn.createStatement().executeQuery("select * from facture");
            while (rs.next()) {
                observableList.add(new Facture(rs.getString(1), rs.getDate(2), rs.getFloat(3), rs.getString(4)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tv.setItems(observableList);
    }
    
     ObservableList<String> observList;
public int GetCodeCli(){
        int x=1;
        observList=FXCollections.observableArrayList();

         try {
              ResultSet rs =cn.createStatement().executeQuery("select codeCli from client");
              while (rs.next()) {
                  observList.add(rs.getString(1));
                 
                  System.out.println(rs.getString(x));
            }
         } catch (SQLException ex) {
             System.out.println("error"+ex.getMessage());
         }
         this.refCli.setItems(observList);
         return x;
    }
    public void ajouter() {

        LocalDate date1 = dateFacture.getValue();
        System.out.println(date1.toString());
        if (numFacture.getText().isEmpty() || total.getText().isEmpty() ) {
            Alert dia3 = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs", ButtonType.OK);
            dia3.setHeaderText("Attention");
            dia3.setTitle("Facture");
            dia3.show();
        } else {
            String num = numFacture.getText();
            LocalDate date = dateFacture.getValue();
            System.out.println(date.toString());
            Date dateFacture = Date.valueOf(date);
            float t = Float.parseFloat(total.getText());
            String ref = refCli.getValue();
            Facture f = new Facture(num, dateFacture, t, ref);
            DaoFacture.ajouter(f);
            lister();
            clearFields();
        }
    }

    public void modifier(ActionEvent event) {
        Facture selectedFacture = tv.getSelectionModel().getSelectedItem();

        if (selectedFacture != null) {
            String newNumFacture = numFacture.getText();
            LocalDate newDateFacture = dateFacture.getValue();
            Date newDate = Date.valueOf(newDateFacture);
            float newTotal = Float.parseFloat(total.getText());
            String newRefCli = refCli.getValue();

            selectedFacture.setNumFacture(newNumFacture);
            selectedFacture.setDateFacture(newDate);
            selectedFacture.setTotal(newTotal);
            selectedFacture.setRefCli(newRefCli);

            // Call the DAO method to update the facture in the database
            if (DaoFacture.modifier(selectedFacture)) {
                // Update the table view with the modified facture
                tv.refresh();
                clearFields();
                System.out.println("Modification réussie");
            } else {
                System.out.println("La modification a échoué");
            }
        } else {
            System.out.println("Aucune facture sélectionnée");
        }
        lister();
    }

    @FXML
    void fillForm(MouseEvent event) {
        Facture selectedFacture = tv.getSelectionModel().getSelectedItem();
        if (selectedFacture != null) {
            numFacture.setText(selectedFacture.getNumFacture());
            dateFacture.setValue(selectedFacture.getDateFacture().toLocalDate());
            total.setText(String.valueOf(selectedFacture.getTotal()));
            refCli.setValue(selectedFacture.getRefCli());
        }
    }

    public void supprimer() {
        Facture selectedFacture = tv.getSelectionModel().getSelectedItem();

        if (selectedFacture != null) {
            // Show a confirmation dialog
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText("Supprimer la facture " + selectedFacture.getNumFacture());
            confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer cette facture ?");
            confirmationDialog.showAndWait();

            if (confirmationDialog.getResult() == ButtonType.OK) {
                // Call the DAO method to delete the facture from the database
                if (DaoFacture.supprimer(selectedFacture)) {
                    // Remove the facture from the table view
                    tv.getItems().remove(selectedFacture);
                    clearFields();
                    System.out.println("Suppression réussie");
                } else {
                    System.out.println("La suppression a échoué");
                }
            } else {
                System.out.println("Suppression annulée par l'utilisateur");
            }
        } else {
            System.out.println("Aucune facture sélectionnée");
        }
        lister();
    }

    public void onGestionFactureClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLClient.fxml"));
            Parent root = loader.load();

            // Access the current stage
            Stage currentStage = (Stage) tv.getScene().getWindow();

            // Replace the content of the current scene with the content of FXMLFacture.fxml
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Gestion Client");
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
        colNumFacture.setCellValueFactory(new PropertyValueFactory<>("numFacture"));
        colDateFacture.setCellValueFactory(new PropertyValueFactory<>("dateFacture"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colRefCli.setCellValueFactory(new PropertyValueFactory<>("refCli"));
        tv.setItems(observableList);
        this.GetCodeCli();
        lister();
    }

    private void clearFields() {
        numFacture.clear();
        dateFacture.setValue(null);
        total.clear();
       
    }
}
