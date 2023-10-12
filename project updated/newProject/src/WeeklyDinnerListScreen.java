import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class WeeklyDinnerListScreen {
    private static Scene scene;

    public static Scene getScene(WeeklyDinnerList weeklyDinnerList) {
        if (scene == null) {
            VBox vbox = new VBox(10);
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(50));

            Label titleLabel = new Label("Weekly Dinner List");
            titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

            // Display the owner of the weekly dinner list
            Label ownerLabel = new Label("Owner: " + weeklyDinnerList.getOwner().getUsername());

            // Display the start and end dates of the weekly dinner list
            Label dateRangeLabel = new Label(
                    "Date Range: " + weeklyDinnerList.getStartDate() + " to " + weeklyDinnerList.getEndDate());

            Button backButton = new Button("Back");
            backButton.setOnAction(e -> App.goToRecipeManagement(App.getLoggedInUser()));

            vbox.getChildren().addAll(titleLabel, ownerLabel, dateRangeLabel, backButton);

            scene = new Scene(vbox, 400, 300);
        }

        return scene;
    }
}
