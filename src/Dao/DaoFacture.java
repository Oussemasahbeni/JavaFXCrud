/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import static Dao.DaoClient.cn;
import classes.Facture;
import java.sql.Connection;
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
public class DaoFacture {
    static Connection cn=LaConnexion.seConnecter();
    
     public static boolean ajouter(Facture c) { 
    String requte = "INSERT INTO facture VALUES (?, ?, ?, ?)";

    try {
        PreparedStatement pst = cn.prepareStatement(requte);
     pst.setString(1, c.getNumFacture());      
     pst.setDate(2, c.getDateFacture());       
     pst.setFloat(3, c.getTotal());           
     pst.setString(4, c.getRefCli());          
        
        System.out.println(pst);
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

        Alert dia3 = new Alert(Alert.AlertType.ERROR, "NUM EXISTS ALREADY" + e.getMessage(), ButtonType.OK);
        dia3.setHeaderText("Erreur");
        dia3.setTitle("Facture");
        dia3.show();
    }
    return false;
}
    
public static boolean modifier(Facture c) {
    String requete = "UPDATE facture SET dateFacture = ?, refCli = ?, total = ? WHERE numFacture = ?";
    try {
        PreparedStatement ps = cn.prepareStatement(requete);
        ps.setString(4, c.getNumFacture());
        ps.setString(2, c.getRefCli());
        ps.setDate(1, c.getDateFacture());
        ps.setFloat(3, c.getTotal());
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


 public static boolean supprimer(Facture c) {

    String requete = "DELETE FROM facture WHERE numFacture=?";
    try {
        PreparedStatement ps = cn.prepareStatement(requete);
        ps.setString(1, c.getNumFacture());
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
 
 /* public static Client chercher(String val){
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
} */
    
    
}
