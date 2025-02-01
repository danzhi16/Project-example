package Data.Interfaces;

import java.sql.Connection;


public interface IDB {
    Connection getConnection();
    void closeConnection();

    static Connection connect() {
        return null;
    }

    void close();
}
