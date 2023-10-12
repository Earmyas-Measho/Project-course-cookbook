package secfolder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.crypto.Data;

import cookbook.DataBaseConnections;
import cookbook.Login;
import cookbook.LoginMenu;
import cookbook.RecipeEntity;
import cookbook.UserEntity;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RecipeManagementView extends Application {
  private static UserEntity loggedInUser;
  private ListView<Recipe> recipesListView;

  public static void setLoggedInUser(UserEntity loggedInUser) {
    RecipeManagementView.loggedInUser = loggedInUser;
  }

  @Override
  public void start(Stage primaryStage) {

    String path = System.getProperty("user.dir");
    primaryStage.getIcons().add(new Image(path + "/project-cookbook-perl/cookbook/resources/img/cookbook_logo2.png"));

    Login.firstLaunch = false;

    primaryStage.setTitle("Recipe Management");

    VBox root = new VBox();
    root.setSpacing(10);
    root.setPadding(new Insets(5, 10, 5, 10));
    root.setStyle("-fx-background-color: " + LoginMenu.backgroundColor + ";");

    Label recipeNameLabel = new Label("Recipe Name:");
    TextField recipeNameField = new TextField();

    Label shortDescriptionLabel = new Label("Short Description:");
    TextField shortDescriptionField = new TextField();

    Label tagsLabel = new Label("Categories:");
    TextField tagsField = new TextField();
    tagsField.setPromptText("Veagan, Healthy, ...");

    Label ingredientsLabel = new Label("Ingredients:                                                                      Amount and unit:");
    TextArea ingredientsArea = new TextArea();
    ingredientsArea.setWrapText(true);
    ingredientsArea.setPromptText("Butter, Milk, ...");
    TextArea unitTextArea = new TextArea();
    unitTextArea.setPromptText("100 gram, 1 liter, ...");
    HBox ingredientHBox = new HBox(ingredientsArea, unitTextArea);

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
    //ObservableList<Recipe> recipesList = FXCollections.observableArrayList(loggedInUser.getAllRecipes());
    //recipesListView.setItems(recipesList);

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
              //loggedInUser.unmarkRecipeAsFavorite(recipe);
              favoriteLabel.setText("☆");
            } else {
              //loggedInUser.markRecipeAsFavorite(recipe);
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
    Button goBack = new Button("Back");

    addRecipeButton.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");
    deleteRecipeButton.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");
    editRecipeButton.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");
    showFavoriteRecipesButton.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");
    showAllRecipesButton.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");
    goBack.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");

    HBox buttonsBox = new HBox(10); // Added an HBox to arrange buttons horizontally.
    buttonsBox.getChildren().addAll(addRecipeButton, deleteRecipeButton, editRecipeButton,
        showFavoriteRecipesButton, showAllRecipesButton, goBack);
    // Add the modeLabel
    Label modeLabel = new Label("All Recipes");
    modeLabel.setStyle("-fx-font-weight: bold;");

    root.getChildren().addAll(recipeNameLabel, recipeNameField, shortDescriptionLabel, shortDescriptionField,
        ingredientsLabel, ingredientHBox, detailedDescriptionLabel, detailedDescriptionArea, tagsLabel, tagsField, buttonsBox,
        recipeInfo, recipesListView);

    // Add action event handlers for the showFavoriteRecipesButton and
    // showAllRecipesButton
    showFavoriteRecipesButton.setOnAction(e -> {
      //List<Recipe> favoriteRecipes = loggedInUser.getFavoriteRecipes();

      //recipesListView.setItems(FXCollections.observableArrayList(favoriteRecipes));
      modeLabel.setText("Favorite Recipes");
    });

    goBack.setOnAction(e -> {
      try {
        LoginMenu mainMenu = new LoginMenu();
        mainMenu.start(new Stage());
        primaryStage.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    });

    showAllRecipesButton.setOnAction(e -> {
      //recipesListView.setItems(FXCollections.observableArrayList(loggedInUser.getAllRecipes()));
      modeLabel.setText("All Recipes");
    });

    // Add action event handler for the delete button
    deleteRecipeButton.setOnAction(e -> {
      Recipe selectedRecipe = recipesListView.getSelectionModel().getSelectedItem();
      if (selectedRecipe != null) {
        //loggedInUser.removeRecipe(selectedRecipe);
        //recipesListView.setItems(FXCollections.observableArrayList(loggedInUser.getAllRecipes()));
      }
    });
    addRecipeButton.setOnAction(e -> {
      String recipeName = recipeNameField.getText();
      String shortDescription = shortDescriptionField.getText();
      String ingredients = ingredientsArea.getText();
      String unitAndAmount = unitTextArea.getText();
      String detailedDescription = detailedDescriptionArea.getText();
      String tags = tagsField.getText();

      System.out.println("Ingredient: " + ingredients + " Amount: " + unitAndAmount);

      RecipeEntity newRecipe = new RecipeEntity(recipeName, ingredients, detailedDescription, tags);
      //newRecipe.setFavorite(false);
      //loggedInUser.addRecipe(newRecipe);

      recipeNameField.clear();
      shortDescriptionField.clear();
      ingredientsArea.clear();
      unitTextArea.clear();
      detailedDescriptionArea.clear();
      tagsField.clear();
      
      String ingrArr [] = ingredients.split(",");
      String unitNAmountArr [] = unitAndAmount.split(",");
      String totalIngrStr = "";
      for (int i = 0; i < unitNAmountArr.length; i++) {
        totalIngrStr += unitNAmountArr[i] + " " + ingrArr[i] + ", ";
      }

      //recipesListView.setItems(FXCollections.observableArrayList(loggedInUser.getAllRecipes()));
      DataBaseConnections.addRecipe(recipeName, detailedDescription, totalIngrStr, tags, shortDescription);
      userInputToAddIngredientToDB(ingredients, unitAndAmount, recipeName);
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

    Scene scene = new Scene(root, 600, 600);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public void userInputToAddIngredientToDB(String ingredientName, String amountAndUnit, String recipeNameField) {
    char [] ch = amountAndUnit.toCharArray();
    int ammount = 0;
    String unit = "";
    int nrOfingredients = 1;
    String temp1 = "";
    int posInCharArray;
    int ingrPos = 0;
    String ingrArray [] = ingredientName.split(",");
    String ammountNUnitArray [] = amountAndUnit.split(",");

    for (int i = 0; i < ch.length; i++) {
      if(String.valueOf(ch[i]).equals(",")){
        nrOfingredients ++;
      }
    }
    //loop for all ingredient
    while(nrOfingredients > 0){

    //loop for one ingr
      for (posInCharArray = 0; posInCharArray < ch.length; posInCharArray++) {

        if(String.valueOf(ch[posInCharArray]).equals(",")){
          break;
        }
        if(Character.isDigit(ch[posInCharArray])){
          temp1 += String.valueOf(ch[posInCharArray]);
        }
        
        if(!Character.isDigit(ch[posInCharArray])){
          unit += String.valueOf(ch[posInCharArray]);
        }

    }
      ammount += Integer.parseInt(temp1);
      String finalUnit = unit.replace(" ", "");
      if(DataBaseConnections.IngredientExists(ingrArray[ingrPos]) == false){
        DataBaseConnections.addIngredient(ingrArray[ingrPos], ammount, finalUnit, recipeNameField);
      }
      nrOfingredients --;
      ingrPos ++;
      unit = "";
      ammount = 0;
      temp1 = "";
      posInCharArray = 0;
  }
}

  public static void main(String[] args) {
    User loggedInUser = new User("username", "displayName", "password");
 
    //setLoggedInUser(loggedInUser);
    launch(args);
  }

  private List<Ingredient> parseIngredients(String ingredientsText) {
    List<Ingredient> ingredients = new ArrayList<>();
    String[] lines = ingredientsText.split("\n");

    for (String line : lines) {
      String[] parts = line.split(","); // Assuming the format is "name, quantity, unit"
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
          //loggedInUser.markRecipeAsFavorite(recipe);
        } else {
          //loggedInUser.unmarkRecipeAsFavorite(recipe);
        }
      }

      //recipesListView.setItems(FXCollections.observableArrayList(loggedInUser.getAllRecipes()));
      editStage.close();
    });

    Scene editScene = new Scene(editRoot, 400, 400);
    editStage.setScene(editScene);
    editStage.show();
  }

}
