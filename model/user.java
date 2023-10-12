package model;

import java.util.List;

public class user {
    private int id;
    private String username;
    private String displayName;
    private String password;
    private List<Recipe> favoriteRecipes;
    private List<weeklyMealPlan> mealPlans;

    // Constructor, getters, and setters

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Recipe> getFavoriteRecipes() {
        return this.favoriteRecipes;
    }

    public void setFavoriteRecipes(List<Recipe> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }

    public List<weeklyMealPlan> getMealPlans() {
        return this.mealPlans;
    }

    public void setMealPlans(List<weeklyMealPlan> mealPlans) {
        this.mealPlans = mealPlans;
    }

    public user() {
    }

    public void addFavoriteRecipe(Recipe recipe) {
        favoriteRecipes.add(recipe);
    }

    public void removeFavoriteRecipe(int recipeIndex) {
        favoriteRecipes.remove(recipeIndex);
    }

    // Other methods
}
