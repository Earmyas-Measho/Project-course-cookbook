package cookbook;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import javafx.util.Duration;
import secfolder.Comment;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;

public class Controller implements Initializable {
    
    @FXML
    private Label portionLabel;
    @FXML
    private ChoiceBox<String> nrOfEaters;
    @FXML
    private HBox tagBox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox commentPane;
    @FXML
    private TextArea makeCommentPane; 
    @FXML
    private ImageView TraStar;
    @FXML
    private ImageView Gstar;
    @FXML
    private Label recipeName;
    @FXML
    private TextArea description;
    @FXML
    private TextArea shortDescription;
    @FXML
    private Label sentMessage;
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private Button backButton;
    @FXML
    private Button commentButton;
    @FXML
    private Label weeklyLabel;
    @FXML
    private ChoiceBox<String> weeklyDropDown;
    private int nrOfTags = 3; //From DB
    private String[] tags;
    private String tagName = "tag1  "; //FromDB
    private int nrOfComments = 3; //From DB
    private String currentComment = "blablabla"; //From DB
    private final String [] nrOfPeopleEating = {"2","4","6","8"};
    private final String [] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private String userShowName = "sweden_23"; //From DB
    private Button removeBtn;
    int userId = DataBaseConnections.getIDFromUserName(LoginMenu.user.getUsername());
    private String bigString = DataBaseConnections.getWeeklyPlan(userId);
    private String[] currentPlan = bigString.split(", ");
    ArrayList<RecipeEntity> userFav = DataBaseConnections.getFavoriteRecipes(DataBaseConnections.getIDFromUserName(Login.user.getUsername()));
    public boolean thisRecIsAFav = false;
    RecipeEntity thisRecipe;
    private String recipeNameText;

    public void setRecName(String s) {
        recipeName.setText(s);
        this.recipeNameText = s;
        System.out.println(recipeName);
    }

