/**
 * 
 */
package cookbook;

import java.io.File;
import java.io.FileWriter;

/**
 * This is Recipe Class.
 * Call methods in DataBaseConnections.
 *
 */
public class RecipeEntity {
	private int id;
	private String  name;
	private String description;
	private String shortDescription;
	private String ingredientList;
	private String category;
	private boolean hasNoti;
	
	// constructors
	public RecipeEntity() {

	}
	
	public RecipeEntity(String name, String description, String shor, String category) {
		super();
		this.name = name;
		this.description = description;
		this.shortDescription = shor;
		this.category = category;
	}
	
	public RecipeEntity(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public RecipeEntity(String name, String shortDescription) {
		super();
		this.name = name;
		this.description = shortDescription;
	}
	
	public RecipeEntity(String name, String description, String shor) {
		super();
		this.name = name;
		this.description = description;
		this.shortDescription = shor;
	}

/* 
	public RecipeEntity(String name, String ingredients, String description, String category) {
		super();
		this.name = name;
		ingredientList = ingredients;
		this.description = description;
		this.category = category;
	}
*/

	public RecipeEntity(int id, String name, String description, String category) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;
	}

	public RecipeEntity(int id, String name, String description, String ingredientList,
			String category) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.ingredientList = ingredientList;
		this.category = category;
	}

	public RecipeEntity(int id, String name, String description, String shortDesc, String ingredientList,
			String category) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.ingredientList = ingredientList;
		this.category = category;
		this.shortDescription = shortDesc;
	}

	// Save to a printable file method
	public void saveInFile(String fileName) {
		try {
			FileWriter file = new FileWriter(fileName);
			file.write(this.name + "\nIngredients:\n" + this.ingredientList );;
//			if(ingredientList != null) {
//				for(Ingredient ingredient: this.ingredientList) {
//					file.write(ingredient.getQuantity() + " " + ingredient.getUnit() + " " + ingredient.getName());
//				}
//			}
			file.write("\nRecipe Category:\n" + this.category);
			file.write("\nRecipe Description:\n" + this.description);
			// file.write("\nPreparation:\n" + this.prepareMethod);
			// file.write("\nPrepare Time:\n + this.prepareTime);
			file.close();
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	// Save File
	public void saveInFile() {
		saveInFile(this.name);
	}
	
	// Delete File Method
	public boolean deleteFile(String fileName) {
		File file = new File(fileName);
		return file.delete();
	}
	
	// Getters
	public int getId() {
		return id;
	}

	public boolean getNoti(){
		return hasNoti;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getIngredientList() {
		return ingredientList;
	}
	
	public String getCategory() {
		return category;
	}

	public String getShortDesc(){
		return shortDescription;
	}
	
	// Setters

	public void setName(String name) {
		this.name = name;
	}

	public void setShortDesc(String shor){
		shortDescription = shor;
	}

	public void setNoti(boolean n){
		hasNoti = n;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setCategory(String categ) {
		this.category = categ;
	}
	
	public void setIngredientList(String ingredientList) {
		this.ingredientList = ingredientList;
	}

	
	// toString
	public String toString() {
		return this.name + "\n" + "\nA short description of the recipe:\n" + "-- " + this.description + "\n" + "\nIngredients:\n" + "-- " + this.ingredientList + "\n" + "\nCategory:\n" + "-- " + this.category;
	}
	
	
	
	
	
	
	
	
	
	

}
