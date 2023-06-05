package Dao;

import classes.Users;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DaoSignUp {
    public static boolean ajouter(Connection connection, Users user) {
        String requte = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        System.out.println(requte);
        try {
            PreparedStatement pst = connection.prepareStatement(requte);
            pst.setString(2, user.getEmail());
            pst.setString(1, user.getUsername());

            // Encrypt the password before storing it
            String encryptedPassword = encryptPassword(user.getPassword());
            pst.setString(3, encryptedPassword);
 System.out.println(pst);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected >= 1) {
                System.out.println("Ajout réussi");
                showAlert("USER CREATED SUCCESSFULLY, Welcome to our application", Alert.AlertType.INFORMATION);
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Problème de requête: " + e.getMessage());
            showAlert("SORRY!, Something wrong happened", Alert.AlertType.ERROR);
        }

        return false;
    }

    private static String encryptPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = messageDigest.digest(password.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashBytes) {
                stringBuilder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Encryption algorithm not found: " + e.getMessage());
        }

        return null;
    }

    private static void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setHeaderText("Success");
        alert.setTitle("Facture");
        alert.show();
    }
}