    public void setFav(String recName){
        
        TraStar.setVisible(false);
        Gstar.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        System.out.println("Current recipe: " + recipeNameText);
        ArrayList<Comment> comments = DataBaseConnections.getCommentsFromRecipe(recipeName.getText());
        nrOfComments = comments.size();

        //thisRecipe.setDescription(DataBaseConnections.getRecipeDescription(tagName));
        Login.firstLaunch = false;
        nrOfEaters.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");
        backButton.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");
        commentButton.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");
        weeklyDropDown.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");

        //rootAnchorPane.setBackground(new Background(new BackgroundFill(Color.web(LoginMenu.backgroundColor), CornerRadii.EMPTY, Insets.EMPTY)));
        rootAnchorPane.setStyle("-fx-background-color:"+ LoginMenu.backgroundColor +";");

        sentMessage.setVisible(false);
        makeCommentPane.setWrapText(true);

        int eaterSize = nrOfEaters.getItems().size();
        if (!(eaterSize >= 4)) {
        nrOfEaters.getItems().addAll(nrOfPeopleEating);
        nrOfEaters.setOnAction(this::getPortionNr);
        }
        
        int weekSize = weeklyDropDown.getItems().size();
        if (!(weekSize >= 7)) {
        weeklyDropDown.getItems().addAll(weekDays);
        weeklyDropDown.setOnAction(this::getSelectedDay);
        }

        for (int i = 0; i < nrOfComments; i++) {
            TextArea commentTextField = new TextArea(comments.get(i).getAuthor() + ": \n" + comments.get(i).getContent());
            commentTextField.setWrapText(true);
            int row = (commentTextField.getPrefRowCount()) * 10;
            commentTextField.setMinHeight(row);
            //Button removeBtn = null;
            if (Login.user.getUsername().equals(comments.get(i).getAuthor())) {
                commentTextField.setEditable(true);
                final Button removeBtn = new Button("Remove");
                removeBtn.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");
                //removeBtn.setTranslateY(100);
                commentPane.getChildren().addAll(commentTextField, removeBtn);
                final int newI = i;
                removeBtn.setOnAction(removevent -> {
                    try {
                        commentPane.getChildren().removeAll(commentTextField, removeBtn);
                        DataBaseConnections.deleteComment(comments.get(newI));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } else {
                commentTextField.setEditable(false);
                commentPane.getChildren().addAll(commentTextField);
            }

        }

    }

    @FXML
    void goBack(ActionEvent event) throws FileNotFoundException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    public void getPortionNr(ActionEvent event){
        portionLabel.setText(" Portions");
        String recIngredients = DataBaseConnections.getAllIngrOfRecipe(DataBaseConnections.getRecipeIDFromName(recipeName.getText()));
        setDescription(DataBaseConnections.getRecipeDescription(recipeName.getText()) ,recIngredients, (nrOfEaters.getSelectionModel().getSelectedIndex()+1)*2);
    }

    public void getSelectedDay(ActionEvent event){
        weeklyLabel.setText("");
        int weekDayIndex = weeklyDropDown.getSelectionModel().getSelectedIndex();
        LoginMenu.weeklyView.getItems().remove(2*weekDayIndex+2);
        LoginMenu.weeklyView.getItems().add(2*weekDayIndex + 2, new TextField(recipeName.getText()));
        System.out.println("submit");
                if (LoginMenu.weeklyView.getItems().get(2*weekDayIndex+2) instanceof TextField) {
                    TextField select = (TextField) LoginMenu.weeklyView.getItems().get(2*weekDayIndex+2);
                    System.out.println(select.getText());
                    if (weekDayIndex == 0) {
                        currentPlan[weekDayIndex] = recipeName.getText() + ",";
                    } else if (weekDayIndex == 6) {
                        currentPlan[weekDayIndex] = " " + recipeName.getText() + ",  ";
                    } else {
                        currentPlan[weekDayIndex] = " " + recipeName.getText() + ",";
                    }
                }
            
            String totString = String.join(", ", currentPlan);
            /* 
            for(int i = 0; i < currentPlan.length; i++) {
                totString = totString + currentPlan[i];
                System.out.println(currentPlan[i]);
            }
            */
            
            System.out.println("totString: " + totString);
            totString = totString.replace(",,", ",");
            System.out.println("totString: " + totString);
            DataBaseConnections.updateWeeklyMealPlan(userId, totString);
    }

    public void commentButton(ActionEvent event){
        String userShowName = LoginMenu.user.getUsername();


        Comment commentInsert = new Comment(userShowName, recipeName.getText(), makeCommentPane.getText());
        DataBaseConnections.addComment(commentInsert);


        TextArea commentTextField = new TextArea(userShowName + ": \n" + makeCommentPane.getText());
        commentTextField.setWrapText(true);
        int row = (commentTextField.getPrefRowCount()) * 15;
        commentTextField.setMinHeight(row);
        if (userShowName.equals(LoginMenu.user.getUsername())) {
            commentTextField.setEditable(true);
            final Button removeBtn = new Button("Remove");
            removeBtn.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");
            //removeBtn.setTranslateY(100);
            commentPane.getChildren().addAll(commentTextField, removeBtn);
            removeBtn.setOnAction(sevent -> {
                try {
                    commentPane.getChildren().removeAll(commentTextField, removeBtn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            commentTextField.setEditable(false);
            commentPane.getChildren().addAll(commentTextField);
        }
    }

    public void pressFavCkeckbox(ActionEvent event){
        //Add fav to user in DB
        if(TraStar.isVisible()){
            TraStar.setVisible(false);
            Gstar.setVisible(true);
            DataBaseConnections.addRecipeToFavorites(DataBaseConnections.getRecipeIDFromName(recipeName.getText()), DataBaseConnections.getIDFromUserName(Login.user.getUsername()));
            thisRecIsAFav = true;
            return;
        }
        //remove fav from db
        if(!TraStar.isVisible()){
            TraStar.setVisible(true);
            Gstar.setVisible(false);
            thisRecIsAFav = false;
            DataBaseConnections.deleteRecipeFromFavorites(DataBaseConnections.getRecipeIDFromName(recipeName.getText()), DataBaseConnections.getIDFromUserName(Login.user.getUsername()));

        }
        if(TraStar.isVisible() == true){
            thisRecIsAFav = false;
        }else {
            thisRecIsAFav = true;
        }
        
    }

    @FXML
    void share(ActionEvent event) {
        shareRecipe();
    }

    private void shareRecipe() {
        Stage shareStage = new Stage();
        shareStage.setTitle("Share");

        VBox shareRoot = new VBox();
        shareRoot.setSpacing(10);
        shareRoot.setPadding(new Insets(10, 10, 10, 10));

        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10, 10, 10, 10));

        ImageView recImage = new ImageView("./cookbook/resources/img/Good_Food.jpg");
        recImage.setFitHeight(200);
        recImage.setFitWidth(200);
        Rectangle clip = new Rectangle(recImage.getFitWidth(), recImage.getFitHeight());
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        recImage.setClip(clip);

        HBox topBox = new HBox();
        topBox.setSpacing(10);
        topBox.setPadding(new Insets(10, 10, 10, 10));

        Label messageRec = new Label();
        messageRec.setText(recipeName.getText());
        messageRec.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");
        TextField messanger = new TextField();
        messanger.setPromptText("Enter Username");

        topBox.getChildren().addAll(messageRec, messanger);

        TextArea shortDesc = new TextArea(description.getText());
            shortDesc.setPrefHeight(100);
            shortDesc.setPrefWidth(200);
            shortDesc.setEditable(false);
            shortDesc.setWrapText(true);

        hbox.getChildren().addAll(recImage, shortDesc);

        TextArea messageArea = new TextArea();
        messageArea.setWrapText(true);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), sentMessage);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        Button sendButton = new Button("Send Recipe");
        sendButton.setStyle("-fx-background-color: " + LoginMenu.buttonColor + "; -fx-text-fill: white;");
        sendButton.setTranslateX(150);
        sendButton.setOnAction(event -> {
            try {
                //need notification in db
                DataBaseConnections.userSetNotification(messanger.getText(), "y");
                String user = LoginMenu.user.getUsername();
                int userId = DataBaseConnections.getIDFromUserName(user);
                //System.out.println(messanger.getText());
                int recieverId = DataBaseConnections.getIDFromUserName(messanger.getText());
                String content = messageArea.getText();
                int recipeId = DataBaseConnections.getRecipeIDFromName(recipeName.getText());
                Message mess = new Message(userId, content, recieverId, recipeId);
                DataBaseConnections.addMessage(mess.getSenderId(), mess.getRecipeId(), mess.getContent(), mess.getReceiverId());
                sentMessage.setVisible(true);
                fadeOut.play();
                shareStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        shareRoot.getChildren().addAll(topBox, hbox, messageArea, sendButton);
        shareRoot.setStyle("-fx-background-color:" + LoginMenu.backgroundColor + ";");
        Scene shareScene = new Scene(shareRoot, 400, 400);
        shareStage.setScene(shareScene);
        shareStage.show();
    }

    public void setShortDesc(String shortDesc){
        shortDescription.setText(shortDesc);
    }

    public void setDescription(String desc, String ingr, int portions) {
        char c [] = ingr.toCharArray();
        String amount = "";
        int oneIngrAmount;
        int i = -1;
        String allIngr1[] = ingr.split(",");
        String allIngr2[] = ingr.split(",");
        String s = "";

        for (int j = 0; j < allIngr1.length; j++) {
            for (i = i+1; i < c.length; i++) {
                if(String.valueOf(c[i]).equals(",")){
                    break;
                }
                if(Character.isDigit(c[i])){
                    amount += String.valueOf(c[i]);
                }
            }   
            if(!amount.equals("")){
                oneIngrAmount = Integer.parseInt(amount);
                allIngr1[j] = allIngr2[j].replace(amount, Integer.toString(oneIngrAmount*portions));
                amount = "";
            }
            
        }
        for (int l = 0; l < allIngr1.length; l++) {
            s+= allIngr1[l];
        }
        description.setText(desc + "\n\n" + s);
    }

    public void setTags(String[] s) {
        if (s != null && s.length > 0) {
        tags = s;
        } else {
        
        }

        for (int i = 0; i < tags.length; i++) {
            //System.out.print(tags[i]);
            Label tag = new Label(tags[i] + ",");
            tagBox.getChildren().add(tag);
        }
    }
    
}
