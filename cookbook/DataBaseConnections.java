package cookbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import secfolder.Comment;

public class DataBaseConnections {
	static Connection conn;
	
	 // Connect DataBase Method
	 public static void setConnection() throws SQLException {
		 try {
			 conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cookbook_new", "root", "root");
		} catch (Exception e) {
			System.err.println("DataBase not found");
		}
	 }
	 
	 // Close Connection DataBase Method
	 public static void connectionClose() throws SQLException {
	        if (conn != null) {
	            conn.close();
	            conn = null;
	            System.out.println("DataBase connection closed.");
	     }
	 }
	 
	 
	 // User Login Method
	 public static UserEntity login(String username, String password) {
		UserEntity onlineUser = null;
		try {
		// checking DataBase Connection
		if(conn == null || conn.isClosed()) 
			 setConnection();
		// Statement interface is a SQL statement to be executed against a database
		// creates a Statement object that is used to execute SQL Queries
		Statement statement = conn.createStatement();
		
		// UserName in DataBase?
		String querySelect = "SELECT userName, password FROM cookbook_new.users WHERE userName LIKE '"+username+"'";
		// ResultSet has the data obtained from the DataBase
		// executeQuery method executes the SQL Query
		ResultSet rs = statement.executeQuery(querySelect);
		if(rs.next()) {
			// checking Password
			String checkPassword = rs.getString("password");
			if(checkPassword.equals(password)) {
				onlineUser = new UserEntity(0, username, null, null);
				//errorMess.setText("Logged in successfully!");
			} else {
				System.out.println("Incorrect Password");
			}
		} 
		else {
			System.out.println("UserName doesn´t exist");
		}
		rs.close();
		statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return onlineUser;
	 }
	 
	 // User Registration Method
	 public static boolean registration(String username, String password) {
		// this method is a boolean, because in a practical way we only need to know if the user is registered or not
		// there is only one possibility true or false
		boolean result = false;
		try {
		// Connection
		if(conn == null || conn.isClosed()) 
			setConnection();
		// Statement interface is a SQL statement to be executed against a database
		// creates a Statement object that is used to execute SQL Queries
		Statement statement = conn.createStatement();
		
		// UserName in DataBase?
		String querySelect = "SELECT userName, password FROM cookbook_new.users WHERE userName = '"+username+"'";
		// ResultSet has the data obtained from the DataBase
		// executeQuery method executes the SQL Query
		ResultSet rs = statement.executeQuery(querySelect);
		if(rs.next()) {
			System.out.println("Please, change the username. This one already exists");
		}
		else {
				String query = "INSERT INTO `cookbook_new`.`users` (`userName`, `password`) VALUES (?, ?);";
				PreparedStatement insertStatement = conn.prepareStatement(query);
				insertStatement.setString(1, username);
				insertStatement.setString(2, password);
				insertStatement.execute();
				insertStatement.close();
				// Created one more constructor inside UserEntity
				// Because at this part we only care about username
				//onlineUser = new UserEntity(username);
				System.out.println("User registered");
				result = true;
			}
		
		rs.close();
		statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	 
	 // Delete User Method
	 public static boolean deleteUser(int id) {
		 // again, boolean method 
		 // we pass the id because if we have an id as PK inside the DataBase is much simpler to identify the user
		 // if you try to change it, wont work with the DataBase and we will need to alter multiple tables!!!!
		 boolean delete = false;
		 try {
			 // Connection
			 if(conn == null || conn.isClosed()) 
				 setConnection();
			 String deleteQuery = "DELETE FROM cookbook_new.users WHERE id = ?;" ;
			 PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery);
			 deleteStatement.setInt(1, id);
			 deleteStatement.execute();
			 delete = true;
			 deleteStatement.close();
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 return delete;
	 }
	 
	 // Add or Change Theme of a specific User
	 public static boolean addTheme(String userName, String theme) {
		 boolean addTheme = false;
		 try {
			// Connection
			if(conn == null || conn.isClosed()) 
				setConnection();
			String themeQuery = "UPDATE `cookbook_new`.`users` SET `theme` = ? WHERE `userName` = ?;";
			PreparedStatement addThemeStatement = conn.prepareStatement(themeQuery);
			addThemeStatement.setString(1, theme);
			addThemeStatement.setString(2, userName);
			addThemeStatement.execute();
			addTheme = true;
			addThemeStatement.close();
		 } catch (Exception e) {
			e.printStackTrace();
		}
		return addTheme;
	 }

	 public static String getTheme(String username){
		
		String query = "select theme from users where userName = '"+username+"';";
		
		try {

			if(conn == null || conn.isClosed()) {
				setConnection();
				System.out.println("connection");
			}
			
			Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
            String t = rs.getString("theme");
			return t;
			}
        	} catch (SQLException e) {
            	// TODO Auto-generated catch block
            	e.printStackTrace();
        }
		return null;
	 }
	 
	 // Add Recipe Method
	 public static RecipeEntity addRecipe(String name, String description, String ingredients, String category, String shortDesc) {
		 RecipeEntity recipe = new RecipeEntity();
		 recipe.setName(name);
		 recipe.setDescription(description);
		 try {
			 // checking DataBase Connection
			if(conn == null || conn.isClosed()) 
				setConnection();
			
			// addRecipe Query
			String addRecipeQuery = "INSERT INTO `cookbook_new`.`recipes` (`name`, `description`, `ingredients_list`, `category`, `short_desc`) VALUES (?, ?, ?, ?, ?);";
			
			// PreparedStatement to execute the Query
			PreparedStatement addRecipeStatement = conn.prepareStatement(addRecipeQuery);
				
			// Values for the Query
			addRecipeStatement.setString(1, name);
			addRecipeStatement.setString(2, description);
			addRecipeStatement.setNString(3, ingredients);
			addRecipeStatement.setString(4, category);
			addRecipeStatement.setString(5, shortDesc);
			addRecipeStatement.execute();
			addRecipeStatement.close();

			//add recipe to ingredient table
			String [] ingredientsArray = ingredients.split(",");

			for (int j = 0; j < ingredientsArray.length; j++) {
				String addToIngredientQuery = "SELECT recipes_names FROM `cookbook_new`.`ingredient` WHERE name = ?;";
				PreparedStatement addToIngredientPrep = conn.prepareStatement(addToIngredientQuery);
				addToIngredientPrep.setString(1, ingredientsArray[j]);
				ResultSet rSet = addToIngredientPrep.executeQuery();
			
				while (rSet.next()) {
					String recipesForCurrentIngr = rSet.getString("recipes_names");
					String updateRec_names = "UPDATE `cookbook_new`.`ingredient` SET recipes_names = ? WHERE name = ?";
					PreparedStatement p = conn.prepareStatement(updateRec_names);
					p.setString(1, recipesForCurrentIngr + ", " + name);
					p.setString(2, ingredientsArray[j]);
					p.executeUpdate();
					p.close();
				}
			
				rSet.close();
			}
			
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		 	return recipe;
	 }
	 
	 // Delete Recipe Method
	 public static boolean deleteRecipe(int recipeId) {
		 // Boolean because we only care if it is deleted or not
		 boolean delete = false;
		 try {
			 // Connection
			 if(conn == null || conn.isClosed()) 
				 setConnection();
			 String deleteRecipeQuery = "DELETE FROM `cookbook_new`.`recipes` WHERE id = ?;";
			 PreparedStatement deleteRecipe = conn.prepareStatement(deleteRecipeQuery);
			 deleteRecipe.setInt(1, recipeId);
			 deleteRecipe.execute();
			 delete = true;
			 deleteRecipe.close();
		 } catch (Exception e) {
				e.printStackTrace();
		 } return delete;
	}
	 
	 // Get all from Recipes Table
	 public static ArrayList<RecipeEntity> getAllRecipes() {
		 ArrayList<RecipeEntity> recipes = new ArrayList<>();
		 try {
			// Connection
			if(conn == null || conn.isClosed()) 
				setConnection();
			
			String query = "SELECT * FROM cookbook_new.recipes;";
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				// get information from each column
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String ingredientString = rs.getString("ingredients_list");
				String category = rs.getString("category");

				//System.out.println(category);
				
				RecipeEntity recipe = new RecipeEntity(id, name, description, ingredientString, category);
				recipes.add(recipe);
			}
			rs.close();
			statement.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return recipes;
	 }
	 
	 
	 // Search Recipe by Name
	 public static ArrayList<RecipeEntity> getRecipeByName(String recipeName) {
		 ArrayList<RecipeEntity> recipes = new ArrayList<>();
		 try {
			// Connection
			 if(conn == null || conn.isClosed()) 
 				setConnection();
 			String searchQuery = "SELECT * FROM `cookbook_new`.`recipes` WHERE name = ?;";
 			PreparedStatement searchRecipe = conn.prepareStatement(searchQuery);
 			searchRecipe.setString(1, recipeName);
 			
 			ResultSet rs = searchRecipe.executeQuery();
 			
 			if(rs.next()) {
 				int id = rs.getInt("id");
 				String shortDesc = rs.getString("short_desc");
 				String ingredientList = rs.getString("ingredients_list");
 				String category = rs.getString("category");
				String recName = rs.getString("name");
				String Desc = rs.getString("description");

 				RecipeEntity recipe = new RecipeEntity(id, recName, Desc, shortDesc, ingredientList, category); 
 				recipes.add(recipe);
 				
 			}	
 			searchRecipe.close();
			rs.close();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
		 return recipes;
 	 }

	  public static String getRecipeDescription(String name) {
        String pass = "select description from recipes where name = '"+name+"'";

        try {

			if(conn == null || conn.isClosed()) {
				setConnection();
				System.out.println("connection");
			}
			
			Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(pass);
            while (rs.next()) {
			return rs.getString("description");
            
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return "";
	 }

	 public static String getRecipeShortDescription(String name) {
        String pass = "select short_desc from recipes where name = '"+name+"'";
		System.out.println("Get recipes");

        try {

			if(conn == null || conn.isClosed()) {
				setConnection();
				System.out.println("connection");
			}
			
			Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(pass);
            while (rs.next()) {
			return rs.getString("short_desc");
            
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return "";
	 }
	 
	 // Search Recipe by Ingredient
	 public static ArrayList<RecipeEntity> getRecipeByIngredient(String ingredient) {
		ArrayList<RecipeEntity> recipe = new ArrayList<RecipeEntity>();
		Set<String> set = new HashSet<>();
		try {
		   // Connection
		   if(conn == null || conn.isClosed()) 
			   setConnection();
		   String searchIngredientQuery = "SELECT DISTINCT recipes_names FROM ingredient WHERE name LIKE '%"+ingredient+"%';";
		   PreparedStatement searchByIngredient = conn.prepareStatement(searchIngredientQuery);
		   //searchByIngredient.setString(0, ingredient);

		   ResultSet rs = searchByIngredient.executeQuery();
		   while (rs.next()) {
			String[] names = rs.getString("recipes_names").split(", ");
			for (String i : names) {
				i = i.replace(",", "");
				if (set.contains(i) == false) {
				set.add(i);
				System.out.print(i + " ");
				System.out.println(set.contains(i)); 
				}
			}
			
		   }
		   
		   
		} catch (SQLException e) {
		   e.printStackTrace();
		}
		
		for (String i : set) { 
		recipe.add(new RecipeEntity(i, getRecipeDescription(i), getRecipeShortDescription(i)));
		}
		return recipe;
		
	}
	 
	 // Add Ingredient Method
	 public static IngredientEntity addIngredient(String name, int quantity, String unit, String recipes_names) {
		 IngredientEntity ingredient = new IngredientEntity();
		 ingredient.setName(name);
		 ingredient.setQuantity(quantity);
		 ingredient.setUnit(unit);
		 ingredient.setRecipes(recipes_names);
		 try {
			 // checking DataBase Connection
			if(conn == null || conn.isClosed()) 
				setConnection();
			
			// addRecipe Query
			String addIngredientQuery = "INSERT INTO `cookbook_new`.`ingredient` (`name`, `quantity`, `unit`, `recipes_names`) VALUES (?, ?, ?, ?);";
			
			// PreparedStatement to execute the Query
			PreparedStatement addIngredientStatement = conn.prepareStatement(addIngredientQuery);

			// Values for the Query
			addIngredientStatement.setString(1, name);
			addIngredientStatement.setInt(2, quantity);
			addIngredientStatement.setString(3, unit);
			addIngredientStatement.setString(4, recipes_names);
			addIngredientStatement.execute();
			addIngredientStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		 	return ingredient;
	 }

	 public static boolean IngredientExists(String IngredientNameToCheck) {
        String pass = "select name from `cookbook_new`.`ingredient` where name = '"+IngredientNameToCheck+"'";
		System.out.println("Get ingr");
		String s = null;

        try {

			if(conn == null || conn.isClosed()) {
				setConnection();
				System.out.println("connection");
			}
			
			Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(pass);
            while (rs.next()) {
			s = rs.getString("name");
            
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		if(s == null){
			return false;
		}
		else{
			return true;
		}
	 }


	 public static ArrayList<UserEntity> getAndCreateAllUsers(){
		String pass = "select userName from users;";
		ArrayList<UserEntity> userEWithName = new ArrayList<UserEntity>();
        try {

			if(conn == null || conn.isClosed()) {
				setConnection();
				System.out.println("connection");
			}
			
			Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(pass);
            while (rs.next()) {
            userEWithName.add(new UserEntity(rs.getString("userName")));
            
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return userEWithName;
	 }

	 public static int getIDFromUserName(String username){
		
		String query = "select id from users where userName = '"+username+"';";
		
		try {

			if(conn == null || conn.isClosed()) {
				setConnection();
				System.out.println("connection");
			}
			
			Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
            String id = rs.getString("id");
			return Integer.parseInt(id);
			}
        	} catch (SQLException e) {
            	// TODO Auto-generated catch block
            	e.printStackTrace();
        }
		return -1;
	 }

	 public static String getUserNameFromID(int id){
		
		String query = "select * from users where id = '"+id+"';";
		
		try {

			if(conn == null || conn.isClosed()) {
				setConnection();
				System.out.println("connection");
			}
			
			Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
            String name = rs.getString("userName");
			return name;
			}
        	} catch (SQLException e) {
            	// TODO Auto-generated catch block
            	e.printStackTrace();
        }
		return "";
	 }

	 public static boolean userSetNotification(String userName, String notification) {
		try {
		   // Connection
		   if(conn == null || conn.isClosed()) 
			   setConnection();
		   String Query = "UPDATE `cookbook_new`.`users` SET `has_notification` = ? WHERE `userName` = ?;";
		   PreparedStatement addThemeStatement = conn.prepareStatement(Query);
		   addThemeStatement.setString(1, notification);
		   addThemeStatement.setString(2, userName);
		   addThemeStatement.execute();
		   addThemeStatement.close();
		   return true;
		} catch (Exception e) {
		   e.printStackTrace();
	   }
	   return false;
	}

	 public static boolean userHasNotification(String username){
		
		String query = "select `has_notification` from users where userName = '"+username+"';";
		
		try {

			if(conn == null || conn.isClosed()) {
				setConnection();
				System.out.println("connection");
			}
			
			Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
            	String yn = rs.getString("has_notification");
				if(yn.equals("n")){
					return false;
				} else {
					return true;
					}
				}
        	} catch (SQLException e) {
            	// TODO Auto-generated catch block
            	e.printStackTrace();
        }
		return false;
	 }

	 // Add Message to DataBase
	 public static boolean addMessage(int senderId, int recipeId, String content, int receiverId) {
		boolean sent = false;
		try {
			// Connection
			if(conn == null || conn.isClosed()) 
				setConnection();
			
			String sendingQuery = "INSERT INTO `cookbook_new`.`messages` (`sender`, `recipe_id`, `content`, `receiver`) VALUES (?, ?, ?, ?);";
			PreparedStatement preparedStatement = conn.prepareStatement(sendingQuery);
			preparedStatement.setInt(1, senderId);
			preparedStatement.setInt(2, recipeId);
			preparedStatement.setString(3, content);
			preparedStatement.setInt(4, receiverId);
			
			preparedStatement.execute();
			sent = true;
			preparedStatement.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sent;
	}

	public static ArrayList<Message> getAllMessages(int userId) throws SQLException {
		ArrayList<Message> messageList = new ArrayList<Message>();
		int sender = 0;
		int recipe = 0;
		String content = "";
		int receiver = 0;
		String messagesString = "SELECT * FROM messages WHERE receiver = '"+userId+"';";
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(messagesString);
		while (rs.next()) {
		sender = rs.getInt("sender");
		recipe = rs.getInt("recipe_id");
		content = rs.getString("content");
		receiver = rs.getInt("receiver");
		messageList.add(new Message(sender, content, receiver, recipe));
		//if (sender > 0) {
		//	messageList.add(new Message(sender, content, receiver, recipe));
		//}
		}

		return messageList;

	}

	 public static int getRecipeIDFromName(String recName){
		
		String query = "select id from recipes where name = '"+recName+"';";
		
		try {

			if(conn == null || conn.isClosed()) {
				setConnection();
				System.out.println("connection");
			}
			
			Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
            String id = rs.getString("id");
			return Integer.parseInt(id);
			}
        	} catch (SQLException e) {
            	// TODO Auto-generated catch block
            	e.printStackTrace();
        }
		return -1;
	 }

	 public static RecipeEntity getRecipeFromId(int id){
		
		String query = "select * from recipes where id = '"+id+"';";
		
		try {

			if(conn == null || conn.isClosed()) {
				setConnection();
				System.out.println("connection");
			}
			
			Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
            String name = rs.getString("name");
			String description = rs.getString("description");
			String category = rs.getString("category");

			return new RecipeEntity(id, name, description, null, category);
			}
        	} catch (SQLException e) {
            	// TODO Auto-generated catch block
            	e.printStackTrace();
        }
		return new RecipeEntity(id, null, null, null, null);
	 }
	 
	// Delete Ingredient Method
		 public static boolean deleteIngredient(int ingredientId) {
			 // Boolean because we only care if it is deleted or not
			 boolean delete = false;
			 try {
				 // Connection
				 if(conn == null || conn.isClosed()) 
					 setConnection();
				 String deleteIngredientQuery = "DELETE FROM `cookbook_new`.`ingredient` WHERE id = ?;";
				 PreparedStatement deleteIngredient = conn.prepareStatement(deleteIngredientQuery);
				 deleteIngredient.setInt(1, ingredientId);
				 deleteIngredient.execute();
				 delete = true;
				 deleteIngredient.close();
			 } catch (Exception e) {
					e.printStackTrace();
			 } return delete;
		}

	public static ArrayList<RecipeEntity> getRecipeByCategory(String tag) {

		ArrayList<RecipeEntity> recipe = new ArrayList<RecipeEntity>();
		try {
		   // Connection
		   if(conn == null || conn.isClosed()) 
			   setConnection();
		   String searchIngredientQuery = "SELECT DISTINCT * FROM recipes WHERE category LIKE '%"+tag+"%';";
		   PreparedStatement searchByIngredient = conn.prepareStatement(searchIngredientQuery);
		   //searchByIngredient.setString(0, ingredient);

		   ResultSet rs = searchByIngredient.executeQuery();
		   while (rs.next()) {
			// Get information
			String recipeName = rs.getString("name");
			String short_desc = rs.getString("short_desc");
			String category = rs.getString("category");
			String Desc = rs.getString("description");
			
			RecipeEntity recipes = new RecipeEntity(recipeName, Desc, short_desc, category);
			recipe.add(recipes);
		}
		   
		} catch (SQLException e) {
		   e.printStackTrace();
		}
		return recipe;
	}

	public static boolean isAdmin(String username) {

		String pass = "select admin from users where userName = '"+username+"'";;
		
		try {

			if(conn == null || conn.isClosed()) {
				setConnection();
				System.out.println("connection");
			}
			
			Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(pass);
            while (rs.next()) {
            String type = rs.getString("admin");
            if (type.equals("admin")) {
				return true;
			} else {
				return false;
			}
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

		return false;

	}

	// Get Favorite Recipes from a User
	public static ArrayList<RecipeEntity> getFavoriteRecipes(int userId){
		ArrayList<RecipeEntity> favoriteRecipes = new ArrayList<>();
		try {
			   // Connection
			   if(conn == null || conn.isClosed()) 
				   setConnection();
			   String searchFavourites = "SELECT * FROM cookbook_new.favorite_recipes INNER JOIN cookbook_new.recipes ON recipe_id = cookbook_new.recipes.id WHERE user_id = ?;";
			   PreparedStatement searchFavorites = conn.prepareStatement(searchFavourites);
			   searchFavorites.setInt(1, userId);
			   
			   ResultSet rs = searchFavorites.executeQuery();
			   while (rs.next()) {
				   // Get information
				   String recipeName = rs.getString("name");
				   String short_desc = rs.getString("short_desc");
				   String category = rs.getString("category");
				   String Desc = rs.getString("description");
				   
				   RecipeEntity recipes = new RecipeEntity(recipeName, Desc, short_desc, category);
				   favoriteRecipes.add(recipes);
			   }
			   
			   searchFavorites.close();
			   rs.close();
			} catch (Exception e) {
					e.printStackTrace();
			}
		   return favoriteRecipes;
	   }

	   public static boolean recipeIsFavOfUser(int userID, String recName){
		ArrayList<RecipeEntity> allUserFav = getFavoriteRecipes(userID);
		for (int i = 0; i < allUserFav.size(); i++) {
			if(allUserFav.get(i).getName().equals(recName)){
				return true;
			}
		}
		return false;
	   }
	
	// Add Recipe to Users Favorites
	public static boolean addRecipeToFavorites(int recipeId, int userId) {
		// Boolean because we only care if it´s added or not
		boolean added = false;
		try {
			// Connection
			if(conn == null || conn.isClosed()) 
				setConnection();
			String addFavouriteQuery = "INSERT INTO `cookbook_new`.`favorite_recipes` (`recipe_id`, `user_id`) VALUES (?, ?);";
			PreparedStatement addRecipe = conn.prepareStatement(addFavouriteQuery);
			addRecipe.setInt(1, recipeId);
			addRecipe.setInt(2, userId);
			addRecipe.execute();
			added = true;
			addRecipe.close();
		} catch (Exception e) {
			   e.printStackTrace();
		} return added;
   }


  // Delete Recipe from Favorites
  public static boolean deleteRecipeFromFavorites(int recipeId, int userId) {
	// Boolean because we only care if it´s added or not
	boolean deleted = false;
	try {
		// Connection
		if(conn == null || conn.isClosed()) 
			setConnection();
		String deleteFavoriteQuery = "DELETE FROM `cookbook_new`.`favorite_recipes` WHERE recipe_id = ? and user_id = ?";
		PreparedStatement deleteRecipe = conn.prepareStatement(deleteFavoriteQuery);
		deleteRecipe.setInt(1, recipeId);
		deleteRecipe.setInt(2, userId);
		
		
		deleteRecipe.execute();
		deleted = true;
		deleteRecipe.close();
	} catch (Exception e) {
		e.printStackTrace();
	} return deleted;
}
   
   // Get Weekly Meal Plan from a specific UserID
   public static String getWeeklyPlan (int userId) {
	   String weeklyMealPlan = "empty";
	   try {
		   // Connection to Database
		   if(conn == null || conn.isClosed()) 
			   setConnection();
		   
		   String weeklyMealPlanQuery = "SELECT recipes FROM `cookbook_new`.`weekly_meal_plan` WHERE `user_id` = ?;";
		   PreparedStatement searchMealPlan = conn.prepareStatement(weeklyMealPlanQuery);
		   searchMealPlan.setInt(1, userId);
		   
		   ResultSet rs = searchMealPlan.executeQuery();
		   if (rs.next()) {
			   // Get information
			   String recipes = rs.getString("recipes");
			   
			   return recipes;
		   }
	   } catch (Exception e) {
		   // TODO: handle exception
	   }
	   return weeklyMealPlan;
   }
   
   // Add Weekly Meal Plan
   public static boolean addWeeklyMealPlan(int userId, String recipes) {
	   // Boolean because we only care if it´s added or not
	   boolean planAdded = false;
	   try {
		   // Connection
		   if(conn == null || conn.isClosed()) 
			   setConnection();
		   String addPlan = "INSERT INTO `cookbook_new`.`weekly_meal_plan` (`user_id`, `recipes`) VALUES (?, ?);";
		   PreparedStatement addWeeklyPlan = conn.prepareStatement(addPlan);
		   addWeeklyPlan.setInt(1, userId);
		   addWeeklyPlan.setString(2, recipes);
		   
		   
		   addWeeklyPlan.execute();
		   planAdded = true;
		   addWeeklyPlan.close();
	   } catch (Exception e) {
		   e.printStackTrace();
	   } return planAdded;
   }

   public static boolean updateWeeklyMealPlan(int userId, String recipes) {
	// Boolean because we only care if it´s added or not
	boolean planAdded = false;
	String weekly = getWeeklyPlan(userId);
	try {
		// Connection
		if(conn == null || conn.isClosed()) 
			setConnection();
		System.out.println(recipes);
		String addPlan = "UPDATE `cookbook_new`.`weekly_meal_plan` SET `recipes` = ? WHERE user_id = ?;";
		String replace = "";
		PreparedStatement addWeeklyPlan = conn.prepareStatement(addPlan);
		addWeeklyPlan.setInt(2, userId);
		addWeeklyPlan.setString(1, recipes);
		
		
		addWeeklyPlan.execute();
		planAdded = true;
		addWeeklyPlan.close();
	} catch (Exception e) {
		e.printStackTrace();
	} return planAdded;
}
   
   
   // Delete Weekly Meal Plan
   public static boolean deleteWeeklyMealPlan(int id) {
	   // Boolean because we only care if it´s added or not
	   boolean planDeleted = false;
	   try {
		   // Connection
		   if(conn == null || conn.isClosed()) 
			   setConnection();
		   String deletePlan = "DELETE FROM `cookbook_new`.`weekly_meal_plan` WHERE id = ?;";
		   PreparedStatement deleteWeeklyPlan = conn.prepareStatement(deletePlan);
		   deleteWeeklyPlan.setInt(1, id);
		   
		   
		   deleteWeeklyPlan.execute();
		   planDeleted = true;
		   deleteWeeklyPlan.close();
	   } catch (Exception e) {
		   e.printStackTrace();
	   } return planDeleted;
   }

   public static String getAllIngrOfRecipe(int recID) {
	String ingedients = "";
	try {
		// Connection to Database
		if(conn == null || conn.isClosed()) 
			setConnection();
		
		String weeklyMealPlanQuery = "SELECT `ingredients_list` FROM `cookbook_new`.`recipes` WHERE `id` = ?;";
		PreparedStatement searchMealPlan = conn.prepareStatement(weeklyMealPlanQuery);
		searchMealPlan.setInt(1, recID);
		
		ResultSet rs = searchMealPlan.executeQuery();
		if (rs.next()) {
			// Get information
			String ingredients = rs.getString("ingredients_list");
			
			return ingredients;
		}
	} catch (Exception e) {
		// TODO: handle exception
	}
	return ingedients;
}


public static boolean addComment(Comment comment) {
	// Boolean because we only care if it´s added or not
	boolean planAdded = false;
	String username = comment.getAuthor();
	String recipeName = comment.getRecipe();
	String content = comment.getContent();
	int userId = getIDFromUserName(username);
	int recipeId = getRecipeIDFromName(recipeName);
	
	try {
		// Connection
		if(conn == null || conn.isClosed()) 
			setConnection();
		String addPlan = "INSERT INTO `cookbook_new`.`comment` (`user_id`, `recipe_id`, `content`) VALUES (?, ?, ?);";
		PreparedStatement addWeeklyPlan = conn.prepareStatement(addPlan);
		addWeeklyPlan.setInt(1, userId);
		addWeeklyPlan.setInt(2, recipeId);
		addWeeklyPlan.setString(3, content);
		
		
		addWeeklyPlan.execute();
		planAdded = true;
		addWeeklyPlan.close();
	} catch (Exception e) {
		e.printStackTrace();
	} return planAdded;
}

public static ArrayList<Comment> getCommentsFromRecipe(String recipeName) {
	ArrayList<Comment> comments = new ArrayList<>();
	int recipeId = getRecipeIDFromName(recipeName);
	try {
		// Connection to Database
		if(conn == null || conn.isClosed()) 
			setConnection();
		
		String weeklyMealPlanQuery = "SELECT * FROM `cookbook_new`.`comment` WHERE `recipe_id` = ?;";
		PreparedStatement searchMealPlan = conn.prepareStatement(weeklyMealPlanQuery);
		searchMealPlan.setInt(1, recipeId);
		System.out.println("RecipeID: " + recipeId);
		
		ResultSet resultSet = searchMealPlan.executeQuery();
		while (resultSet.next()) {
			// Get information
			String content = resultSet.getString("content");
			int userId = resultSet.getInt("user_id");
			Comment readComment = new Comment(getUserNameFromID(userId), recipeName, content);
			comments.add(readComment);
			System.out.println("content: "+ content);
			
		}
	} catch (Exception e) {
		// TODO: handle exception
	}
	return comments;
}

public static boolean deleteComment(Comment comment) {
	// Boolean because we only care if it is deleted or not
	boolean delete = false;
	try {
		// Connection
		if(conn == null || conn.isClosed()) 
			setConnection();
		String deleteRecipeQuery = "DELETE FROM `cookbook_new`.`comment` WHERE content = ? AND user_id = ?;";
		PreparedStatement deleteRecipe = conn.prepareStatement(deleteRecipeQuery);
		deleteRecipe.setString(1, comment.getContent());
		deleteRecipe.setInt(2, getIDFromUserName(LoginMenu.user.getUsername()));
		deleteRecipe.execute();
		delete = true;
		System.out.println("deleted");
		deleteRecipe.close();
	} catch (Exception e) {
		   e.printStackTrace();
	} return delete;
}



	 
//	 public static List<RecipeEntity> getFavoriteRecipes(int id){
//		 try {
//			 Statement statement = conn.createStatement();
//			 String favoritesQuery = "SELECT recipe_id FROM favorite_recipes WHERE user_id = '%s'" + id;
//			 ResultSet result = statement.executeQuery(favoritesQuery);
//			 LinkedList<RecipeEntity> favorites = new LinkedList<>();
//			 while(result.next()) {
//				 int id = result.getInt("recipe_id");
//			 }
//		 }
//	 }
	 
	
//	private static RecipeEntity getRecipe(int recipeId) {
//		// gives all information from recipes table using recipe_id
//		try {
//			// Statement interface is a SQL statement to be executed against a database
//			// creates a Statement object that is used to execute SQL Queries
//			Statement statament = conn.createStatement();
//			// Query to access DataBase
//			String getRecipe = "SELECT * from recipes WHERE id = '%s'" + recipeId;
//			// ResultSet has the data obtained from the DataBase
//			// executeQuery method executes the SQL Query
//			ResultSet result = statament.executeQuery(getRecipe);
//			result.next();
//			String recipeName = result.getString("name");
//			String recipeDescription = result.getString("description");
//			String recipeIngredients = result.getString("ingredients_list");
//			String recipeCategory = result.getString("category");
//			result.close();
//			return RecipeEntity
//			} catch (Exception e) {
//				e.printStackTrace();
//				}
//			}
}
