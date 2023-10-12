
public class admin {
    public static User createUser(String username, String displayName, String password) {
        User newUser = new User(username, displayName, password);
        return newUser;
    }

    public static void modifyUser(User userToModify, String newUsername, String newDisplayName, String newPassword) {
        userToModify.setUsername(newUsername);
        userToModify.setDisplayName(newDisplayName);
        userToModify.setPassword(newPassword);
    }

    public static void deleteUser(User userToDelete) {
        User.getUsers().remove(userToDelete);
    }
}
