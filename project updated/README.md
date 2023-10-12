## Getting Started


## Recipe class:
hasMany Ingredients (Composition): A Recipe has a list of Ingredient objects.
hasMany Tags (Association): A Recipe has a list of Tag objects.
hasMany Comments (Composition): A Recipe has a list of Comment objects.

## Ingredient class:
belongsTo Recipe (Composition): An Ingredient is part of a Recipe.

## User class:
hasMany Recipes as favoriteRecipes (Association): A User has a list of Recipe objects marked as favorites.
hasMany WeeklyMealPlans (Composition): A User has a list of WeeklyMealPlan objects.

## Tag class:
belongsTo Recipe (Association): A Tag is associated with one or more Recipe objects.

## WeeklyMealPlan class:
hasMany Recipes (Association): A WeeklyMealPlan has a Map associating a String (day of the week) with a list of Recipe objects.
belongsTo User (Composition): A WeeklyMealPlan is created by a User.

## Comment class:
belongsTo User (Association): A Comment has a User object as the author.
belongsTo Recipe (Composition): A Comment is part of a Recipe.




## controller
## RecipeController:
interactsWith Recipe (Association): The RecipeController manages interactions and operations related to Recipe objects.

## IngredientController:
interactsWith Ingredient (Association): The IngredientController manages interactions and operations related to Ingredient objects.

## UserController:
interactsWith User (Association): The UserController manages interactions and operations related to User objects.

## TagController:
interactsWith Tag (Association): The TagController manages interactions and operations related to Tag objects.

## WeeklyMealPlanController:
interactsWith WeeklyMealPlan (Association): The WeeklyMealPlanController manages interactions and operations related to WeeklyMealPlan objects.

## CommentController:
interactsWith Comment (Association): The CommentController manages interactions and operations related to Comment objects. 



## view
