package cookbook;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class InboxController implements Initializable{

    @FXML
    private Button backButton;

    @FXML
    private VBox container;

    @FXML
    private HBox leftHbox;

    @FXML
    private Pane p;

    @FXML
    private AnchorPane rootAnchorPane;

    private ArrayList<Pane> recipePanes = new ArrayList<Pane>();
    int nrofVisibleRec = 10;
    int nrOfTags = 4;
    String tagName;
    String searchString;
    String searchName;
    String searchCat;
    ArrayList<RecipeEntity> lst = new ArrayList<>();
    String[] alltags = null;
    String[] messages;

    private ArrayList<Message> messageList = new ArrayList<Message>();

    @FXML
    void goBack(ActionEvent event) throws FileNotFoundException {
        LoginMenu app = new LoginMenu();
        app.start((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");
        rootAnchorPane.setStyle("-fx-background-color:" + LoginMenu.backgroundColor + ";");

        /* 
        RecipeEntity recipe0 = DataBaseConnections.getRecipeByName("Eggs benedict").get(0);
        lst.add(recipe0);
        RecipeEntity recipe1 = DataBaseConnections.getRecipeByName("Cinnamon and Nuts Cake").get(0);
        lst.add(recipe1);
        */

        String userName = LoginMenu.user.getUsername();
        int id = DataBaseConnections.getIDFromUserName(userName);

        try {
            messageList = DataBaseConnections.getAllMessages(id);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        nrofVisibleRec = messageList.size();
        System.out.println(LoginMenu.user.getUsername());
        System.out.println(LoginMenu.user.getId());
        System.out.println("messages: "+nrofVisibleRec);


        for (int i = 0; i < nrofVisibleRec ; i++) {
            ImageView recImage = new ImageView("./cookbook/resources/img/Good_Food.jpg");

            int recipeId = messageList.get(i).getRecipeId();
            System.out.println(recipeId);
            RecipeEntity recipe = DataBaseConnections.getRecipeFromId(recipeId);

            recImage.setFitHeight(70);
            recImage.setFitWidth(70);
            HBox tBox = new HBox();
            Label recName = new Label(recipe.getName());
            TextArea shortDesc = new TextArea(messageList.get(i).getContent());

            //Getting tags

            ArrayList<RecipeEntity> allrecipes = DataBaseConnections.getAllRecipes();

            

            
            for (int x = 0; x < messageList.size(); x++) {
                //System.out.println(allrecipes.get(x).getCategory());
                    String cats = recipe.getCategory();
                    //System.out.println(cats);
                    alltags = cats.split(",");
                    nrOfTags = alltags.length;
            }

            for (int j = 0; j < nrOfTags; j++) {
                tagName = alltags[j] + ",";
                Label tag = new Label(tagName);
                tBox.getChildren().add(tag);
            }
            
           // ImageView gStar = new ImageView("./cookbook/resources/img/GStar.png");
           // ImageView tStar = new ImageView("./cookbook/resources/img/TStar.png");

            CheckBox favCheckBox = new CheckBox();
            favCheckBox.setTranslateX(80);
            shortDesc.setPrefHeight(100);
            shortDesc.setPrefWidth(200);
            shortDesc.setTranslateX(300);
            //shortDesc.setVisible(false);
            shortDesc.setEditable(false);

            shortDesc.setWrapText(true);
            shortDesc.setMaxSize(180, 80);
            shortDesc.setTranslateX(400);
            recImage.setFitWidth(160);
            recImage.setFitHeight(100);
            recName.setTranslateX(170);
            favCheckBox.setTranslateX(330);
            favCheckBox.setTranslateY(34);
            favCheckBox.setOpacity(0.0);
            tBox.setTranslateX(170);
            tBox.setTranslateY(40);
            recName.setStyle("-fx-font-size: 20px");



            Pane copyPane = new Pane();
            copyPane.setMaxSize(500, 120);
            copyPane.setMinSize(500, 120);
            copyPane.getChildren().addAll(recImage, recName, tBox, favCheckBox, shortDesc);
            container.getChildren().add(copyPane);
            if (!recipePanes.contains(copyPane)) {
                recipePanes.add(copyPane);
            } 
            
    }

    

}
}
