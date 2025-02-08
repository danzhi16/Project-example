package controllers.interfaces;

public interface IUserController {
    String createUser(String username, String password, String gender, String role);
    String getUserById(int id);
    String getAllUsers();
    String deleteUser(int id);
    boolean loginUser(String username, String password);
}