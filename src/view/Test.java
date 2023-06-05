package view;

import javafx.scene.image.Image;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Test extends Application {
  
  @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
        Scene scene = new Scene(root);
        Image image=new Image (getClass().getResourceAsStream("/images/1595539876239.jpg"));
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.setTitle("Gestion des clients");
        stage.show();
       // scene.getStylesheets().add(getClass().getResource("/view/Style.css").toExternalForm());

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       launch(args);
        
    }
    
}