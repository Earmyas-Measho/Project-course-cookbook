package model;

import java.util.List;
import java.util.Map;

public class weeklyMealPlan {
    private int id;
    private Map<String, List<Recipe>> recipesByDay;

    public weeklyMealPlan() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, List<Recipe>> getRecipesByDay() {
        return this.recipesByDay;
    }

    public void setRecipesByDay(Map<String, List<Recipe>> recipesByDay) {
        this.recipesByDay = recipesByDay;
    }

    public void addRecipeForDay(String day, Recipe recipe) {
        recipesByDay.get(day).add(recipe);
    }

    // Other methods
}
