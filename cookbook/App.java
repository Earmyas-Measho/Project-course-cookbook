package cookbook;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Charger l'image à afficher
        String path = System.getProperty("user.dir");
        System.out.println(path);
        File file = new File(path + "/project-cookbook-perl/cookbook/resources/img/cookbook_logo2.png");
        Image image = new Image(file.toURI().toString());

        // Créer une ImageView pour afficher l'image
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);

        // Créer une transition de mise à l'échelle pour effectuer l'animation de zoom
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), imageView);
        scaleTransition.setFromX(0.5);
        scaleTransition.setFromY(0.5);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(5);

        // Créer un conteneur de mise en page pour la ImageView
        StackPane root = new StackPane(imageView);

        // Créer une scène pour afficher le conteneur de mise en page
        Scene scene = new Scene(root, 600, 400);
        root.setBackground(new Background(new BackgroundFill(Color.web("white"), CornerRadii.EMPTY, Insets.EMPTY)));
        // Ajouter la scène à la fenêtre principale
        primaryStage.setScene(scene);
        primaryStage.setTitle("My Cookbook");

        // Démarrer l'animation de zoom
        scaleTransition.play();

        // Quand l'animation se termine, passer à la classe Login
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), root);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        scaleTransition.setOnFinished(event -> {
            fadeOut.play();
            fadeOut.setOnFinished(fadeEvent -> {
            Login login = new Login();
            try {
                login.start(primaryStage);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
            });
        primaryStage.show();

        
    }

    public static void main(String[] args) {
        launch(args);
    }
}