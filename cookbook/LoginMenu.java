package cookbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import javax.print.FlavorException;
import javax.swing.Action;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import secfolder.RecipeManagementView;
import secfolder.UserManagement;

public class LoginMenu extends Application {

    String path = System.getProperty("user.dir");
    private final String [] possibleBackgroundThemes = {"gray","black","blue","white","orange"};
    private final String [] possibleButtonThemes = {"black", "gray", "lightblue", "black", "red"};
    String selectedBackgroundTheme;
    String selectedButtonTheme;
    public static UserEntity user = new UserEntity("sweden_23");
    String bigString = "";
    String currentPlan;
    
    public static String buttonColor = "blue";
    public static String backgroundColor = "white";
    UserEntity loggedInUser = new UserEntity("sweden_23");
    Label themeLabel;
    ChoiceBox setThemeCB = new ChoiceBox<String>();
    Stage primaryStage;
    public static ListView weeklyView = new ListView<>();
    //UserEntity.getTheme put into choisebox line ~155
    ArrayList<RecipeEntity> lst = new ArrayList<>();


    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.getIcons().add(new Image(path + "/project-cookbook-perl/cookbook/resources/img/cookbook_logo2.png"));
        for (int i = 0; i < possibleBackgroundThemes.length; i++) {
            if(DataBaseConnections.getTheme(Login.user.getUsername()).equals(possibleBackgroundThemes[i])){
                selectedButtonTheme = possibleButtonThemes[i];
                buttonColor = selectedButtonTheme;
            }
        }

        this.setThemeCB = new ChoiceBox<String>();
        this.primaryStage = primaryStage;
        // Create the menu bar
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(actionEvent -> primaryStage.close());
        menuFile.getItems().addAll(exit);
        menuBar.getMenus().addAll(menuFile);

        int userid = DataBaseConnections.getIDFromUserName(user.getUsername());
        //Getting weeklyPlan
        currentPlan = DataBaseConnections.getWeeklyPlan(userid);
        System.out.println("UserID " + userid + " Current Plan: " + currentPlan);
        String[] plans = currentPlan.split(",");


        // Create the header
        Label headerLabel = new Label("Welcome to our Cookbook!");
        headerLabel.setStyle("-fx-font-size: 20pt; -fx-padding: 20px 0 0 20px; -fx-text-fill: black; -fx-font-weight: bold;");

        //create weekly diner list
        weeklyView.setMinHeight(455);
        String[] weekdays = {"Mo: ", "Tu: ", "We: ", "Th: ", "Fr: ", "Sa: ", "Su: "};
        LocalDate date = LocalDate.now(); 
        WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
        AtomicInteger weekNumber = new AtomicInteger(date.get(weekFields.weekOfWeekBasedYear()));
        //weeklyView.setStyle("-fx-background-color: " + buttonColor + "; -fx-text-fill: white;");
        String mealPlan = "";

