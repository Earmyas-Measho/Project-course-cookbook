
package cookbook;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class MessagePage extends Application {

    @Override
    public void start(Stage primaryStage) {
        String path = System.getProperty("user.dir");
        primaryStage.getIcons().add(new Image(path + "/project-cookbook-perl/cookbook/resources/img/cookbook_logo2.png"));
  
  
  Parent root;
try {
    root = FXMLLoader.load(getClass().getResource("MessagePage.fxml"));


    Scene scene = new Scene(root);
  
  
    primaryStage.setTitle("Recipe");
    primaryStage.setScene(scene);
    primaryStage.show();

} catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}
    }
 
 public static void main(String[] args) {
        launch(args);
    }
}