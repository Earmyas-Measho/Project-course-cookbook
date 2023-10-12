package cookbook;

import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HelpButton extends Application {
    private Stage primaryStage;
    private String currentPage;

    private Button inboxButton;
    private Button btnShowDinnerList;
    private Button backButton;
    private Label pageTitleLbl;
    private TextArea helpTextArea;
    private ChoiceBox<String> selectPage;

    private String homePageStr = "Home Page";
    private String recipeViewStr = "Recipe View";
    private String createRecipeStr = "Create New Recipe";
    private String inboxStr = "Inbox";
    private String weeklyDinnersStr = "Weekly Dinners";
    private String shoppingListStr = "Weekly Shopping List";
    private String adminTableStr = "Admin Page: Table of Users";
    private String adminCreateStr = "Admin Page: Create New User";
    private String adminEditStr = "Admin Page: Edit User";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        String path = System.getProperty("user.dir");
        primaryStage.getIcons().add(new Image(path + "/project-cookbook-perl/cookbook/resources/img/cookbook_logo2.png"));
        Login.firstLaunch = false;
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Help Center");

        BorderPane root = new BorderPane();
        VBox container = new VBox(10);
        container.setPadding(new Insets(10));

        inboxButton = new Button("Inbox");
        btnShowDinnerList = new Button("Show Dinner List");
        backButton = new Button("Back");
        pageTitleLbl = new Label("Help Center");
        helpTextArea = new TextArea();
        selectPage = new ChoiceBox<>();
        selectPage.getItems().addAll(
                homePageStr,
                recipeViewStr,
                createRecipeStr,
                inboxStr,
                weeklyDinnersStr,
                shoppingListStr,
                adminTableStr,
                adminCreateStr,
                adminEditStr
        );
        selectPage.setValue("Select Help Page");
        selectPage.setOnAction(this::selectPageChoiceBox);
        selectPage.setStyle("-fx-background-color:" + LoginMenu.buttonColor + "; -fx-text-fill: white;");

        backButton.setOnAction(this::handleBackButtonAction);
        backButton.setStyle("-fx-background-color:" + LoginMenu.buttonColor + "; -fx-text-fill: white;");
        container.getChildren().addAll(selectPage, pageTitleLbl, helpTextArea, backButton);
        root.setCenter(container);
        root.setStyle("-fx-background-color:" + LoginMenu.backgroundColor + ";");
        root.setBackground(new Background(new BackgroundFill(Color.web(LoginMenu.backgroundColor), CornerRadii.EMPTY, Insets.EMPTY)));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public void selectPageChoiceBox(ActionEvent event) {
        String selectedPage = selectPage.getValue();
        loadData(selectedPage);
    }

    public void loadData(String pageName) {
        this.currentPage = pageName;

        // Load help text based on the selected page
        String helpTxt;
        if (pageName.equals(homePageStr)) {
            helpTxt = ("- Search for a recipe:"
            + "\n1) The 'Search Recipe Bar' allows you to search for a specific recipe if the name is known."
            + "\n2) The Categories Choice Box allows you to select a specific category of recipes (including favorite recipes)"
            + "\n3) Click on the 'Search' button to apply your search parameters"
            + "\n"
            + "\n- Show a recipe:"
            + "\n1) Click on the desired recipe from the list to select it"
            + "\n2) Click the 'Show Recipe' button"
            + "\n"
            + "\n- Create a new recipe:"
            + "\n1) Click on the 'Create Recipe' button and fill in the different fields that will show up in the 'Create New Recipe' page.");
        } else if (pageName.equals(recipeViewStr)) {
            helpTxt = "- Add a comment:"
            + "\nClick on the 'Comment' button and follow the pop-up's instructions"
            + "\n\n- Show comments:"
            + "\nClick on the 'Display Comments' button and follow the pop-up's instructions"
            + "\n\n- Share the recipe"
            + "\nClick on the 'Share' button and follow the pop-up's instructions"
            + "\n\n- Add the recipe to your Weekly Dinners Page"
            + "\nClick on the 'Add to Weekly List' button and follow the pop-up's instructions"
            + "\n\n- Add / remove the recipe to / from your favorites"
            + "\nClick on the 'Favorite' checkbox"
            + "\n\n- Add a tag to your recipe"
            + "\nClick on the 'Add Tag' button and follow the pop-up's instructions";
        } else if (pageName.equals(createRecipeStr)) {
            helpTxt = "- Create a recipe:"
            + "\n\nIn the different fields:"
            + "\n1) Give the recipe a name"
            + "\n2) Give the recipe a short description / summary"
            + "\n3) Give the recipe a long description / instructions"
            + "\n4) Select an ingredient in the 'Ingredient' choice box, enter an amount (a number) and a unit"
            + " and confirm to add an ingredient"
            + "\n5) Click on the 'Create Recipe' button";
        } else if (pageName.equals(inboxStr)) {
            helpTxt = "- View a message:"
            + "\nClick on the wanted message in the 'Inbox' section"
            + "\n\n- View the linked recipe:"
            + "\nClick on the Recipe Icon button next to the 'Recipe linked' field";
        } else if (pageName.equals(weeklyDinnersStr)) {
            helpTxt = ("- Select the desired week in the 'Weeks' choice box"
            + "\n\n- View the desired recipe:"
            + "\nClick on the desired recipe and click on the 'View Recipe' button"
            + "\n\n- View the Shopping List for the selected week:"
            + "\nClick on the 'Shopping List' button");
        } else if (pageName.equals(shoppingListStr)) {
            helpTxt = "- Remove an ingredient that you already have at home:"
            + "\nSelect the unwanted ingredient from the table and click on the 'Delete' button";
        } else if (pageName.equals(adminTableStr)) {
            helpTxt = "- Create a new user:"
            + "\nClick on the 'Create New User' button"
            + "\n\n- Edit an existing user:"
            + "\nSelect a user from the table and click on the 'Edit' button"
            + "\n\n- Delete an existing user:"
            + "\nSelect a user from the table and click on the 'Delete' button";
        } else if (pageName.equals(adminCreateStr)) {
            helpTxt = "- Create a new user:"
            + "\nFill in the user's information in the fields and click on the 'Create User' button"
            + "\nClick on the 'Cancel' button to go back to the Admin Table without changes";
        } else if (pageName.equals(adminEditStr)) {
            helpTxt = "- Edit an existing user:"
            + "\nChange the user's information in the fields and click on the 'Save' button"
            + "\nClick on the 'Cancel' button to go back to the Admin Table without changes";
        } else {
            helpTxt = "Sorry, no help is available for this page.";
        }

        pageTitleLbl.setText(pageName);
        helpTextArea.setText(helpTxt);
    }

    public void handleBackButtonAction(ActionEvent event) {
        // Handle the back button action based on the current page
        try {
            LoginMenu mainMenu = new LoginMenu();
            mainMenu.start(new Stage());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        primaryStage.close();
        /* 
        if (currentPage.equals(adminTableStr) || currentPage.equals(adminEditStr)) {
            // Navigate to the admin page
            // Implement your code to navigate to the admin page here
        } else {
            // Navigate to the previous page
            // Implement your code to navigate to the previous page here
        }
        */
    }
}

