import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;

public class UserManagement extends VBox {

  private Scene scene;
  private admin admin;

  public UserManagement() {
  }

  public UserManagement(admin admin) {
    this.admin = admin;
  }

  public Scene createScene() {
    if (scene == null) {
      StackPane stackPane = new StackPane();
      stackPane.setPadding(new Insets(10));
      stackPane.setStyle("-fx-background-color: #5865F2;");

      GridPane gridPane = new GridPane();
      gridPane.setHgap(10);
      gridPane.setVgap(10);
      gridPane.setPadding(new Insets(10));

      // UI elements
      TextField usernameTextField = new TextField();
      TextField displayNameTextField = new TextField();
      TextField passwordTextField = new TextField();
      Button createUserButton = new Button("Create User");
      Button modifyUserButton = new Button("Modify User");
      Button deleteUserButton = new Button("Delete User");

      // Styling buttons
      createUserButton.setStyle("-fx-background-color: #98fb98; -fx-border-color: #006400;");
      modifyUserButton.setStyle("-fx-background-color: #add8e6; -fx-border-color: #000080;");
      deleteUserButton.setStyle("-fx-background-color: #ff6347; -fx-border-color: #8b0000;");

      ListView<User> userListView = new ListView<>();
      userListView.setPrefHeight(200);

      userListView.getItems().addAll(User.getUsers());

      // Set custom cell factory for user ListView
      userListView.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
        @Override
        public ListCell<User> call(ListView<User> param) {
          return new ListCell<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
              super.updateItem(item, empty);
              if (item != null && !empty) {
                setText(item.getUsername() + " (" + item.getDisplayName() + ")");
              } else {
                setText(null);
              }
            }
          };
        }
      });

      int columnIndex = 15;
      int rowIndex = 4;

      Image image = new Image(getClass().getResourceAsStream("image.JPEG"));
      ImageView imageView = new ImageView(image);

      imageView.setFitWidth(450);
      imageView.setFitHeight(450);

      GridPane.setConstraints(imageView, columnIndex, rowIndex);
      gridPane.getChildren().add(imageView);

      columnIndex++;

      userListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
          usernameTextField.setText(newSelection.getUsername());
          displayNameTextField.setText(newSelection.getDisplayName());
          passwordTextField.setText(newSelection.getPassword());
        }
      });

      // Create user action
      createUserButton.setOnAction(event -> {
        String username = usernameTextField.getText();
        String displayName = displayNameTextField.getText();
        String password = passwordTextField.getText();

        if (!username.isEmpty() && !displayName.isEmpty() && !password.isEmpty()) {
          User newUser = admin.createUser(username, displayName, password);
          userListView.getItems().add(newUser);
        }
      });

      // Modify user action
      modifyUserButton.setOnAction(event -> {
        User selectedUser = userListView.getSelectionModel().getSelectedItem();
        String newUsername = usernameTextField.getText();
        String newDisplayName = displayNameTextField.getText();
        String newPassword = passwordTextField.getText();

        if (selectedUser != null && !newUsername.isEmpty() && !newDisplayName.isEmpty()
            && !newPassword.isEmpty()) {
          admin.modifyUser(selectedUser, newUsername, newDisplayName, newPassword);
        }
      });

      // Delete user action
      deleteUserButton.setOnAction(event -> {
        User selectedUser = userListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
          admin.deleteUser(selectedUser);
          userListView.getItems().remove(selectedUser);
        }
      });

      // Binding button states
      createUserButton.disableProperty().bind(
          Bindings.isEmpty(usernameTextField.textProperty())
              .or(Bindings.isEmpty(displayNameTextField.textProperty()))
              .or(Bindings.isEmpty(passwordTextField.textProperty())));
      modifyUserButton.disableProperty().bind(userListView.getSelectionModel().selectedItemProperty().isNull());
      deleteUserButton.disableProperty().bind(userListView.getSelectionModel().selectedItemProperty().isNull());
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

      Button backButton = new Button("Back");
      backButton.setOnAction(event -> {
        // Implement your logic to go back to the previous scene here
        Stage currentStage = (Stage) scene.getWindow();
        currentStage.setScene(App.getPreviousScene());

      });
      stackPane.getChildren().add(gridPane);
      scene = new Scene(stackPane, 1000, 800);
    }

    return scene;
  }
}
