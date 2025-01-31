package repositories;

import models.Order;
import repositories.interfaces.IOrderRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements IOrderRepository {
    private final List<Order> orders = new ArrayList<>();

    @Override
    public boolean createOrder(Order order) {
        return orders.add(order);
    }

    @Override
    public Order getOrderById(int id) {
        return orders.stream()
                .filter(order -> order.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    @Override
    public boolean deleteOrder(int id) {
        return orders.removeIf(order -> order.getId() == id);
    }
}
