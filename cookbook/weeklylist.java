package cookbook;

import java.sql.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.File;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class weeklylist extends Application implements EventHandler<ActionEvent>{

    private Stage primaryStage;
    private Scene startScene;
    private Scene listScene;
    private Button customButton;
    private Button displayButton;
    private Button menuButton;
    private Button backButton;
    private Button submiButton;
    private ArrayList<String> foodlist = new ArrayList<String>();


    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        
        // Welcome Screen
        Label welcomelabel = new Label("Welcome to The Dinner List Manager");
        welcomelabel.setTextFill(Color.BLACK);
        welcomelabel.setStyle("-fx-background-color: 16px;");
        welcomelabel.setStyle("-fx-font-family: Arial; -fx-font-size: 20px; -fx-font-weight: bold;");
        welcomelabel.setAlignment(Pos.CENTER);
        welcomelabel.setTranslateY(-40);

        // Buttons
        customButton = new Button("Customize");
        customButton.setStyle("-fx-background-color:" + LoginMenu.buttonColor + "; -fx-text-fill: white;");
        customButton.setOnAction(this);
        displayButton = new Button("Display");
        displayButton.setStyle("-fx-background-color:" + LoginMenu.buttonColor + "; -fx-text-fill: white;");
        displayButton.setTranslateX(-70);
        displayButton.setOnAction(this);
        menuButton = new Button("Go Back");
        menuButton.setStyle("-fx-background-color:" + LoginMenu.buttonColor + "; -fx-text-fill: white;");
        menuButton.setTranslateX(70);
        menuButton.setOnAction(this);


        //Create layout
        StackPane group1 = new StackPane();
        group1.getChildren().addAll(welcomelabel, customButton, displayButton, menuButton);
        group1.setStyle("-fx-background-color:" + LoginMenu.backgroundColor + ";");

        startScene = new Scene(group1, 600, 600);
        startScene.setFill(Color.web("#F6DFCC"));

        primaryStage.setTitle("Weekly List Manager");
        primaryStage.setScene(startScene);
        primaryStage.show();

        //list layout
        VBox group2 = new VBox();
        group2.setPadding(new Insets(100, 30, 100, 30));

        //Buttons for listScene
        backButton = new Button("Go Back");
        backButton.setStyle("-fx-background-color:" + LoginMenu.buttonColor + "; -fx-text-fill: white;");
        backButton.setTranslateY(-350);
        backButton.setOnAction(this);
        submiButton = new Button("Submit");
        submiButton.setStyle("-fx-background-color:" + LoginMenu.buttonColor + "; -fx-text-fill: white;");
        
        //DataBase db = new DataBase();
        String pass = "select Rname from recipe";
        Statement st;
        try {

            //st = db.connect().createStatement();
            ResultSet rs = st.executeQuery(pass);
            String result;
            while (rs.next()) {
            System.out.println(rs.getString("Rname"));
            foodlist.add(rs.getString("Rname"));
            
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        //Create fields for list
        String[] weekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < 7; i++) {
            Text FieldLabel = new Text("Dinner for " + weekdays[i]);
            ComboBox<String> box = new ComboBox<>();
            box.setItems(FXCollections.observableArrayList(foodlist));
            group2.getChildren().addAll(FieldLabel, box);
        }

        

        //Create listScene and layout
        group2.getChildren().addAll(backButton, submiButton);
        group2.setStyle("-fx-background-color:" + LoginMenu.backgroundColor + ";");
        listScene = new Scene(group2, 600, 600);
        
        
    }

    @Override
    /*public void handle(ActionEvent event) {
        if(event.getSource()==customButton) {
            System.out.println("Customize Button pressed");
            primaryStage.setTitle("Customize");
            primaryStage.setScene(listScene);
            DataBase db = new DataBase();
            String pass = "select Rname from recipe";
            Statement st;
            try {

                st = db.connect().createStatement();
                ResultSet rs = st.executeQuery(pass);
                String result;
                while (rs.next()) {
                System.out.println(rs.getString("Rname"));
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if(event.getSource()==displayButton) {
            System.out.println("Display Button pressed");

        }
        if(event.getSource()==backButton) {
            primaryStage.setScene(startScene);
            System.out.println("Back Button pressed");
        }
        if(event.getSource()==menuButton) {
            try {
            LoginMenu logout = new LoginMenu();
            logout.start(new Stage() );
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }
*/
    public static void main(String[] args) {
        launch(args);
    }

}