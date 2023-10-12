package model;

import java.util.ArrayList;
import java.util.List;

public class DailyDinnerEntry {
    private String day;
    private List<Recipe> recipes;

    public DailyDinnerEntry(String day) {
        this.day = day;
        this.recipes = new ArrayList<>();
    }

    public String getDay() {
        return this.day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<Recipe> getRecipes() {
        return this.recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public void removeRecipe(Recipe recipe) {
        recipes.remove(recipe);
    }
}