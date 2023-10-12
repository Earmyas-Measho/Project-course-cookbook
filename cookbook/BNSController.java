package cookbook;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.lang.model.util.Types;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class BNSController implements Initializable {

    @FXML
    private Pane p;
    @FXML
    private VBox container;
    @FXML
    private ImageView TraStar;
    @FXML
    private HBox leftHbox;
    @FXML
    private TextField search;
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private Button ingButton;
    @FXML
    private Button nameButton;
    @FXML
    private Button tagButton;
    @FXML
    private Button favButton;
    @FXML
    private Button backButton;

    private Controller controller;

    private ArrayList<Pane> recipePanes = new ArrayList<Pane>();
    int nrofVisibleRec = 10;
    int nrOfTags = 4;
    String tagName;
    String searchString = "a";
    String searchName;
    String searchCat;
    ArrayList<RecipeEntity> lst = new ArrayList<>();
    String[] alltags = null;
    public static boolean doNotAddToWeekly = false;
    String nameOfRecClicked;
    ArrayList<RecipeEntity> userFav = DataBaseConnections.getFavoriteRecipes(DataBaseConnections.getIDFromUserName(Login.user.getUsername()));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Login.firstLaunch = false;
        ingButton.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");
        nameButton.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");
        tagButton.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");
        backButton.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");
        favButton.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");

        //rootAnchorPane.setBackground(new Background(new BackgroundFill(Color.web(LoginMenu.backgroundColor), CornerRadii.EMPTY, Insets.EMPTY)));
        rootAnchorPane.setStyle("-fx-background-color:"+ LoginMenu.backgroundColor +";");

        if (searchString != null) {
            lst = DataBaseConnections.getRecipeByIngredient(searchString);
            nrofVisibleRec = lst.size();
        } else if (searchName != null) {
            lst = DataBaseConnections.getRecipeByName(searchName);
            nrofVisibleRec = lst.size();
        } else if (searchCat != null){
            lst = DataBaseConnections.getRecipeByCategory(searchCat);
            nrofVisibleRec = lst.size();
        } else if (searchCat == null && searchName == null && searchString == null){
            lst = DataBaseConnections.getFavoriteRecipes(DataBaseConnections.getIDFromUserName(Login.user.getUsername()));
            nrofVisibleRec = lst.size();
        }
        
        
        for (int i = 0; i < nrofVisibleRec ; i++) {
            ImageView recImage = new ImageView("./cookbook/resources/img/Good_Food.jpg");
            recImage.setFitHeight(70);
            recImage.setFitWidth(70);
            HBox tBox = new HBox();
            Label recName = new Label(lst.get(i).getName());
            TextArea shortDesc = new TextArea(lst.get(i).getShortDesc());
            String description = lst.get(i).getDescription();

            //Getting tags

            ArrayList<RecipeEntity> allrecipes = DataBaseConnections.getAllRecipes();

            
            for (int x = 0; x < allrecipes.size(); x++) {
                //System.out.println(allrecipes.get(x).getCategory());
                if ((allrecipes.get(x).getName()).equals(lst.get(i).getName())) {
                    String cats = allrecipes.get(x).getCategory();
                    //System.out.println(cats);
                    alltags = cats.split(",");
                    nrOfTags = alltags.length;
                }
            }

            for (int j = 0; j < nrOfTags; j++) {
                tagName = alltags[j] + ",";
                Label tag = new Label(tagName);
                tBox.getChildren().add(tag);
            }
            
            ImageView gStar = new ImageView("./cookbook/resources/img/GStar.png");
            ImageView tStar = new ImageView("./cookbook/resources/img/TStar.png");

            CheckBox favCheckBox = new CheckBox();
            favCheckBox.setTranslateX(80);
            shortDesc.setPrefHeight(100);
            shortDesc.setPrefWidth(200);
            shortDesc.setTranslateX(300);
            shortDesc.setVisible(false);
            shortDesc.setEditable(false);

            shortDesc.setWrapText(true);
            shortDesc.setMaxSize(180, 80);
            shortDesc.setTranslateX(400);
            recImage.setFitWidth(160);
            recImage.setFitHeight(100);
            recName.setTranslateX(170);
            tStar.setTranslateX(330);
            tStar.setTranslateY(34);
            gStar.setTranslateX(330);
            gStar.setTranslateY(34);
            tStar.setFitHeight(20);
            tStar.setFitWidth(20);
            gStar.setFitHeight(20);
            gStar.setFitWidth(20);
            gStar.setVisible(false);
            tStar.setVisible(true);
            favCheckBox.setTranslateX(330);
            favCheckBox.setTranslateY(34);
            favCheckBox.setOpacity(0.0);
            tBox.setTranslateX(170);
            tBox.setTranslateY(40);
            recName.setStyle("-fx-font-size: 20px");

            Pane copyPane = new Pane();
            copyPane.setMaxSize(500, 120);
            copyPane.setMinSize(500, 120);
            copyPane.getChildren().addAll(recImage, recName, tBox, gStar, tStar, favCheckBox, shortDesc);
            container.getChildren().add(copyPane);
            if (!recipePanes.contains(copyPane)) {
                recipePanes.add(copyPane);
            } 

            if(DataBaseConnections.recipeIsFavOfUser(DataBaseConnections.getIDFromUserName(Login.user.getUsername()),recName.getText())){
                tStar.setVisible(false);
                gStar.setVisible(true);
            } else {
                tStar.setVisible(true);
                gStar.setVisible(false);
            }

            String[] tags = alltags;
            
            recImage.setOnMouseClicked(event -> {
                System.out.println(recName.getText());
                nameOfRecClicked = recName.getText();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("RecipePage.fxml"));
                try {
                    Parent root = loader.load();
                    Controller controller = loader.getController();
                    
                    controller.setRecName(recName.getText());
                    if(DataBaseConnections.recipeIsFavOfUser(DataBaseConnections.getIDFromUserName(Login.user.getUsername()),recName.getText())){
                        controller.setFav(recName.getText());
                    }
                    //change to long desc when short in DB
                    String recIngredients = DataBaseConnections.getAllIngrOfRecipe(DataBaseConnections.getRecipeIDFromName(recName.getText()));
                    controller.setDescription(description, recIngredients, 2);
                    for (int a = 0; a < tags.length; a++) {
                        System.out.println(tags[a]);
                    }
                    controller.setTags(tags);
                    controller.setShortDesc(shortDesc.getText());
                    controller.initialize(null, null);
                    
                    loader.setController(controller);
                    Stage recipeStage = new Stage();
                    Scene scene = new Scene(root);
                    recipeStage.setTitle("Recipe instance");
                    recipeStage.setScene(scene);
                    recipeStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            


            copyPane.setOnMouseEntered(event -> {
                shortDesc.setVisible(true);
            });
            copyPane.setOnMouseExited(event -> {
                shortDesc.setVisible(false);
            });

            favCheckBox.setOnAction(event -> {
                try {
                    if(tStar.isVisible()){
                        tStar.setVisible(false);
                        gStar.setVisible(true);
                        DataBaseConnections.addRecipeToFavorites(DataBaseConnections.getRecipeIDFromName(recName.getText()), DataBaseConnections.getIDFromUserName(Login.user.getUsername()));
                        return;
                    }
                    if(!tStar.isVisible()){
                        tStar.setVisible(true);
                        gStar.setVisible(false);
                        DataBaseConnections.deleteRecipeFromFavorites(DataBaseConnections.getRecipeIDFromName(recName.getText()), DataBaseConnections.getIDFromUserName(Login.user.getUsername()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }
    }

    public void pressFavCkeckbox(ActionEvent event){
        //Add fav to user in DB
        if(TraStar.isVisible()){
            TraStar.setVisible(false);
            return;
        }
        if(!TraStar.isVisible()){
            TraStar.setVisible(true);
        }
    }

    @FXML
    void goBack(ActionEvent event) throws FileNotFoundException {
        doNotAddToWeekly = true;
        LoginMenu app = new LoginMenu();
        app.start((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    @FXML
    void search(ActionEvent event) {
        searchName = null;
        searchCat = null;
        searchString = search.getText();
        System.out.println(searchString);
        container.getChildren().clear();
        recipePanes.clear();
        initialize(null, null);
    }

    @FXML
    void searchByName(ActionEvent event) {
        searchString = null;
        searchCat = null;
        searchName = search.getText();
        System.out.println(searchName);
        container.getChildren().clear();
        recipePanes.clear();
        initialize(null, null);
    }

    @FXML
    void searchByTag(ActionEvent event) {
        searchString = null;
        searchName = null;
        searchCat = search.getText();
        System.out.println(searchCat);
        container.getChildren().clear();
        recipePanes.clear();
        initialize(null, null);
        
    }
    @FXML
    void searchByFav(ActionEvent event) {
        searchString = null;
        searchName = null;
        searchCat = null;
        container.getChildren().clear();
        recipePanes.clear();
        initialize(null, null);
    }
    
}
