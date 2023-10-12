package cookbook;
import java.sql.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;

import java.io.File;

public class App1 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Parent p;
        try {
            p = FXMLLoader.load(getClass().getResource("C:/Users/asus/Desktop/project group perl/project group perl java fx/cookbook/src/cookbook/RecipePage.fxml"));
            Scene s = new Scene(p);
           
            primaryStage.setTitle("Rec");
            primaryStage.setScene(s);
            primaryStage.show();

        } catch (Exception e) {
            
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
