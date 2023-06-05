/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author sahba
 */
public class Users {
    private int id;
    private String username;
    private String email;
    private String password;

    public int getId() {
        return id;
    }

    // Constructor for sign up
    public Users(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    // Constructor for login
    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

 
    
    
    
    
    
    
}
