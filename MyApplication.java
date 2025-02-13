import controllers.interfaces.IProductController;
import controllers.interfaces.IUserController;
import controllers.interfaces.ICategoryController;

import java.util.InputMismatchException;
import java.util.Scanner;

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
            System.out.println("Welcome! Choose an option:");
            System.out.println("1. Register");
            System.out.println("2. Sign In");
            System.out.println("0. Exit");

            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1 -> registerUser();
                    case 2 -> loginUser();
                    case 0 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("❌ Invalid option. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Error: Enter a number.");
                scanner.nextLine();
            }
        }
    }

    private void registerUser() {
        System.out.print("Enter role (CUSTOMER, SELLER, ADMIN): ");
        String role = scanner.nextLine().trim().toUpperCase();

        String adminPassword = "";
        if ("ADMIN".equals(role)) {
            System.out.print("Enter admin password: ");
            adminPassword = scanner.nextLine().trim();
        }

        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        String response = userController.createUser(username, password, email, role, adminPassword);
        System.out.println(response);
    }

    private void loginUser() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();

        boolean isLoggedIn = userController.loginUser(username, password);
        if (isLoggedIn) {
            System.out.println("Login successful! Welcome, " + username);
            String role = userController.getUserRole(username);
            switch (role.toUpperCase()) {
                case "ADMIN" -> adminMenu();
                case "SELLER" -> sellerMenu();
                case "CUSTOMER" -> customerMenu();
                default -> System.out.println("❌ Error: Unknown role.");
            }
        } else {
            System.out.println("❌ Incorrect username or password.");
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View all users");
            System.out.println("2. Find user by ID");
            System.out.println("3. Delete user");
            System.out.println("4. View all products");
            System.out.println("0. Logout");

            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1 -> System.out.println(userController.getAllUsers());
                    case 2 -> getUserById();
                    case 3 -> deleteUser();
                    case 4 -> System.out.println(productController.getAllProducts());
                    case 0 -> {
                        System.out.println("Logging out...");
                        return;
                    }
                    default -> System.out.println("❌ Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Error: Enter a number.");
                scanner.nextLine();
            }
        }
    }

    private void sellerMenu() {
        while (true) {
            System.out.println("\nSeller Menu:");
            System.out.println("1. View Your Products");
            System.out.println("2. Add New Product");
            System.out.println("0. Logout");

            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1 -> System.out.println(productController.getSellerProducts());
                    case 2 -> addNewProduct();
                    case 0 -> {
                        System.out.println("Logging out...");
                        return;
                    }
                    default -> System.out.println("❌ Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Error: Enter a number.");
                scanner.nextLine();
            }
        }
    }

    private void customerMenu() {
        while (true) {
            System.out.println("\n🛒 Customer Menu:");
            System.out.println("1. View Cart");
            System.out.println("2. View Categories");
            System.out.println("3. Browse Products by Category");
            System.out.println("0. Logout");

            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1 -> models.Cart.viewCart();
                    case 2 -> System.out.println(categoryController.getAllCategories());
                    case 3 -> browseProducts();
                    case 0 -> {
                        System.out.println("🚪 Logging out...");
                        return;
                    }
                    default -> System.out.println("❌ Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Error: Enter a number.");
                scanner.nextLine();
            }
        }
    }

    private void browseProducts() {
        System.out.println("Enter category ID:");
        System.out.println(categoryController.getAllCategories());
        try {
            int categoryId = scanner.nextInt();
            scanner.nextLine();
            System.out.println(productController.getProductsByCategory(categoryId));

            System.out.println("\nWould you like to add a product to your cart? (yes/no)");
            String response = scanner.nextLine().trim().toLowerCase();
            if (response.equals("yes")) {
                models.Cart.addToCart();
            }
        } catch (InputMismatchException e) {
            System.out.println("❌ Error: ID must be a number.");
            scanner.nextLine();
        }
    }

    private void addNewProduct() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price;
        try {
            price = scanner.nextDouble();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("❌ Error: Price must be a number.");
            scanner.nextLine();
            return;
        }
        System.out.print("Enter product category: ");
        String category = scanner.nextLine();

        String response = productController.addProduct(name, price, category);
        System.out.println(response);
    }

    private void viewProfile() {
        System.out.println("Profile view is under development...");
    }

    private void getUserById() {
        System.out.print("Enter user ID: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.println(userController.getUserById(id));
        } catch (InputMismatchException e) {
            System.out.println("❌ Error: ID must be a number.");
            scanner.nextLine();
        }
    }

    private void deleteUser() {
        System.out.print("Enter user ID to delete: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.println(userController.deleteUser(id));
        } catch (InputMismatchException e) {
            System.out.println("❌ Error: ID must be a number.");
            scanner.nextLine();
        }
    }


}
