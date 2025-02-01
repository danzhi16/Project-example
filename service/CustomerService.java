package service;
import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Data.Interfaces.IDB;
import Data.PostgresDB;

import static java.sql.DriverManager.getConnection;

public class CustomerService {
    public static int createCustomer(Connection conn, String name, String email) {
        String sql = "INSERT INTO customers (first_name, email) VALUES (?, ?) RETURNING customer_id;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("customer_id"); // Return new customer ID
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Error case
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Enter your email: ");
        String email = scanner.nextLine();

        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "290607";



        // Declare conn outside try block
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected successfully!");

            // Now use conn inside the same try block
            int customerId = createCustomer(conn, name, email);
            if (customerId != -1) {
                System.out.println("Welcome, " + name + "! Your customer ID is: " + customerId);
            } else {
                System.out.println("Error creating customer.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e); // Optional: Stop execution on error
        }
    }

    public static int createCustomer(Connection conn, Console_Application.Customer customer) {
        // Normally, you'd execute an SQL INSERT query here
        System.out.println("Creating customer: " + customer.getName());
        return 1; // Simulated customer ID
    }

}





