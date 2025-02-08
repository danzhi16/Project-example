import controllers.interfaces.IProductController;
import controllers.interfaces.IUserController;
import controllers.interfaces.ICategoryController;
import models.Category;
import models.Product;
import java.util.*;

public class MyApplication {
    private final IUserController userController;
    private final IProductController productController;
    private final ICategoryController categoryController;
    private final Scanner scanner = new Scanner(System.in);

    public MyApplication(IUserController userController, IProductController productController, ICategoryController categoryController) {
        this.userController = userController;
        this.productController = productController;
        this.categoryController = categoryController;
    }

    public void start() {
        while (true) {
            System.out.println("Welcome! Please select an option:");
            System.out.println("1. Register");
            System.out.println("2. Sign In");
            System.out.println("0. Exit");

            try {
                int option = scanner.nextInt();
                switch (option) {
                    case 1: registerUser(); break;
                    case 2: loginUser(); break;
                    case 0: return;
                    default: System.out.println("Invalid option. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be a number.");
                scanner.next();
            }
        }
    }

    private void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();
        System.out.print("Enter email: ");
        String email = scanner.next();
        System.out.print("Enter role (CUSTOMER, SELLER, or ADMIN): ");
        String role = scanner.next().toUpperCase();

        String response = userController.createUser(username, password, email, role);
        System.out.println(response);
    }

    private void loginUser() {
        System.out.print("Enter your username: ");
        String username = scanner.next();
        System.out.print("Enter your password: ");
        String password = scanner.next();

        boolean isLoggedIn = userController.loginUser(username, password);
        if (isLoggedIn) {
            System.out.println("Login successful! Welcome, " + username);
            String role = userController.getUserRole(username);
            if (role.equalsIgnoreCase("ADMIN")) {
                adminMenu();
            } else if (role.equalsIgnoreCase("SELLER")) {
                sellerMenu();
            } else {
                customerMenu();
            }
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Get all users");
            System.out.println("2. Get user by ID");
            System.out.println("3. Delete user");
            System.out.println("4. View all products");
            System.out.println("0. Logout");

            try {
                int option = scanner.nextInt();
                switch (option) {
                    case 1: System.out.println(userController.getAllUsers()); break;
                    case 2: getUserById(); break;
                    case 3: deleteUser(); break;
                    case 4: System.out.println(productController.getAllProducts()); break;
                    case 0: return;
                    default: System.out.println("Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be a number.");
                scanner.next();
            }
        }
    }
    private void sellerMenu() {
        while (true) {
            System.out.println("\nSeller Menu:");
            System.out.println("1. View Your Products");
            System.out.println("2. Add New Product");
            System.out.println("3. Logout");

            try {
                int option = scanner.nextInt();
                switch (option) {
                    case 1: System.out.println(productController.getSellerProducts()); break;
                    case 2: System.out.println("Feature in progress: Adding a new product"); break;
                    case 3: return;
                    default: System.out.println("Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be a number.");
                scanner.next();
            }
        }
    }

    private void customerMenu() {
        while (true) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. View Profile");
            System.out.println("2. View Categories");
            System.out.println("3. Browse Products");
            System.out.println("4. Logout");

            try {
                int option = scanner.nextInt();
                switch (option) {
                    case 1: System.out.println("Fetching user profile... (Feature in progress)"); break;
                    case 2: System.out.println(categoryController.getAllCategories()); break;
                    case 3: browseProducts(); break;
                    case 4: return;
                    default: System.out.println("Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be a number.");
                scanner.next();
            }
        }
    }

    private void browseProducts() {
        System.out.println("Select a category by ID to view products: ");
        System.out.println(categoryController.getAllCategories());

        try {
            int categoryId = scanner.nextInt();
            System.out.println(productController.getProductById(categoryId));
        } catch (InputMismatchException e) {
            System.out.println("ID must be a number.");
            scanner.next();
        }
    }

    private void getUserById() {
        System.out.print("Enter user ID: ");
        try {
            int id = scanner.nextInt();
            System.out.println(userController.getUserById(id));
        } catch (InputMismatchException e) {
            System.out.println("ID must be a number.");
            scanner.next();
        }
    }

    private void deleteUser() {
        System.out.print("Enter user ID to delete: ");
        try {
            int id = scanner.nextInt();
            System.out.println(userController.deleteUser(id));
        } catch (InputMismatchException e) {
            System.out.println("ID must be a number.");
            scanner.next();
        }
    }
}