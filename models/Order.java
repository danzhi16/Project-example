package models;

import models.interfaces.IOrder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Order implements IOrder {
    private static List<Order> orders = new ArrayList<>();
    private static int idCounter = 1;

    private int id;
    private Date date;
    private int userId;
    private String address;

    public Order() {}

    public Order(Date date, int userId, String address) {
        this.id = idCounter++;
        this.date = date;
        this.userId = userId;
        this.address = address;
        orders.add(this);
    }

    public Order(int id, Date date, int userId, String address) {
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.address = address;
    }

    @Override
    public Order createOrder(Date date, int userId, String address) {
        return new Order(date, userId, address);
    }

    @Override
    public Order getOrderById(int id) {
        Optional<Order> order = orders.stream().filter(o -> o.getId() == id).findFirst();
        return order.orElse(null);
    }

    @Override
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    @Override
    public boolean updateOrder(int id, Date date, int userId, String address) {
        for (Order order : orders) {
            if (order.getId() == id) {
                order.setDate(date);
                order.setUserId(userId);
                order.setAddress(address);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteOrder(int id) {
        return orders.removeIf(order -> order.getId() == id);
    }

    @Override
    public void setId(int anInt) {

    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", userId=" + userId +
                ", address='" + address + '\'' +
                '}';
    }
}