        if(Login.firstLaunch == true){
            weeklyView.getItems().add("week: " + weekNumber);
        for (int i = 0; i < weekdays.length; i++) {
            Label t = new Label(weekdays[i]);
            weeklyView.getItems().add(t);
            TextField textField = new TextField();
            weeklyView.getItems().add(new TextField());
        }
        weeklyView.setMaxHeight(280);
        Button nextButton = new Button("->");
        Button prevButton = new Button("<-");
        //Button submitButton = new Button("Submit");
        HBox h = new HBox(prevButton, nextButton);
            weeklyView.getItems().add(h);

        nextButton.setOnAction(event ->{
            weekNumber.incrementAndGet();
            weeklyView.getItems().clear();
            weeklyView.getItems().add("week: " + (weekNumber.intValue() % 53));
            for (int i = 0; i < weekdays.length; i++) {
                Label t = new Label(weekdays[i]);
                weeklyView.getItems().add(t);
                weeklyView.getItems().add(new TextField());
            }
            HBox hb = new HBox(prevButton, nextButton);
            weeklyView.getItems().add(hb);
        });


        prevButton.setOnAction(event ->{
            weekNumber.decrementAndGet();
            weeklyView.getItems().clear();
            weeklyView.getItems().add("week: " +weekNumber.intValue()%53);
            for (int i = 0; i < weekdays.length; i++) {
                Label t = new Label(weekdays[i]);
                weeklyView.getItems().add(t);
                weeklyView.getItems().add(new TextField());
            }
            HBox hb = new HBox(prevButton, nextButton);
            weeklyView.getItems().add(hb);

        });

        /* 
        submitButton.setOnAction(event ->{
            System.out.println("submit");
            bigString = "";
            for (int i = 0; i < weeklyView.getItems().size(); i++) {
                if (weeklyView.getItems().get(i) instanceof TextField) {
                    TextField select = (TextField) weeklyView.getItems().get(i);
                    System.out.println(select.getText());
                    if (bigString.length() > 0) {
                    bigString = bigString + ", " + select.getText();
                    } else {
                        bigString = bigString + ", ";  
                    }
                }
            }
    
            System.out.println(bigString);
           // int userid = DataBaseConnections.getIDFromUserName(user.getUsername());
            DataBaseConnections.updateWeeklyMealPlan(userid, bigString);
        });
        */

        }
        int count = 0;
        for (int i = 0; i < weeklyView.getItems().size(); i++) {
            if (weeklyView.getItems().get(i) instanceof TextField) {
                weeklyView.getItems().remove(i);
                String s = plans[count];
                if (s.startsWith(" ") || s.startsWith("  ")) {
                    s = s.strip();
                }
                weeklyView.getItems().add(i, new TextField(s));
                if (count != 6) {
                count += 1;
                }
                
            }
        }
        
        
        weeklyView.setOnMouseClicked (event -> {
            if(event.getClickCount() == 2){
                TextField selectedRecName = null;
                if(weeklyView.getSelectionModel().getSelectedItem() instanceof TextField){
                    selectedRecName = (TextField) weeklyView.getSelectionModel().getSelectedItem();
                    String recName = selectedRecName.getText();
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("RecipePage.fxml"));
            try {
                Parent root = loader.load();
                Controller controller = loader.getController();
                
                int nrOfTags = 3;
                String tagName;
                String[] alltags = null;
                lst = DataBaseConnections.getRecipeByName(selectedRecName.getText());
                TextArea shortDesc = new TextArea(lst.get(0).getDescription());
                ArrayList<RecipeEntity> allrecipes = DataBaseConnections.getAllRecipes();
                for (int x = 0; x < allrecipes.size(); x++) {
                    //System.out.println(allrecipes.get(x).getCategory());
                    if ((allrecipes.get(x).getName()).equals(selectedRecName.getText())) {
                        String cats = allrecipes.get(x).getCategory();
                        //System.out.println(cats);
                        alltags = cats.split(",");
                        nrOfTags = alltags.length;
                    }
                }

                String[] tags = alltags;
                String recIngredients = DataBaseConnections.getAllIngrOfRecipe(DataBaseConnections.getRecipeIDFromName(selectedRecName.getText()));
                controller.setRecName(selectedRecName.getText());
                controller.setShortDesc(DataBaseConnections.getRecipeShortDescription(selectedRecName.getText()));
                controller.setDescription(shortDesc.getText(), recIngredients, 2);
                for (int a = 0; a < tags.length; a++) {
                    System.out.println(tags[a]);
                }
                controller.setTags(tags);
                
                
                loader.setController(controller);
                // Show the RecipePage stage
                Stage recipeStage = new Stage();
                Scene scene = new Scene(root);
                recipeStage.setTitle("Recipe instance");
                recipeStage.setScene(scene);
                recipeStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
          }
        
        });

        

        Button searchButton = new Button("Browse & Search");
        searchButton.setStyle("-fx-background-color: " + buttonColor + "; -fx-text-fill: white;");
        //searchButton.setPrefWidth(80);
        HBox searchBox = new HBox(weeklyView);
        searchBox.setAlignment(Pos.BOTTOM_CENTER);
        searchBox.setSpacing(10);

        searchButton.setOnAction(event -> {
            try {
                BNS bns = new BNS();
                bns.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Create the help button
        Button helpButton = new Button("Help");
        helpButton.setStyle("-fx-background-color: " + buttonColor + "; -fx-text-fill: white;");
        helpButton.setPrefWidth(80);
        helpButton.setOnAction(event -> {
            try {
                HelpButton help = new HelpButton();
                help.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button adminButton = new Button("Admin");
        adminButton.setStyle("-fx-background-color: " + buttonColor + "; -fx-text-fill: white;");
        adminButton.setPrefWidth(80);
        adminButton.setOnAction(event -> {
            try {
                UserManagement user = new UserManagement();
                user.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        adminButton.setVisible(false);

        if (DataBaseConnections.isAdmin(user.getUsername())) {
            System.out.println("admin");
            adminButton.setVisible(true);
        } else {
            System.out.println("user");
        }

        // Create the create recipe button
        Button createRecipeButton = new Button("Create Recipe");
        createRecipeButton.setStyle("-fx-background-color: " + buttonColor + "; -fx-text-fill: white;");
        //createRecipeButton.setTranslateX(50);
        createRecipeButton.setOnAction(event -> {
            try {
                RecipeManagementView Shp = new RecipeManagementView();
                Shp.setLoggedInUser(loggedInUser);
                Shp.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //Create inbox
        ImageView inboxView = new ImageView(path + "/project-cookbook-perl/cookbook/resources/img/inbox.png");
        inboxView.setPreserveRatio(true);
        inboxView.setFitWidth(50);
        inboxView.setFitHeight(50);
        Button inboxButton = new Button();
        inboxButton.setTranslateX(48);
        inboxButton.setTranslateY(0);
        inboxButton.setMinWidth(50);
        inboxButton.setMinHeight(45);
        inboxButton.setOpacity(0);
        ImageView notificationView = new ImageView(path + "/project-cookbook-perl/cookbook/resources/img/RedDot.png");
        notificationView.setFitHeight(70);
        notificationView.setFitWidth(70);
        notificationView.setTranslateX(125);
        notificationView.setTranslateY(-18);
        if(!DataBaseConnections.userHasNotification(Login.user.getUsername())){
            notificationView.setVisible(false);
        }

        if(DataBaseConnections.userHasNotification(Login.user.getUsername())){
            notificationView.setVisible(true);
        }
        inboxButton.setOnAction(event ->{
            try {
                notificationView.setVisible(false);
                DataBaseConnections.userSetNotification(Login.user.getUsername(), "n");
                MessagePage msg = new MessagePage();
                msg.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        // Create the shopping list button
        Button shoplistButton = new Button("Shopping List");
        shoplistButton.setStyle("-fx-background-color: " + buttonColor + "; -fx-text-fill: white;");
        shoplistButton.setPrefWidth(120);
        shoplistButton.setOnAction(event -> {
            try {
                ShoppingList shpLst = new ShoppingList();
                shpLst.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Create the log out button
        Button logoutButton = new Button("Log Out");
        logoutButton.setStyle("-fx-background-color: " + buttonColor + "; -fx-text-fill: white;");
        logoutButton.setPrefWidth(80);
        logoutButton.setOnAction(event -> {
            try {
                Login login = new Login();
                login.start(new Stage());
                primaryStage.close();
                Login.firstLaunch = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //LoginLabel
        Label loginLabel;
        if (user != null) {
        loginLabel = new Label("Logged In as: " + user.getUsername());
        } else {
        loginLabel = new Label("  Logged In as: Dev");
        }
        loginLabel.setTranslateX(10);
        

        //Create the set theme ChoiseBox & lable
        setThemeCB.getItems().addAll(possibleBackgroundThemes);
        setThemeCB.setOnAction(event -> getTheme(event));
        themeLabel = new Label("Theme");
        themeLabel.setTranslateY(-42);
        themeLabel.setTranslateX(-12);
        setThemeCB.setMinWidth(80);
        setThemeCB.setStyle("-fx-background-color: " + buttonColor + "; -fx-text-fill: white;");


        // Create the image
        String path = System.getProperty("user.dir");
        System.out.println(path);
        Image logoImage = new Image(new FileInputStream(path +"/project-cookbook-perl/cookbook/resources/img/cookbook_logo2.png"));
        ImageView imageView = new ImageView(logoImage);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);

        // Create the left VBox
        VBox leftVBox = new VBox(imageView, searchButton, shoplistButton, logoutButton, setThemeCB, themeLabel, inboxView, inboxButton);
        leftVBox.setAlignment(Pos.CENTER);
        leftVBox.setSpacing(20);
        leftVBox.setPadding(new Insets(20));

        // Create the bottom HBox
        HBox bottomHBox = new HBox(createRecipeButton, loginLabel);
        bottomHBox.setAlignment(Pos.CENTER);
        bottomHBox.setPadding(new Insets(20));

        HBox helpAdmin = new HBox(adminButton, helpButton, notificationView, inboxButton, inboxView);

        // Create the main layout
        BorderPane root = new BorderPane();
        root.setTop(headerLabel);
        BorderPane.setAlignment(headerLabel, Pos.CENTER);
        root.setCenter(searchBox);
        BorderPane.setMargin(searchBox, new Insets(50, 0, 0, 0));
        root.setRight(helpAdmin);
        BorderPane.setAlignment(helpAdmin, Pos.TOP_RIGHT);
        root.setLeft(leftVBox);
        BorderPane.setMargin(leftVBox, new Insets(0, 20, 0, 20));
        root.setBottom(bottomHBox);
        BorderPane.setAlignment(bottomHBox, Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.web(backgroundColor), CornerRadii.EMPTY, Insets.EMPTY)));

        // Create the scene
        Scene scene = new Scene(root, 800, 630);

        // Set the stage
        primaryStage.setTitle("Cookbook");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void getTheme(Event event) {
        Login.firstLaunch = false;
        themeLabel.setText("");
        int i = setThemeCB.getSelectionModel().getSelectedIndex();
        selectedBackgroundTheme = possibleBackgroundThemes[i];
        selectedButtonTheme = possibleButtonThemes[i];
        DataBaseConnections.addTheme(Login.user.getUsername(), selectedBackgroundTheme);
        buttonColor = selectedButtonTheme;
        backgroundColor = selectedBackgroundTheme;
        primaryStage.close();
        try {
            start(new Stage());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}



 