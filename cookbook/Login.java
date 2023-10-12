package cookbook;

import java.sql.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

public class Login extends Application {

    String path = System.getProperty("user.dir");
    private static final String APP_TITLE = "My Cookbook";
    private final String APP_LOGO_PATH = path + "/project-cookbook-perl/cookbook/resources/img/cookbook_logo2.png";
    private static final double APP_PADDING = 0.05; // 5% padding around screen edges
    private static final int APP_SPACING = 10;
    public static final int APP_WIDTH = 600;
    public static final int APP_HEIGHT = 400;
    public static UserEntity user;
    public static boolean firstLaunch = true;

    @Override
    public void start(Stage primaryStage) throws Exception {

        String path = System.getProperty("user.dir");
        primaryStage.getIcons().add(new Image(path + "/project-cookbook-perl/cookbook/resources/img/cookbook_logo2.png"));

        System.out.println(path);
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
        loginButton.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");
        loginButton.setOnAction(event -> {
            user = DataBaseConnections.login(usernameTextField.getText(), passwordField.getText());
            if (user != null) {
                user = new UserEntity(usernameTextField.getText());
                LoginMenu menu = new LoginMenu();
                LoginMenu.user = user;
                LoginMenu.backgroundColor = DataBaseConnections.getTheme(Login.user.getUsername());
                        try {
                            menu.start(new Stage());
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        primaryStage.close();

            }
        });

         // Create create account button
         Button createAccountButton = new Button("Create Account");
         createAccountButton.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");
         createAccountButton.setOnAction(event -> {
             try {
                 SignUp signUp = new SignUp();
                 signUp.start(new Stage());
                 primaryStage.close();
             } catch (Exception e) {
                 e.printStackTrace();
             }
         });

         // Create layout for buttons
        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(loginButton, createAccountButton);

        // Create layout
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(APP_PADDING * APP_HEIGHT, 
                                    APP_PADDING * APP_WIDTH, 
                                    APP_PADDING * APP_HEIGHT, 
                                    APP_PADDING * APP_WIDTH));
        root.setSpacing(APP_SPACING);
        root.setBackground(new Background(new BackgroundFill(Color.web(LoginMenu.backgroundColor), CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().addAll(
                logoImageView,
                welcomeLabel,
                usernameLabel, usernameTextField,
                passwordLabel, passwordField,
                loginButton, buttonLayout
        );

        // Create scene and show stage
        Scene scene = new Scene(root, APP_WIDTH, APP_HEIGHT);
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
        //System.out.println(l.uName + l.pass);
    }
    
}
