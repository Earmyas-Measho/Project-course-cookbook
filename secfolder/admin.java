package secfolder;
import cookbook.UserEntity;

public class admin {
    public static UserEntity createUser(String username, String password) {
        UserEntity newUser = new UserEntity(username, password);
        return newUser;
    }

    public static void modifyUser(User userToModify, String newUsername, String newDisplayName, String newPassword) {
        userToModify.setUsername(newUsername);
        userToModify.setDisplayName(newDisplayName);
        userToModify.setPassword(newPassword);
    }

   // public static void deleteUser(UserEntity userToDelete) {
     //   UserEntity.getUsers().remove(userToDelete);
    //}
}
