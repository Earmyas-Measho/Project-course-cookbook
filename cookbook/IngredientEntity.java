/**
 * 
 */
package cookbook;


/**
 * Ingredient Java Class.
 * DataBaseConnections will call these methods.
 *
 */
public class IngredientEntity {
	private int id;
	private String name;
	private int quantity;
	private String unit;
	private String recipes;
	
	public IngredientEntity() {
		
	}
	
	public IngredientEntity(String name, String recipes) {
		super();
		this.name = name;
		this.recipes = recipes;
	}

	public IngredientEntity(int id, String name, int quantity, String unit) {
		super();
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.unit = unit;
	}

	public IngredientEntity(int id, String name, int quantity, String unit, String recipes) {
		super();
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.unit = unit;
		this.recipes = recipes;
	}

	// Getters
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getUnit() {
		return unit;
	}

	public String getRecipes() {
		return recipes;
	}
	
	// Setters

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setRecipes(String recipes) {
		this.recipes = recipes;
	}

	@Override
	public String toString() {
		return "Ingredient name: " + name + " ------- recipes: " + recipes;
	}
	
	

	
	
	
	
	

}
