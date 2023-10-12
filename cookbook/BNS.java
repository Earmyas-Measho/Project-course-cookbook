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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Screen;

import java.io.File;

public class BNS extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Login.firstLaunch = false;
        Parent p;
        try {
            p = FXMLLoader.load(getClass().getResource("BrowseNSearch.fxml"));
            Scene s = new Scene(p, 800, 630);

            String path = System.getProperty("user.dir");
            primaryStage.getIcons().add(new Image(path + "/project-cookbook-perl/cookbook/resources/img/cookbook_logo2.png"));
            
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
