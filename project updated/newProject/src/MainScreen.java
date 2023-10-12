import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainScreen {
  private static Scene scene;

  public static Scene getScene() {
    if (scene == null) {
      VBox vbox = new VBox(20); // Increase spacing
      vbox.setAlignment(Pos.CENTER);
      vbox.setPadding(new Insets(20)); // Add padding
      Label welcomeLabel = new Label("Welcome to the main screen!");
      welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24)); // Make text larger and bold
      welcomeLabel.setTextFill(Color.web("#ffffff")); // Change text color to white

      DropShadow shadow = new DropShadow();
      shadow.setColor(Color.web("#ffffff"));

      Button goToUserManagementButton = new Button("Admin");
      goToUserManagementButton.setOnAction(e -> App.goToUserManagement());
      goToUserManagementButton.setStyle("-fx-background-color: #feb2b2; -fx-text-fill: #ffffff;");
      goToUserManagementButton.setEffect(shadow); // Add shadow to button
      Button goToRecipeManagementButton = new Button("Recipes");
      goToRecipeManagementButton.setOnAction(e -> App.goToRecipeManagement(App.getLoggedInUser()));
      goToRecipeManagementButton.setStyle("-fx-background-color: #feb2b2; -fx-text-fill: #ffffff;"); // Change
      goToRecipeManagementButton.setEffect(shadow); // Add shadow to button
      vbox.getChildren().addAll(welcomeLabel, goToUserManagementButton, goToRecipeManagementButton);
      scene = new Scene(vbox, 500, 400); // Make scene larger
    }

    return scene;
  }
}
