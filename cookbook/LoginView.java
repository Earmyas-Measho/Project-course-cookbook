package cookbook;

import java.sql.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cookbook.DataBaseConnections;
import cookbook.UserEntity;

import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginView extends Application {


    private static final String APP_TITLE = "My Cookbook";
    private static final String APP_LOGO_PATH = "C:\\javaFX\\LOGO.jpg";
    private static final double APP_PADDING = 0.05; // 5% padding around screen edges
    private static final int APP_SPACING = 10;
    private static final int APP_WIDTH = 600;
    private static final int APP_HEIGHT = 400;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create logo image
        File logoFile = new File(APP_LOGO_PATH);
        Image logoImage = new Image(logoFile.toURI().toString());
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setPreserveRatio(true);
        logoImageView.setFitHeight(150);

        // Create welcome message label
        Label welcomeLabel = new Label("Welcome to The Cookbook!");
        welcomeLabel.setTextFill(Color.BLACK);
        welcomeLabel.setStyle("-fx-font-size: 16px;");

        // Create input fields
        Label usernameLabel = new Label("Username:");
        usernameLabel.setTextFill(Color.BLACK);
        TextField usernameTextField = new TextField();
        usernameTextField.setPrefWidth(200);
        Label passwordLabel = new Label("Password:");
        passwordLabel.setTextFill(Color.BLACK);
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(200);
        //Need to create login as admin button
        // Create login button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #FB6601; -fx-text-fill: white;");
        loginButton.setOnAction(event -> {
        	UserEntity user = DataBaseConnections.login(usernameTextField.getText(), passwordField.getText());
    		if(user == null) {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setContentText("User does not exists");
    			alert.show();
    		} else {
    			Alert alert = new Alert(AlertType.CONFIRMATION);
    			alert.setContentText("User exists");
    			alert.show();
    		}
        });
        
        // Create layout
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(APP_PADDING * APP_HEIGHT, 
                                    APP_PADDING * APP_WIDTH, 
                                    APP_PADDING * APP_HEIGHT, 
                                    APP_PADDING * APP_WIDTH));
        root.setSpacing(APP_SPACING);
        root.setBackground(new Background(new BackgroundFill(Color.web("#F6DFCC"), CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().addAll(
                logoImageView,
                welcomeLabel,
                usernameLabel, usernameTextField,
                passwordLabel, passwordField,
                loginButton
        );

        // Create scene and show stage
        Scene scene = new Scene(root, APP_WIDTH, APP_HEIGHT);
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        LoginView l = new LoginView();
        //l.db.connect();
        launch(args);
        //System.out.println(l.uName + l.pass);
    }
}

