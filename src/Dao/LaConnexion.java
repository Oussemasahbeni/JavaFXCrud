/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author sahba
 */
public class LaConnexion {
        private  static Connection con;
  
    public  static Connection seConnecter(){
        if (con==null)// pas de connexion
        { 
            try{
             /*   //Charger un driver avec la méthode statique forName de la classe Class.
                Class.forName("com.mysql.jdbc.Driver");*/
                //D’établir une connexion vers la base de données bd1_java, avec la méthode statique getConnection de la classe DriverManager
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bdgestion","root","");
                System.out.println("connexion établie");
           
      /* }/*catch(ClassNotFoundException e)
        {System.out.println("driver non trouvé"+e.getMessage());*/
        }catch(SQLException ex){
                System.out.println("bd non trouvée ou problème d'identification "+ex.getMessage());
        }
        }
        return con;
    }
    
}
