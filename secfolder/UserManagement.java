package secfolder;

import java.util.ArrayList;

import cookbook.DataBaseConnections;
import cookbook.Login;
import cookbook.LoginMenu;
import cookbook.UserEntity;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;

public class UserManagement extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Login.firstLaunch = false;
        primaryStage.setTitle("User Management");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // UI elements
        TextField usernameTextField = new TextField();
        TextField displayNameTextField = new TextField();
        TextField passwordTextField = new TextField();
        Button createUserButton = new Button("Create User");
        Button modifyUserButton = new Button("Modify User");
        Button deleteUserButton = new Button("Delete User");
        Button goBack = new Button("Back");

        ListView<UserEntity> userListView = new ListView<>();
        userListView.setPrefHeight(200);
        ArrayList<UserEntity> allCurentUsers = DataBaseConnections.getAndCreateAllUsers();
        userListView.getItems().addAll(allCurentUsers);

        // Set custom cell factory for user ListView
        userListView.setCellFactory(new Callback<ListView<UserEntity>, ListCell<UserEntity>>() {
            @Override
            public ListCell<UserEntity> call(ListView<UserEntity> param) {
                return new ListCell<UserEntity>() {
                    @Override
                    protected void updateItem(UserEntity item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            setText(item.getUsername());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
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

        userListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                usernameTextField.setText(newSelection.getUsername());
                //displayNameTextField.setText(newSelection.getDisplayName());
                passwordTextField.setText(newSelection.getPassword());
            }
        });

        // Create user action
        createUserButton.setOnAction(event -> {
            String username = usernameTextField.getText();
            String displayName = displayNameTextField.getText();
            String password = passwordTextField.getText();

            if (!username.isEmpty() && !displayName.isEmpty() && !password.isEmpty()) {
                UserEntity newUser = admin.createUser(username, password);
                userListView.getItems().add(newUser);
                DataBaseConnections.registration(username, password);
                int id = DataBaseConnections.getIDFromUserName(username);
                DataBaseConnections.addWeeklyMealPlan(id, " ,  ,  ,  ,  ,  ,  , ");
            }
        });

        // Modify user action
        modifyUserButton.setOnAction(event -> {
            UserEntity selectedUser = userListView.getSelectionModel().getSelectedItem();
            String newUsername = usernameTextField.getText();
            String newDisplayName = displayNameTextField.getText();
            String newPassword = passwordTextField.getText();

            if (selectedUser != null && !newUsername.isEmpty() && !newDisplayName.isEmpty() && !newPassword.isEmpty()) {
             //   admin.modifyUser(selectedUser, newUsername, newDisplayName, newPassword);
            }
        });

        // Delete user action
        deleteUserButton.setOnAction(event -> {
            UserEntity selectedUser = userListView.getSelectionModel().getSelectedItem();
            int selectedId = DataBaseConnections.getIDFromUserName(usernameTextField.getText());
            if (selectedUser != null) {
                //admin.deleteUser(selectedUser);
                allCurentUsers.remove(selectedUser);
                userListView.getItems().remove(selectedUser);
                DataBaseConnections.deleteUser(selectedId);
            }
        });

        // Binding button states
        createUserButton.disableProperty().bind(
                Bindings.isEmpty(usernameTextField.textProperty())
                        .or(Bindings.isEmpty(displayNameTextField.textProperty()))
                        .or(Bindings.isEmpty(passwordTextField.textProperty())));
        modifyUserButton.disableProperty().bind(userListView.getSelectionModel().selectedItemProperty().isNull());
        deleteUserButton.disableProperty().bind(userListView.getSelectionModel().selectedItemProperty().isNull());
        // Layout setup
        gridPane.add(new Label("Username:"), 0, 0);
        gridPane.add(usernameTextField, 1, 0);
        gridPane.add(new Label("Display Name:"), 0, 1);
        gridPane.add(displayNameTextField, 1, 1);
        gridPane.add(new Label("Password:"), 0, 2);
        gridPane.add(passwordTextField, 1, 2);
        gridPane.add(createUserButton, 0, 3);
        gridPane.add(modifyUserButton, 1, 3);
        gridPane.add(deleteUserButton, 2, 3);
        gridPane.add(userListView, 0, 4, 3, 1);
        gridPane.add(goBack, 2, 0);

        primaryStage.setScene(new Scene(gridPane, 800, 600));
        primaryStage.show();
    }
}
