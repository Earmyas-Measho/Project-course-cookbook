package secfolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShoppingList {
    private WeeklyDinnerList weeklyDinnerList;
    private List<Ingredient> ingredients;

    public ShoppingList(WeeklyDinnerList weeklyDinnerList) {
        this.weeklyDinnerList = weeklyDinnerList;
        this.ingredients = new ArrayList<>();
    }

    public ShoppingList(User user) {
        WeeklyDinnerList weeklyDinnerList = new WeeklyDinnerList(user, LocalDate.now(), LocalDate.now().plusDays(6));
        this.weeklyDinnerList = weeklyDinnerList;
        this.ingredients = new ArrayList<>();
    }

    public WeeklyDinnerList getWeeklyDinnerList() {
        return this.weeklyDinnerList;
    }

    public void setWeeklyDinnerList(WeeklyDinnerList weeklyDinnerList) {
        this.weeklyDinnerList = weeklyDinnerList;
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }

    public void updateIngredientQuantity(Ingredient ingredient, double newQuantity) {
        int index = ingredients.indexOf(ingredient);
        if (index >= 0) {
            Ingredient updatedIngredient = new Ingredient(ingredient.getName(), newQuantity, ingredient.getUnit());
            ingredients.set(index, updatedIngredient);
        }
    }

    public void createShoppingList() {
        for (DailyDinnerEntry entry : weeklyDinnerList.getDailyDinnerEntries()) {
            for (Recipe recipe : entry.getRecipes()) {
                for (Ingredient ingredient : recipe.getIngredients()) {
                    boolean found = false;
                    for (Ingredient shoppingListIngredient : ingredients) {
                        if (shoppingListIngredient.getName().equalsIgnoreCase(ingredient.getName())) {
                            double updatedQuantity = shoppingListIngredient.getQuantity() + ingredient.getQuantity();
                            updateIngredientQuantity(shoppingListIngredient, updatedQuantity);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        ingredients.add(
                                new Ingredient(ingredient.getName(), ingredient.getQuantity(), ingredient.getUnit()));
                    }
                }
            }
        }
    }

}
