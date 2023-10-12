package cookbook;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;

public class MainController implements Initializable{


    @FXML
    private Button addBtn;

    @FXML
    private Button backBtn;

    @FXML
    private ListView<String> myListView;

    @FXML
    private AnchorPane plane;

    @FXML
    private Button removeBtn;

    @FXML
    private TextField tfCount;

    @FXML
    private TextField tfShoppingList;

    private ArrayList<String> items = new ArrayList<>();

    String currentItem; 
    


    @FXML
    void btnOKclick(ActionEvent event) {
        Stage mainWindow = (Stage) tfShoppingList.getScene().getWindow();
        String shopping = tfShoppingList.getText();
        String amount = tfCount.getText();
        String itemString = shopping + ":" + amount;
        items.add(itemString);
        myListView.getItems().addAll(itemString);
    }

    @FXML
    void delete(ActionEvent event) {
        myListView.getItems().removeAll(myListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    void goToMenu(ActionEvent event) throws IOException {
        LoginMenu app = new LoginMenu();
        app.start((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Login.firstLaunch = false;
        backBtn.setStyle("-fx-background-color:" + LoginMenu.buttonColor + "; -fx-text-fill: white;");
        removeBtn.setStyle("-fx-background-color:" + LoginMenu.buttonColor + "; -fx-text-fill: white;");
        addBtn.setStyle("-fx-background-color:" + LoginMenu.buttonColor + "; -fx-text-fill: white;");
        plane.setStyle("-fx-background-color:" + LoginMenu.backgroundColor + ";");

        //ArrayList<RecipeEntity> allRecipeEntities = new ArrayList<RecipeEntity>();
        //RecipeEntity recipe = DataBaseConnections.getRecipeByName("Eggs benedict").get(0);
        //allRecipeEntities.add(recipe);
        //RecipeEntity recipe1 = DataBaseConnections.getRecipeByName("Cinnamon and Nuts Cake").get(0);
        //allRecipeEntities.add(recipe1);

        int userId = DataBaseConnections.getIDFromUserName(LoginMenu.user.getUsername());
        String[] allRecipeEntities = DataBaseConnections.getWeeklyPlan(userId).split(", ");  
        ObservableList<String> listViewItems = myListView.getItems();

        for (int x = 0; x < allRecipeEntities.length; x++) {

            if ((!allRecipeEntities[x].equals(" ") && !allRecipeEntities[x].equals("") ) && allRecipeEntities.length > 0) {
                if (allRecipeEntities[x].startsWith(" ") || allRecipeEntities[x].startsWith("  ")) {
                    allRecipeEntities[x] = allRecipeEntities[x].strip();
                }
            RecipeEntity recipe = DataBaseConnections.getRecipeByName(allRecipeEntities[x]).get(0);
            String recipeString = recipe.getIngredientList();
            String[] recipeList = recipeString.split(", ");
            String name = "";
            String unit = "";
            int amount = 1;
            int secamount = 0;
            int tot = 0;
            int remover = 0;
            //char[] digitCheckList = recipeList[x].toCharArray();

            for (int i = 0; i < recipeList.length; i++) {
                char[] digitCheckList = recipeList[i].toCharArray();
                String temp = "";
                String ingredient = "";
                //System.out.println(myListView.getItems());
                for (int j = 0; j < digitCheckList.length; j++) {
                    if (Character.isDigit(digitCheckList[j])) {
                        temp += String.valueOf(digitCheckList[j]);
                    } else {
                        ingredient += String.valueOf(digitCheckList[j]);
                    }
                }
                ingredient = ingredient.replace(" of ", ",");
                String[] actual = ingredient.split(",");
                unit = "";
                if (actual.length >= 2) {
                    unit = actual[0];
                    name = actual[1];
                    //ingredient = temp + unit + name;
                } else {
                    name = actual[0];
                }
                if (temp.equals("")) {
                    temp = "1";
                }
                amount = Integer.parseInt(temp);
                //System.out.println(amount);
                //System.out.println(ingredient);
                //myListView.getItems().contains("Egg");

                
                boolean add = false;
                boolean replace = false;
                if (actual.length >= 1) {
                    for (String item : listViewItems) {
                        System.out.println(item);
                        if (item.contains(name)) {
                            System.out.println("contains a " + name);
                            String[] par = item.split(" ");
                            char[] newCharList = par[0].toCharArray();
                            String newTemp = "";
                            for (int a = 0; a < newCharList.length; a++) {
                                if (Character.isDigit(newCharList[a])) {
                                    newTemp += String.valueOf(newCharList[a]);
                                }
                            }
                            
                            secamount = Integer.parseInt(newTemp);
                            tot = amount + secamount;
                            add = false;
                            replace = true;
                            remover = myListView.getItems().indexOf(item);
                            break;
                        } else {
                            add = true;
                        }
                    }

                    /*
                     * Issue: there is a space between amount and unit,
                     * ëxcept for when replacing.
                     */

                    if (add == true) {
                    myListView.getItems().add(amount + " " + unit + " " + name);
                    } else if (replace == true) {
                        String replaceString = myListView.getItems().get(remover);
                        String[] parts = replaceString.split(" ", 2);
                        char[] newCharList = parts[0].toCharArray();
                        String newTemp = "";
                            for (int a = 0; a < newCharList.length; a++) {
                                if (Character.isDigit(newCharList[a])) {
                                    newTemp += String.valueOf(newCharList[a]);
                                }
                            }
                        //String part = parts[0].replace(unit, "");
                        int currentAmount = Integer.parseInt(newTemp);
                        int newAmount = currentAmount + amount;
                        String newString = newAmount + unit + " " + name;
                        myListView.getItems().set(remover, newString);
                    } else {
                        myListView.getItems().add(amount + " " + unit + " " + name);
                    }
                }
            }
            }
            System.out.println(myListView.getItems());
        }

    }



}
