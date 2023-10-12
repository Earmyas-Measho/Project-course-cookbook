package secfolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeeklyDinnerList {
    private User owner;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<DailyDinnerEntry> dailyDinnerEntries;
    private HashMap<String, Recipe> recipes;

    public WeeklyDinnerList(User owner, LocalDate startDate, LocalDate endDate) {
        this.owner = owner;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dailyDinnerEntries = new ArrayList<>();
        this.recipes = new HashMap<>();
    }

    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<DailyDinnerEntry> getDailyDinnerEntries() {
        return this.dailyDinnerEntries;
    }

    public void setDailyDinnerEntries(List<DailyDinnerEntry> dailyDinnerEntries) {
        this.dailyDinnerEntries = dailyDinnerEntries;
    }

    public void addDailyDinnerEntry(DailyDinnerEntry dailyDinnerEntry) {
        dailyDinnerEntries.add(dailyDinnerEntry);
    }

    public void addRecipe(String day, Recipe recipe) {
        for (DailyDinnerEntry entry : dailyDinnerEntries) {
            if (entry.getDay().equals(day)) {
                entry.addRecipe(recipe);
                return;
            }
        }
        DailyDinnerEntry newEntry = new DailyDinnerEntry(day);
        newEntry.addRecipe(recipe);
        dailyDinnerEntries.add(newEntry);
    }

    public ShoppingList createShoppingList() {
        ShoppingList shoppingList = new ShoppingList(this);
        for (DailyDinnerEntry entry : dailyDinnerEntries) {
            for (Recipe recipe : entry.getRecipes()) {
                for (Ingredient ingredient : recipe.getIngredients()) {
                    if (!shoppingList.getIngredients().contains(ingredient)) {
                        shoppingList.getIngredients().add(ingredient);
                    }
                }
            }
        }
        return shoppingList;
    }

    public List<Recipe> getRecipesForDay(String day) {
        List<Recipe> recipes = new ArrayList<>();
        for (DailyDinnerEntry entry : dailyDinnerEntries) {
            if (entry.getDay().equals(day)) {
                recipes.addAll(entry.getRecipes());
            }
        }
        return recipes;
    }

    public HashMap<String, Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(HashMap<String, Recipe> recipes) {
        this.recipes = recipes;
    }

}
