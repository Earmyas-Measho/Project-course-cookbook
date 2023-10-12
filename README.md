# README

## How to get the program running

1. You need to select to the correct referenced libraries in vscode (JavaFX and SQL Connector)
2. You also need to add this line: '"vmArgs": "--module-path C:\\path\\lib --add-modules javafx.controls,javafx.fxml"' to the launch.json file where you replace "C:\\path`\\lib" with the path of the lib folder, which contains the JavaFX libraries.
3. You may also need to change the paths in the code for the images if it still isn't working depending on where you open the project from. The path should lead to the img folder inside of resources: /cookbook/resources/img/


## Other notes

You can access the recipe by dubble clicking the recipename in the weekly dinner list. 
