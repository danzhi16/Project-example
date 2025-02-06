import controllers.interfaces.IProductController;
import controllers.interfaces.IUserController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MyApplication {
    private final IUserController userController;
    private  final IProductController productController;
    private final Scanner scanner = new Scanner(System.in);
    private boolean isGender;

    public MyApplication(IUserController userController, IProductController productController) {
        this.userController = userController;
        this.productController = productController;
    }

    public void start() {
        while (true) {
            try {
                System.out.println("Welcome to Tea store DB!");
                System.out.println("Select an option:");
                System.out.println("1. Log in as admin");
                System.out.println("2. Log in as user");
                System.out.println("0. Exit");
                int option = scanner.nextInt();
                switch (option) {
                    case 1: AdminMenu(); break;
                    case 2: UserMenu(); break;
                    default: return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be a number.");
                scanner.next(); // Clear the invalid input
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void AdminMenu() {
        System.out.println("Select an option:");
        System.out.println("1. Find User with ID");
        System.out.println("2. Show List of Users");
        System.out.println("3. Delete User");
        System.out.println("0. Exit");
        try {
            int option = scanner.nextInt();
            switch (option) {
                case 1: getUserByIdMenu(); break;
                case 2: getAllUsersMenu(); break;
                case 3: deleteUserMenu(); break;// Fixed this line
                default: return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Input must be a number.");
            scanner.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void UserMenu() {
        System.out.println("Select an option:");
        System.out.println("1. Create a User");
        System.out.println("2. Log in as user");
        System.out.println("0. Exit");
        try {
            int option = scanner.nextInt();
            switch (option) {
                case 1: createUserMenu(); break;
                case 2: loginasUser(); break;
                default: return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Input must be a number.");
            scanner.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void createUserMenu() {
        System.out.println("Please enter a username: ");
        String username = scanner.next();
        System.out.println("Please enter a password: ");
        String password = scanner.next();
        System.out.println("Please enter a name: ");
        String name = scanner.next();
        System.out.println("Please enter a surname: ");
        String surname = scanner.next();
        System.out.println("Please enter a gender (male/female): ");
        String gender = scanner.next();

        if(gender.equalsIgnoreCase("male")){
            isGender = true;
        } else if (gender.equalsIgnoreCase("f")) {
            isGender = false;
        } else {
            System.out.println("Invalid gender");
            return;
        }

        String response = userController.createUser(username, password, name, surname, gender);
        System.out.println(response);
    }

    private void loginasUser() {
        System.out.println("Enter your username: ");
        String username = scanner.next();
        System.out.println("Enter your password: ");
        String password = scanner.next();

        boolean isLoggedIn = userController.loginUser(username, password);

        if (isLoggedIn) {
            System.out.println("Login successful! Welcome, " + username);
            UserDashboard();
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private void UserDashboard() {
        System.out.println("Welcome to your user dashboard!");
        System.out.println("1. View Profile");
        System.out.println("2. Logout");
        System.out.println("3. List of goods");

        try {
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.println("Fetching user profile... (Feature in progress)");
                    break;
                case 2:
                    System.out.println("Logging out...");
                    return;
                case 3:
                    getAllProducts(); break;
                default:
                    System.out.println("Invalid option.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input must be a number.");
            scanner.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void getUserByIdMenu() {
        System.out.println("Enter user ID: ");
        try {
            int id = scanner.nextInt();
            String response = userController.getUserById(id);
            System.out.println(response);
        } catch (InputMismatchException e) {
            System.out.println("ID must be a number.");
            scanner.next();
        }
    }

    private void getAllUsersMenu() {
        String response = userController.getAllUsers();
        System.out.println(response);
    }

    private void deleteUserMenu() {
        System.out.println("Enter user ID to delete: ");
        try {
            int id = scanner.nextInt();
            String response = userController.deleteUser(id);
            System.out.println(response);
        } catch (InputMismatchException e) {
            System.out.println("ID must be a number.");
            scanner.next();
        }
    }
    private void getAllProducts(){
        String response = productController.getAllProducts();
        System.out.println(response);
    }

    private void mainMenu() {
        System.out.println();
        System.out.println("Welcome to Tea store DB!");
        System.out.println("Select an option:");
        System.out.println("1. Get all users");
        System.out.println("2. Get user by ID");
        System.out.println("3. Create a new user");
        System.out.println("4. Delete user");
        System.out.println("5. List of goods (Feature in progress)");
        System.out.println("0. Exit");
        System.out.print("Enter option (0-5): ");
    }
}