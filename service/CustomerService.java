package service;

import service.interfaces.ICustomerService;

import java.sql.*;
import java.util.Scanner;

public class CustomerService implements ICustomerService {
    @Override
    public int createCustomer(Connection conn, String name, String email) {
        String sql = "INSERT INTO customers (first_name, email) VALUES (?, ?) RETURNING customer_id;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("customer_id"); // Return new customer ID
            }
        } catch (SQLException e) {
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
        String url = "jdbc:postgresql://localhost:5432/tea_shop";
        String user = "postgres";
        String password = "12345678";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected successfully!");

            CustomerService customerService = new CustomerService();
            int customerId = customerService.createCustomer(conn, name, email);
            if (customerId != -1) {
                System.out.println("Welcome, " + name + "! Your customer ID is: " + customerId);
            } else {
                System.out.println("Error creating customer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}