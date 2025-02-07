package service.interfaces;

import java.sql.*;
import java.util.Scanner;

public interface ICustomerService {
    int createCustomer(String name, String email);

    int createCustomer(Connection conn, String name, String email);
}