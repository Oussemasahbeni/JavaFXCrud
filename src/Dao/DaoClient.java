/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import classes.Client;
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
public class DaoClient {
      static Connection cn=LaConnexion.seConnecter();
    public static boolean ajouter(Client c) { 
    String requte = "INSERT INTO client VALUES (?, ?, ?, ?)";

    try {
        PreparedStatement pst = cn.prepareStatement(requte);
        pst.setString(1, c.getCodeCli());
        pst.setString(2, c.getNomCli());
        pst.setString(3, c.getAdrCli());
        pst.setString(4, c.getEmailCli());
        
        int n = pst.executeUpdate();
        if (n >= 1) {
            System.out.println("Ajout réussi");
             Alert dia3 = new Alert(Alert.AlertType.INFORMATION, "ADDED SUCCESSFULY  ", ButtonType.OK);
        dia3.setHeaderText("Success");
        dia3.setTitle("Facture");
        dia3.show();
            return true;
        }
    } catch (SQLException e) {
        System.out.println("Problème de requête: " + e.getMessage());

        Alert dia3 = new Alert(Alert.AlertType.ERROR, "ID EXISTS ALREADY", ButtonType.OK);
        dia3.setHeaderText("Erreur");
        dia3.setTitle("Facture");
        dia3.show();
    }
    return false;
}
    
public static boolean modifier(Client c) {
    String requete = "UPDATE client SET nomCli = ?, adrCli = ?, emailCli = ? WHERE codeCli = ?";
    try {
        PreparedStatement ps = cn.prepareStatement(requete);
        ps.setString(1, c.getNomCli());
        ps.setString(2, c.getAdrCli());
        ps.setString(3, c.getEmailCli());
        ps.setString(4, c.getCodeCli());

        int n = ps.executeUpdate();
        if (n >= 1) {
            System.out.println("Modification réussie");
             Alert dia3 = new Alert(Alert.AlertType.INFORMATION, "MODIFIED SUCCESSFULY  ", ButtonType.OK);
        dia3.setHeaderText("Success");
        dia3.setTitle("Facture");
        dia3.show();
            return true;
        }
    } catch (SQLException e) {
        System.out.println("Problème de requête: " + e.getMessage());
    }
    return false;
}


 public static boolean supprimer(Client c) {

    String requete = "DELETE FROM client WHERE CodeCli=?";
    try {
        PreparedStatement ps = cn.prepareStatement(requete);
        ps.setString(1, c.getCodeCli());
        int n = ps.executeUpdate();
        if (n >= 1) {
            System.out.println("Suppression réussie");
             Alert dia3 = new Alert(Alert.AlertType.INFORMATION, "DELETED SUCCESSFULY  ", ButtonType.OK);
        dia3.setHeaderText("Success");
        dia3.setTitle("Facture");
        dia3.show();
            return true;
        }
    } catch (SQLException e) {
        System.out.println("Problème de requête: " + e.getMessage());
    }
    return false;
}
 
   public static Client chercher(String val){
         Client res=null;
       
        String requete ="select * from client where codeCli  like?";
    try{
    PreparedStatement pst=cn.prepareStatement(requete);
    pst.setString(1,"%"+val+"%");

    ResultSet rs=pst.executeQuery();
    String code;
    String nom,adresse,email;
    Client c;
    if(rs!=null){
        while(rs.next()){
            code=rs.getString("codeCli");
            nom=rs.getString("nomCli");
            
            adresse=rs.getString("adresseCli");
            email=rs.getString("emailCli");
            c=new Client(code,nom,adresse,email);
            return c;
        }
    }
}catch(SQLException ex){
            System.out.println("probleme de req select"+ex.getMessage());
}
return res;
}
    
}
