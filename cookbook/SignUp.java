package cookbook;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SignUp extends Application {

    private static final String APP_TITLE = "My Cookbook - Create Account";
    private static final double APP_PADDING = 0.05; // 5% padding around screen edges
    private static final int APP_SPACING = 10;
    private static final int APP_WIDTH = 600;
    private static final int APP_HEIGHT = 400;

    @Override
    public void start(Stage primaryStage) throws Exception {

        String path = System.getProperty("user.dir");
        primaryStage.getIcons().add(new Image(path + "/project-cookbook-perl/cookbook/resources/img/cookbook_logo2.png"));

        // Create input fields
        Label usernameLabel = new Label("Username:");
        usernameLabel.setTextFill(Color.BLACK);
        TextField usernameTextField = new TextField();
        usernameTextField.setPrefWidth(200);

        Label displayNameLabel = new Label("Display Name:");
        displayNameLabel.setTextFill(Color.BLACK);
        TextField displayNameTextField = new TextField();
        displayNameTextField.setPrefWidth(200);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setTextFill(Color.BLACK);
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(200);

        Label signedUp = new Label("Signup complete!");
        signedUp.setVisible(false);

        // Create back button
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            Login app = new Login();
            try {
                app.start(new Stage());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            primaryStage.close();
        });

        // Create create account button
        Button createAccountButton = new Button("Create Account");
        createAccountButton.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");
        createAccountButton.setOnAction(e ->{
            DataBaseConnections.registration(usernameTextField.getText(), passwordField.getText());
            int id = DataBaseConnections.getIDFromUserName(usernameTextField.getText());
            DataBaseConnections.addWeeklyMealPlan(id, " ,  ,  ,  ,  ,  ,  , ");
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), signedUp);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            signedUp.setVisible(true);
            fadeOut.play();
        });

        // Create welcome message label
        Label welcomeLabel = new Label("SignUp");
        welcomeLabel.setTextFill(Color.BLACK);
        welcomeLabel.setStyle("-fx-font-size: 24px;");
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
                welcomeLabel,
                usernameLabel, usernameTextField,
                displayNameLabel, displayNameTextField,
                passwordLabel, passwordField,
                createAccountButton,
                backButton, signedUp
        );

        // Create scene and show stage
        Scene scene = new Scene(root, APP_WIDTH, APP_HEIGHT);
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

