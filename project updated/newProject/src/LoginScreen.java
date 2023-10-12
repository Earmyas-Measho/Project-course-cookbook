import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LoginScreen {
    private static Scene scene;

    public static Scene getScene() {
        if (scene == null) {
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(50));

            Text scenetitle = new Text("Welcome to Cookbook");
            scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 36));
            scenetitle.setFill(Color.WHITE);
            scenetitle.setStroke(Color.BLACK);
            scenetitle.setStrokeWidth(2);
            grid.add(scenetitle, 0, 0, 2, 1);

            Label userName = new Label("User Name:");
            userName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            userName.setStyle("-fx-text-fill: white;");
            grid.add(userName, 0, 1);

            TextField userTextField = new TextField();
            userTextField.setPrefWidth(200);
            grid.add(userTextField, 1, 1);

            Label pw = new Label("Password:");
            pw.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            pw.setStyle("-fx-text-fill: white;");
            grid.add(pw, 0, 2);

            PasswordField pwBox = new PasswordField();
            pwBox.setPrefWidth(200);
            grid.add(pwBox, 1, 2);

            Label displayNameLabel = new Label("Display Name:");
            displayNameLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            displayNameLabel.setStyle("-fx-text-fill: white;");
            grid.add(displayNameLabel, 0, 3);

            TextField displayNameField = new TextField();
            displayNameField.setPrefWidth(200);
            grid.add(displayNameField, 1, 3);

            Button btn = new Button("Sign in");
            btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            grid.add(btn, 1, 4);

            final Text actiontarget = new Text();
            actiontarget.setFill(Color.RED);
            grid.add(actiontarget, 1, 6);

            btn.setOnAction(e -> {
                String username = userTextField.getText();
                String password = pwBox.getText();
                User user = User.login(username, password);
                if (user != null) {
                    App.userLoggedIn(user); // inform the App about the logged in user
                    App.goToRecipeManagement(user); // go to the recipe management view
                } else {
                    actiontarget.setText("Invalid login credentials.");
                }
            });

            // Added register button
            Button registerButton = new Button("Register");
            registerButton.setStyle("-fx-background-color: #3F51B5; -fx-text-fill: white;");
            grid.add(registerButton, 1, 5);
            registerButton.setOnAction(e -> {
                App.goToRegistrationScreen();
            });

            // Set image as background
            String imagePath = "image.jpeg";
            Image image = new Image(imagePath, true);
            image.errorProperty().addListener((observable, oldValue, newValue) -> {
            });

            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
            Background backgroundWithImage = new Background(backgroundImage);
            grid.setBackground(backgroundWithImage);

            scene = new Scene(grid, 800, 600);
        }

        return scene;
    }
}
