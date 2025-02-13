package models.interfaces;

import models.Order;
import java.util.Date;
import java.util.List;

public interface IOrder {
    Order createOrder(Date date, int userId, String address);
    Order getOrderById(int id);
    List<Order> getAllOrders();
    boolean updateOrder(int id, Date date, int userId, String address);
    boolean deleteOrder(int id);

    void setId(int anInt);
}
