import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static Stage stage;
    private static admin admin = new admin(); // Create an Admin object
    private static UserManagement userManagement = new UserManagement(admin);
    private static RecipeManagementView recipeManagementView;
    private static User loggedInUser;
    private static Scene previousScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        goToLoginScreen();
        primaryStage.show();
    }

    static void goToLoginScreen() {
        setPreviousScene(stage.getScene());
        Scene scene = LoginScreen.getScene();
        stage.setScene(scene);
    }

    static void userLoggedIn(User user) {
        loggedInUser = user;
        setPreviousScene(stage.getScene());
        goToRecipeManagement(loggedInUser);
    }

    static void goToMainScreen() {
        setPreviousScene(stage.getScene());
        Scene scene = MainScreen.getScene();
        stage.setScene(scene);
    }

    static void goToRegistrationScreen() {
        setPreviousScene(stage.getScene());
        Scene scene = UserRegistration.getScene();
        stage.setScene(scene);
    }

    static void goToRecipeManagement(User loggedInUser) {
        App.loggedInUser = loggedInUser;
        setPreviousScene(stage.getScene());
        recipeManagementView = new RecipeManagementView(loggedInUser);
        Scene scene = new Scene(recipeManagementView, 800, 600);
        stage.setScene(scene);
    }

    static void goToUserManagement() {
        setPreviousScene(stage.getScene());
        Scene scene = userManagement.createScene();
        stage.setScene(scene);
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setPreviousScene(Scene scene) {
        previousScene = scene;
    }

    public static Scene getPreviousScene() {
        return previousScene;
    }

}
