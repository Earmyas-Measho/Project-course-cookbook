package model;

public class admin {
    public static user createUser(String username, String displayName, String password) {
        user newUser = new user(username, displayName, password);
        return newUser;
    }

    public static void modifyUser(user userToModify, String newUsername, String newDisplayName, String newPassword) {
        userToModify.setUsername(newUsername);
        userToModify.setDisplayName(newDisplayName);
        userToModify.setPassword(newPassword);
    }

    public static void deleteUser(user userToDelete) {
        user.getUsers().remove(userToDelete);
    }
}
