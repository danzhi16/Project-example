package models;

import models.interfaces.IOrderItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderItem implements IOrderItem {
    private static List<OrderItem> orderItems = new ArrayList<>();
    private static int idCounter = 1;

    private int id;
    private int orderId;
    private int productId;
    private int quantity;

    public OrderItem() {}

    public OrderItem(int orderId, int productId, int quantity) {
        this.id = idCounter++;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        orderItems.add(this);
    }

    public OrderItem(int id, int orderId, int productId, int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    @Override
    public OrderItem createOrderItem(int orderId, int productId, int quantity) {
        return new OrderItem(orderId, productId, quantity);
    }

    @Override
    public OrderItem getOrderItemById(int id) {
        Optional<OrderItem> orderItem = orderItems.stream().filter(o -> o.getId() == id).findFirst();
        return orderItem.orElse(null);
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return new ArrayList<>(orderItems);
    }

    @Override
    public boolean updateOrderItem(int id, int orderId, int productId, int quantity) {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getId() == id) {
                orderItem.setOrderId(orderId);
                orderItem.setProductId(productId);
                orderItem.setQuantity(quantity);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteOrderItem(int id) {
        return orderItems.removeIf(orderItem -> orderItem.getId() == id);
    }

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
