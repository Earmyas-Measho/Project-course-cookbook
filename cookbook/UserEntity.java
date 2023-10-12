/**
 * 
 */
package cookbook;

import java.util.ArrayList;
import java.util.List;

import model.Ingredient;
import model.Recipe;

/**
 * User Java Class.
 * We have to call it on DataBaseConnections.
 *
 */
public class UserEntity {
	private int id;
	private String username;
	private String password;
	private List<Recipe> favoriteRecipes;
	private int groupId;
	private List<Ingredient> shoppingList;
	private String weeklyMealPlan;
	
	// Constructor
	
	public UserEntity() {
		
	}

	public UserEntity(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public UserEntity(int id, String weeklyMealPlan) {
		super();
		this.id = id;
		this.weeklyMealPlan = weeklyMealPlan;
	}

	public UserEntity(int id, String username, List<Recipe> favoriteRecipes, List<Ingredient> shoppingList,
			String weeklyMealPlan) {
		super();
		this.id = id;
		this.username = username;
		this.favoriteRecipes = favoriteRecipes;
		this.shoppingList = shoppingList;
		this.weeklyMealPlan = weeklyMealPlan;
	}

	public UserEntity(String username) {
		this.username = username;
		this.favoriteRecipes = new ArrayList<>();
		this.shoppingList = new ArrayList<>();
	}
	
	public UserEntity(int id, String username, List<Recipe> favoriteRecipes, List<Ingredient> shoppingList) {
		super();
		this.id = id;
		this.username = username;
		this.favoriteRecipes = favoriteRecipes;
		this.shoppingList = shoppingList;
	}
	
	public UserEntity(int id, String username, int groupId) {
		super();
		this.id = id;
		this.username = username;
		this.groupId = groupId;
	}

	// Methods
	
	// addFavorites
	public void addFavorite(Recipe favoriteRecipe) {
		if(!favoriteRecipes.contains(favoriteRecipe)) {
			favoriteRecipes.add(favoriteRecipe);
		} else {
			System.out.println("Recipe is already a favorite!");
		}
	}

	//add Ingredient to Shopping List
	public void addIngredientShoppingList(Ingredient ingredient) {
		shoppingList.add(ingredient);
	}
	
	// Remove favoriteRecipe
	public void removeFavoriteRecipe(Recipe removableRecipe) {
		favoriteRecipes.remove(removableRecipe);
	}
	
	// Remove Ingredient from Shopping List
	public void removeIngredientShoppingList(Ingredient ingredient) {
		shoppingList.remove(ingredient);
	}
	
	// Remove if Ingredient is already in ShoppingList
	public void removeExistingIngredient(String name) {
		for(Ingredient ingredientIn: shoppingList) {
			if(ingredientIn.getName().equals(name)) {
				shoppingList.remove(ingredientIn);
			}
		}
	}
	
	// Print ShoppingList
	public void showShoppingList() {
		System.out.println("Ingredients to buy:\n");
		for(Ingredient ingredient: shoppingList) {
			System.out.println("- " + ingredient.getName());
		}
	}

	//Getters
	
	public int getId() {
		return id;
	}

	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

	public List<Recipe> getFavoriteRecipes() {
		return favoriteRecipes;
	}

	public List<Ingredient> getShoppingList() {
		return shoppingList;
	}
	
	public String getWeeklyMealPlan() {
		return weeklyMealPlan;
	}
	
	public int getGroupId() {
		return groupId;
	}
	
	// Setters
	
	public void setUserName(String userName) {
		this.username = userName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setFavoriteRecipes(List<Recipe> favoriteRecipes) {
		this.favoriteRecipes = favoriteRecipes;
	}

	public void setShoppingList(List<Ingredient> shoppingList) {
		this.shoppingList = shoppingList;
	}
	
	public void setWeeklyMealPlan(String weeklyMealPlan) {
		this.weeklyMealPlan = weeklyMealPlan;
	}
	
	private void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	@Override
	public String toString() {
		return "The user " + id + " with userName " + username + ", has these favorite recipes:\n" + favoriteRecipes + "\nAnd shopping list:\n" + shoppingList;
	}
	
	
	
	
	
	
	
	
	

}
