package controllers.interfaces;

import models.Order;

public interface IOrderController {
    String createOrder(Order order);
    String getOrderById(int id);
    String getAllOrders();
    String deleteOrder(int id);
}
