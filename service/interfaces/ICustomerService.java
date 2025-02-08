package service.interfaces;

import java.sql.*;

public interface ICustomerService {
    int createCustomer(String name, String email);

    int createCustomer(Connection conn, String name, String email);
}