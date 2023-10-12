import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class UserRegistration {

    private static Scene scene;

    public static Scene getScene() {
        if (scene == null) {
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(50));

            Text scenetitle = new Text("Register New User");
            grid.add(scenetitle, 0, 0, 2, 1);

            Label userName = new Label("User Name:");
            grid.add(userName, 0, 1);

            TextField userTextField = new TextField();
            userTextField.setPrefWidth(200);
            grid.add(userTextField, 1, 1);

            Label pw = new Label("Password:");
            grid.add(pw, 0, 2);

            PasswordField pwBox = new PasswordField();
            pwBox.setPrefWidth(200);
            grid.add(pwBox, 1, 2);

            Label displayNameLabel = new Label("Display Name:");
            grid.add(displayNameLabel, 0, 3);

            TextField displayNameField = new TextField();
            displayNameField.setPrefWidth(200);
            grid.add(displayNameField, 1, 3);

            Button btn = new Button("Register");
            grid.add(btn, 1, 4);

            final Text actiontarget = new Text();
            grid.add(actiontarget, 1, 6);

            btn.setOnAction(e -> {
                String username = userTextField.getText();
                String displayName = displayNameField.getText();
                String password = pwBox.getText();
                User user = admin.createUser(username, displayName, password);
                if (user != null) {
                    // User was created successfully, now go back to the login screen.
                    App.goToLoginScreen();
                } else {
                    actiontarget.setText("Username already exists.");
                }
            });

            scene = new Scene(grid, 800, 600);
        }
        return scene;
    }
}
