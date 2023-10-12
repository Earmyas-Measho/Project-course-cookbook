package secfolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private static List<User> users = new ArrayList<User>();
    private static List<Recipe> allRecipes = new ArrayList<>();
    private String password;
    private List<Recipe> favoriteRecipes;
    private List<Message> messages;
    private List<WeeklyDinnerList> weeklyDinnerLists;
    private static User loggedIn;
    private StringProperty username;
    private StringProperty displayName;

    public User(String username, String displayName, String password) {
        this.username = new SimpleStringProperty(username);
        this.displayName = new SimpleStringProperty(displayName);
        this.password = password;
        this.favoriteRecipes = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.weeklyDinnerLists = new ArrayList<>();
        this.messages = new ArrayList<>();
        User.loggedIn = null;
        users.add(this);

    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public StringProperty usernameProperty() {
        return this.username;
    }

    public String getDisplayName() {
        return this.displayName.get();
    }

    public void setDisplayName(String displayName) {
        this.displayName.set(displayName);
    }

    public StringProperty displayNameProperty() {
        return this.displayName;
    }

    // User story 30:
    // User registration
    public static User register(String username, String displayName, String password) {
        // Add checks to ensure the username is unique
        for (User existingUser : users) {
            if (existingUser.getUsername().equals(username)) {
                return null; // Return null if the username already exists
            }
        }
        return new User(username, displayName, password);
    }

    public void deleteRecipe(Recipe recipe) {
        allRecipes.remove(recipe);
    }

    // Mark a recipe as favorite (User story 20)
    public void markRecipeAsFavorite(Recipe recipe) {
        if (!favoriteRecipes.contains(recipe)) {
            favoriteRecipes.add(recipe);
        }
    }

    // Unmark a recipe as favorite (User story 21)
    public void unmarkRecipeAsFavorite(Recipe recipe) {
        favoriteRecipes.remove(recipe);
    }

    public void removeRecipe(Recipe recipe) {
        allRecipes.remove(recipe);
    }

    public String getUsername() {
        return this.username.get();
    }

    public String getPassword() {
        return this.password;
    }

    // User story 31:
    public void setPassword(String password) {
        this.password = password;
    }

    public List<Recipe> getFavoriteRecipes() {
        return this.favoriteRecipes;
    }

    public void setFavoriteRecipes(List<Recipe> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }

    public List<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    // user story 4
    public List<WeeklyDinnerList> getWeeklyDinnerLists() {
        return this.weeklyDinnerLists;
    }

    public void setWeeklyDinnerLists(List<WeeklyDinnerList> weeklyDinnerLists) {
        this.weeklyDinnerLists = weeklyDinnerLists;
    }

    public void addFavoriteRecipe(Recipe recipe) {
        favoriteRecipes.add(recipe);
    }

    public void removeFavoriteRecipe(Recipe recipe) {
        favoriteRecipes.remove(recipe);
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void addWeeklyDinnerList(WeeklyDinnerList weeklyDinnerList) {
        weeklyDinnerLists.add(weeklyDinnerList);
    }

    // User story 26 and 27:
    public void sendMessage(User recipient, String content, Recipe recipe) {
        Message message = new Message(this, recipient, content, recipe);
        messages.add(message);
        recipient.receiveMessage(message);
    }

    public void addToFavorites(Recipe recipe) {
        favoriteRecipes.add(recipe);
    }

    public void removeFromFavorites(Recipe recipe) {
        favoriteRecipes.remove(recipe);
    }

    // User story 6:
    public List<Recipe> searchRecipesByName(String recipeName) {
        List<Recipe> foundRecipes = new ArrayList<>();
        for (Recipe recipe : this.favoriteRecipes) {
            if (recipe.getName().equalsIgnoreCase(recipeName)) {
                foundRecipes.add(recipe);
            }
        }
        return foundRecipes;
    }

    // User story 7:
    public List<Recipe> searchRecipesByIngredient(String ingredient) {
        List<Recipe> matchingRecipes = new ArrayList<>();
        for (Recipe recipe : allRecipes) {
            for (Ingredient recipeIngredient : recipe.getIngredients()) {
                if (recipeIngredient.getName().equalsIgnoreCase(ingredient)) {
                    matchingRecipes.add(recipe);
                    break;
                }
            }
        }
        return matchingRecipes;
    }

    // user stroy 2
    public static User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedIn = user;
                return user;
            }
        }
        return null;
    }

    public static User getLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(User loggedIn) {
        User.loggedIn = loggedIn;
    }

    public User getLoggedInUser() {
        return loggedIn;
    }

    // User story 9: Browse favorite recipes
    public static List<Recipe> browseFavoriteRecipes() {
        if (loggedIn != null) {
            return new ArrayList<>(loggedIn.favoriteRecipes);
        } else {
            return new ArrayList<>();
        }
    }

    // User story 9: Browse all recipes
    public static List<Recipe> browseAllRecipes() {
        return new ArrayList<>(allRecipes);
    }

    public List<Recipe> getAllRecipes() {
        return allRecipes;
    }

    public static List<User> getUsers() {
        return users;
    }

    public void addRecipe(Recipe recipe) {
        allRecipes.add(recipe);
    }

    // User story 26:
    public void sendRecipeToUser(Recipe recipe, User recipient, String messageText) {
        Message message = new Message(this, recipient, messageText, recipe);
        recipient.receiveMessage(message);
        this.messages.add(message);
    }

    public void receiveMessage(Message message) {
        this.messages.add(message);
    }

    // user story 10
    public List<WeeklyDinnerList> viewUserWeeklyDinnerLists(User targetUser) {
        return targetUser.getWeeklyDinnerLists();
    }

    // user story 22
    public WeeklyDinnerList createWeeklyDinnerList(String weekName, LocalDate startDate, LocalDate endDate) {
        WeeklyDinnerList newWeeklyDinnerList = new WeeklyDinnerList(this, startDate, endDate);
        this.weeklyDinnerLists.add(newWeeklyDinnerList);
        return newWeeklyDinnerList;
    }

}
