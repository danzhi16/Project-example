package controllers;

import controllers.interfaces.IOrderController;
import models.Order;
import repositories.interfaces.IOrderRepository;

import java.util.List;

public class OrderController implements IOrderController {

    private final IOrderRepository repo;

    public OrderController(IOrderRepository repo) {
        this.repo = repo;
    }

    @Override
    public String createOrder(Order order) {
        if (order == null || order.getUserId() <= 0 || order.getAddress().trim().isEmpty()) {
            return "❌ Error: Invalid order data.";
        }

        boolean created = (boolean) repo.createOrder(order);
        return created ? "✅ Order successfully created!" : "❌ Error creating order.";
    }

    @Override
    public String getOrderById(int id) {
        Order order = repo.getOrderById(id);
        return (order != null) ? "Order found:\n" + order : "Order not found.";
    }

    @Override
    public String getAllOrders() {
        List<Order> orders = repo.getAllOrders();
        if (orders.isEmpty()) {
            return "No active orders.";
        }

        StringBuilder response = new StringBuilder("Order list:\n");
        for (Order order : orders) {
            response.append(order).append("\n");
        }
        return response.toString();
    }

    @Override
    public String deleteOrder(int id) {
        boolean deleted = repo.deleteOrder(id);
        return deleted ? "✅ Order canceled." : "❌ Error canceling order.";
    }

    public String cancelOrder(int id) {
        return deleteOrder(id);
    }
}
