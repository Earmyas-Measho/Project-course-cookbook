package secfolder;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create labels and input fields for username and password
        Label userLabel = new Label("Username:");
        TextField userField = new TextField();
        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();

        // Create a login button
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            String username = userField.getText();
            String password = passField.getText();
            if (username.equals("admin") && password.equals("password")) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Login failed.");
            }
        });

        // Create a layout with the labels, input fields, and login button
        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setVgap(5);
        root.setHgap(10);
        root.addRow(0, userLabel, userField);
        root.addRow(1, passLabel, passField);
        root.add(loginButton, 1, 2);

        // Create a scene with the layout
        Scene scene = new Scene(root, 300, 150);

        // Set the scene on the primary stage
        primaryStage.setScene(scene);

        // Set the title of the primary stage
        primaryStage.setTitle("Login");

        // Show the primary stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
