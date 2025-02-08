package models.interfaces;

import models.OrderItem;
import java.util.List;

public interface IOrderItem {
    OrderItem createOrderItem(int orderId, int productId, int quantity);
    OrderItem getOrderItemById(int id);
    List<OrderItem> getAllOrderItems();
    boolean updateOrderItem(int id, int orderId, int productId, int quantity);
    boolean deleteOrderItem(int id);
}
