import Data.Interfaces.IDB;
import Data.PostgresDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Data.Interfaces.IDB.*;
import static Data.PostgresDB.*;

class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/AngelsOOP";
    private static final String USER = "postgres";
    private static final String PASSWORD = "290607";


    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}


class Product {
    int productId;
    String name;
    double price;

    public Product(int productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }
}



class Cart {
    List<Product> products = new ArrayList<>();
    double totalAmount = 0;

    void addProduct(Product product) {
        products.add(product);
        totalAmount += product.price;
    }

    void displayCart() {
        System.out.println("Your Cart:");
        for (Product p : products) {
            System.out.println(p.name + " - " + p.price + " Tenge");
        }
        System.out.println("Total: " + totalAmount + " Tenge");
    }

    double applyDiscount() {
        if (totalAmount > 10000) {
            return totalAmount * 0.85;  // Apply 15% discount
        }
        return totalAmount;
    }
}

class Shop {
    static List<Product> fetchProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection conn = Database.connect()) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT product_id, name, price FROM products")) {
                    while (rs.next()) {
                        products.add(new Product(rs.getInt("product_id"), rs.getString("name"), rs.getDouble("price")));
                        rs.getInt("product_id");
                        rs.getString("name");
                        rs.getDouble("price");;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    static void placeOrder(int customerId, Cart cart) {
        String orderQuery = "INSERT INTO orders (customer_id, total_amount, status) VALUES (?, ?, 'Pending') RETURNING order_id";
        String orderItemQuery = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = IDB.connect();
             PreparedStatement orderStmt = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS)) {

            double total = cart.applyDiscount();
            orderStmt.setInt(1, customerId);
            orderStmt.setDouble(2, total);
            orderStmt.executeUpdate();

            ResultSet rs = orderStmt.getGeneratedKeys();
            if (rs.next()) {
                int orderId = rs.getInt(1);
                try (PreparedStatement orderItemStmt = conn.prepareStatement(orderItemQuery)) {
                    for (Product p : cart.products) {
                        orderItemStmt.setInt(1, orderId);
                        orderItemStmt.setInt(2, p.productId);
                        orderItemStmt.setInt(3, 1);
                        orderItemStmt.setDouble(4, p.price);
                        orderItemStmt.executeUpdate();
                    }
                }
                System.out.println("Order placed successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        IDB db = new PostgresDB("jdbc:postgresql://localhost:5432", "postgres", "12345678", "AngelsOOP") {
            @Override
            public Connection getConnection() {
                return null;
            }

            @Override
            public void closeConnection() {

            }
        };

        try (Connection conn = IDB.connect()) {
            if (conn != null) {
                System.out.println("🚀 Database connection successful!");
            } else {
                System.out.println("❌ Database connection failed!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Database connection error: " + e.getMessage());
            e.printStackTrace();
        }

        // Дальше продолжаем работу программы
        Scanner scanner = new Scanner(System.in);
        System.out.println("Mystic Infusions – Discover the magic of tea through unique blends and timeless traditions.");

        System.out.println("Enter your name:");
        String customerName = scanner.nextLine();  // Заменил на nextLine, так как name обычно строка

        System.out.println("Enter your email:");
        String customerEmail = scanner.nextLine();

        System.out.println("Enter your customer ID:");
        int customerId = scanner.nextInt();  // Declare customerId here
        scanner.nextLine();

        Cart cart = new Cart();
        boolean shopping = true;

        while (shopping) {
            System.out.println("1. View Products\n2. Add Product to Cart\n3. View Cart\n4. Checkout\n5. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    List<Product> products = Shop.fetchProducts();
                    for (Product p : products) {
                        System.out.println(p.productId + ". " + p.name + " - " + p.price + " Tenge");
                    }
                    break;
                case 2:
                    System.out.println("Enter Product ID to add:");
                    int productId = scanner.nextInt();
                    products = Shop.fetchProducts();
                    for (Product p : products) {
                        if (p.productId == productId) {
                            cart.addProduct(p);
                            System.out.println(p.name + " added to cart!");
                        }
                    }
                    break;
                case 3:
                    cart.displayCart();
                    break;
                case 4:
                    Shop.placeOrder(customerId, cart);
                    shopping = false;
                    break;
                case 5:
                    shopping = false;
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
        scanner.close();
    }
}