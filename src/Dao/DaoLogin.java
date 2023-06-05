/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import classes.Users;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author sahba
 */
public class DaoLogin {
    static Connection cn = LaConnexion.seConnecter();

    public static boolean login(Users c) {
        String requte = "SELECT * FROM users WHERE email=? AND password=?";
        try {
            PreparedStatement pst = cn.prepareStatement(requte);
            pst.setString(1, c.getEmail());

            // Encrypt the entered password
            String encryptedPassword = encryptPassword(c.getPassword());
            pst.setString(2, encryptedPassword);

            ResultSet r = pst.executeQuery();

            if (r.next()) {
                // Successful login
                Alert dia3 = new Alert(Alert.AlertType.INFORMATION, "SUCCESS", ButtonType.OK);
                dia3.setHeaderText("Success");
                dia3.setTitle("Facture");
                dia3.show();
                return true;
            }
        } catch (SQLException e) {
            // Error occurred during login
            Alert dia3 = new Alert(Alert.AlertType.INFORMATION, "ERROR LOGIN", ButtonType.OK);
            dia3.setHeaderText("Error");
            dia3.setTitle("Facture");
            dia3.show();
        }
        return false;
    }

    private static String encryptPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception
            e.printStackTrace();
        }
        return null;
    }
}
