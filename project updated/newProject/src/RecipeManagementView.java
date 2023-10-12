import javafx.scene.Parent;
import javafx.scene.Node;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class RecipeManagementView extends VBox {
  private User user;
  private ListView<Recipe> recipesListView;
  private HBox buttonsBox;

  public RecipeManagementView(User loggedInUser) {
    this.user = loggedInUser;
    this.setSpacing(20);
    this.setPadding(new Insets(20, 20, 20, 20));
    this.setStyle("-fx-background-color: #87CEEB;");

    buttonsBox = new HBox(10);
    Button adminButton = new Button("Admin");
    adminButton.setOnAction(e -> {
      UserManagement userManagement = new UserManagement();
      Scene userManagementScene = userManagement.createScene();
      Stage currentStage = (Stage) getScene().getWindow();
      currentStage.setScene(userManagementScene);
    });
    buttonsBox.getChildren().add(adminButton);

    Label recipeNameLabel = new Label("Recipe Name:");
    recipeNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    TextField recipeNameField = new TextField();

    Label shortDescriptionLabel = new Label("Short Description:");
    TextField shortDescriptionField = new TextField();

    Label ingredientsLabel = new Label("Ingredients:");
    TextArea ingredientsArea = new TextArea();
    ingredientsArea.setWrapText(true);

    Label detailedDescriptionLabel = new Label("Detailed Description:");
    TextArea detailedDescriptionArea = new TextArea();
    detailedDescriptionArea.setWrapText(true);

    // Add a ListView to display the user's recipes
    VBox recipeInfo = new VBox();
    recipeInfo.setSpacing(5);
    Label recipesLabel = new Label("Recipes:");
    recipeInfo.getChildren().add(recipesLabel);

    recipesListView = new ListView<>();
    // recipesListView.setStyle("-fx-background-color: #00FF00;");
    ObservableList<Recipe> recipesList = FXCollections.observableArrayList(loggedInUser.getAllRecipes());
    recipesListView.setItems(recipesList);

    recipesListView.setCellFactory(param -> new ListCell<Recipe>() {
      @Override
      protected void updateItem(Recipe recipe, boolean empty) {
        super.updateItem(recipe, empty);
        if (empty || recipe == null) {
          setText(null);
          setGraphic(null);
        } else {
          Label recipeInfo = new Label(recipe.getName());
          recipeInfo.setTextFill(Color.DARKBLUE);

          Label favoriteLabel = new Label();
          favoriteLabel.setTextFill(Color.RED);
          if (recipe.isFavorite()) {
            favoriteLabel.setText("★");
          } else {
            favoriteLabel.setText("☆");
          }

          HBox recipeHeaderBox = new HBox(5);
          recipeHeaderBox.getChildren().addAll(recipeInfo, favoriteLabel);
          setGraphic(recipeHeaderBox);

          // Add event filter directly to the Label
          favoriteLabel.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (recipe.isFavorite()) {
              loggedInUser.unmarkRecipeAsFavorite(recipe);
              favoriteLabel.setText("☆");
            } else {
              loggedInUser.markRecipeAsFavorite(recipe);
              favoriteLabel.setText("★");
            }
            recipesListView.refresh();
            e.consume();
          });

        }
      }

    });

    Button addRecipeButton = new Button("Add Recipe");
    Button deleteRecipeButton = new Button("Delete Recipe");
    Button editRecipeButton = new Button("Edit Recipe");
    Button showFavoriteRecipesButton = new Button("Show Favorite Recipes");
    Button showAllRecipesButton = new Button("Show All Recipes");

    Button myWeeklyDinnerListButton = new Button("My Weekly Dinner List");
    myWeeklyDinnerListButton.setOnAction(e -> {
      WeeklyDinnerList myWeeklyDinnerList = new WeeklyDinnerList(user, LocalDate.now(), LocalDate.now().plusDays(6));
      Scene weeklyDinnerListScene = WeeklyDinnerListScreen.getScene(myWeeklyDinnerList);
      Stage currentStage = (Stage) getScene().getWindow();
      currentStage.setScene(weeklyDinnerListScene);
    });

    buttonsBox.getChildren().addAll(addRecipeButton, deleteRecipeButton, editRecipeButton,
        showFavoriteRecipesButton, showAllRecipesButton, myWeeklyDinnerListButton);

    // Add the modeLabel
    Label modeLabel = new Label("All Recipes");
    modeLabel.setStyle("-fx-font-weight: bold;");

    this.getChildren().addAll(buttonsBox, recipeNameLabel, recipeNameField, shortDescriptionLabel,
        shortDescriptionField,
        ingredientsLabel, ingredientsArea, detailedDescriptionLabel, detailedDescriptionArea, recipeInfo,
        recipesListView);

    // Add action event handlers for the showFavoriteRecipesButton and
    // showAllRecipesButton
    showFavoriteRecipesButton.setOnAction(e -> {
      List<Recipe> favoriteRecipes = loggedInUser.getFavoriteRecipes();

      recipesListView.setItems(FXCollections.observableArrayList(favoriteRecipes));
      modeLabel.setText("Favorite Recipes");
    });

    showAllRecipesButton.setOnAction(e -> {
      recipesListView.setItems(FXCollections.observableArrayList(loggedInUser.getAllRecipes()));
      modeLabel.setText("All Recipes");
    });

    // Add action event handler for the delete button
    deleteRecipeButton.setOnAction(e -> {
      Recipe selectedRecipe = recipesListView.getSelectionModel().getSelectedItem();
      if (selectedRecipe != null) {
        loggedInUser.removeRecipe(selectedRecipe);
        recipesListView.setItems(FXCollections.observableArrayList(loggedInUser.getAllRecipes()));
      }
    });
    addRecipeButton.setOnAction(e -> {
      String recipeName = recipeNameField.getText();
      String shortDescription = shortDescriptionField.getText();
      List<Ingredient> ingredients = parseIngredients(ingredientsArea.getText());
      String detailedDescription = detailedDescriptionArea.getText();
      Recipe newRecipe = new Recipe(recipeName, shortDescription, ingredients, detailedDescription, 0);
      newRecipe.setFavorite(false);
      loggedInUser.addRecipe(newRecipe);

      recipeNameField.clear();
      shortDescriptionField.clear();
      ingredientsArea.clear();
      detailedDescriptionArea.clear();
      recipesListView.setItems(FXCollections.observableArrayList(loggedInUser.getAllRecipes()));
    });

    recipesListView.setOnMouseClicked(event -> {
      if (event.getClickCount() == 2) { // Double-click event
        Recipe selectedRecipe = recipesListView.getSelectionModel().getSelectedItem();
        if (selectedRecipe != null) {
          editRecipe(selectedRecipe);
        }
      }
    });

    editRecipeButton.setOnAction(e -> {
      Recipe selectedRecipe = recipesListView.getSelectionModel().getSelectedItem();
      if (selectedRecipe != null) {
        editRecipe(selectedRecipe);
      }
    });
  }

  private List<Ingredient> parseIngredients(String ingredientsText) {
    List<Ingredient> ingredients = new ArrayList<>();
    String[] lines = ingredientsText.split("\n");

    for (String line : lines) {
      String[] parts = line.split(",");
      if (parts.length == 3) {
        String name = parts[0].trim();
        double quantity = Double.parseDouble(parts[1].trim());
        String unit = parts[2].trim();
        ingredients.add(new Ingredient(name, quantity, unit));
      }
    }

    return ingredients;
  }

  private void editRecipe(Recipe recipe) {
    Stage editStage = new Stage();
    editStage.setTitle("Edit Recipe");

    VBox editRoot = new VBox();
    editRoot.setSpacing(10);
    editRoot.setPadding(new Insets(10, 10, 10, 10));

    Label editRecipeNameLabel = new Label("Recipe Name:");
    TextField editRecipeNameField = new TextField(recipe.getName());

    Label editShortDescriptionLabel = new Label("Short Description:");
    TextField editShortDescriptionField = new TextField(recipe.getShortDescription());

    Label editIngredientsLabel = new Label("Ingredients:");
    TextArea editIngredientsArea = new TextArea();
    editIngredientsArea.setWrapText(true);
    editIngredientsArea.setText(recipe.getIngredients().stream()
        .map(ingredient -> ingredient.getName() + ", " + ingredient.getQuantity() + ", " + ingredient.getUnit())
        .collect(Collectors.joining("\n")));

    Label editDetailedDescriptionLabel = new Label("Detailed Description:");
    TextArea editDetailedDescriptionArea = new TextArea();
    editDetailedDescriptionArea.setWrapText(true);
    editDetailedDescriptionArea.setText(recipe.getDetailedDescription());

    // Add favorite toggle feature inside the Edit Recipe window
    Label favoriteLabel = new Label("Favorite:");
    CheckBox favoriteCheckBox = new CheckBox();
    favoriteCheckBox.setSelected(recipe.isFavorite());

    HBox favoriteHBox = new HBox(5);
    favoriteHBox.getChildren().addAll(favoriteLabel, favoriteCheckBox);

    Button saveChangesButton = new Button("Save Changes");

    editRoot.getChildren().addAll(editRecipeNameLabel, editRecipeNameField, editShortDescriptionLabel,
        editShortDescriptionField, editIngredientsLabel, editIngredientsArea, editDetailedDescriptionLabel,
        editDetailedDescriptionArea, favoriteHBox, saveChangesButton);

    saveChangesButton.setOnAction(e -> {
      String newRecipeName = editRecipeNameField.getText();
      String newShortDescription = editShortDescriptionField.getText();
      List<Ingredient> newIngredients = parseIngredients(editIngredientsArea.getText());
      String newDetailedDescription = editDetailedDescriptionArea.getText();

      recipe.setName(newRecipeName);
      recipe.setShortDescription(newShortDescription);
      recipe.setIngredients(newIngredients);
      recipe.setDetailedDescription(newDetailedDescription);

      // Update favorite status
      if (favoriteCheckBox.isSelected() != recipe.isFavorite()) {
        if (favoriteCheckBox.isSelected()) {
          user.markRecipeAsFavorite(recipe);
        } else {
          user.unmarkRecipeAsFavorite(recipe);
        }
      }

      recipesListView.setItems(FXCollections.observableArrayList(user.getAllRecipes()));
      editStage.close();
    });

    Scene editScene = new Scene(editRoot, 400, 400);
    editStage.setScene(editScene);
    editStage.show();
  }

}
