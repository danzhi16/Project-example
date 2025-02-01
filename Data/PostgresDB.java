package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import Data.Interfaces.IDB;

public abstract class PostgresDB implements IDB {
    private String host;
    private String username;
    private String password;
    private String dbname;
    private Connection connection;

    public PostgresDB(String host, String username, String password, String dbname) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.dbname = dbname;
    }


    public Connection connect() {
        String connectionUrl = "jdbc:postgresql://" + host + "/" + dbname;
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
            Class.forName("org.postgresql.Driver");  // Загружаем драйвер
            connection = DriverManager.getConnection(connectionUrl, username, password);
            System.out.println("✅ Successfully connected to database!");
            return connection;
        } catch (ClassNotFoundException e) {
            System.out.println("❌ PostgreSQL Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("✅ Connection closed!");
            } catch (SQLException e) {
                System.out.println("❌ Failed to close database connection: " + e.getMessage());
            }
        }
    }
}
