package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeaShopDatabase {
    static class Product {
        String name;
        double price;

        Product(String name, double price) {
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return name + " - " + price + " Tenge";
        }
    }


    public static List<Product> getTeasFromDatabase() {
        List<Product> teas = new ArrayList<>();
        // Параметры подключения
        String url = "jdbc:postgresql://localhost:5432/AngelsOOP";
        String user = "postgres";
        String password = "Avangard21885.";

        // SQL запрос для получения всех чаев
        String sql = "SELECT name, price FROM products";

        // Подключение к базе данных
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Извлечение данных из ResultSet
            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                teas.add(new Product(name, price));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teas;
    }

    // Основной метод для запуска приложения
    public static void main(String[] args) {

        // Получаем список чаев из базы данных
        List<Product> teas = getTeasFromDatabase();

        // Выводим список чаев
        System.out.println("List of Teas:");
        for (Product tea : teas) {
            System.out.println(tea);
        }
    }
}
