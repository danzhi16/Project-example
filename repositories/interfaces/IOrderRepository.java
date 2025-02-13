package repositories.interfaces;

import models.Order;
import java.util.List;

public interface IOrderRepository {
    Object createOrder(Order order);
    Order getOrderById(int id);
    List<Order> getAllOrders();
    boolean updateOrder(Order order);
    boolean deleteOrder(int id);
}
