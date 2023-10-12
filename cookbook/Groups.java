/**
 * 
 */
package cookbook;

/**
 * This is the Group Class
 *
 */
public class Groups {
	private int id;
	private String groupName;
	private String recipes;
	
	public Groups(int id, String groupName) {
		super();
		this.id = id;
		this.groupName = groupName;
	}
	
	public Groups(int id, String groupName, String recipes) {
		super();
		this.id = id;
		this.groupName = groupName;
		this.recipes = recipes;
	}

	// Getters
	
	public int getId() {
		return id;
	}


	public String getGroupName() {
		return groupName;
	}
	
	public String getRecipes() {
		return recipes;
	}
	
	
	// Setters
	
	
	public void setRecipes(String recipes) {
		this.recipes = recipes;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	// toString
	public String toSrString() {
		return "The group " + this.groupName + " has these recipes saved: \n " + this.recipes;
	}
	
	
	
	

}
