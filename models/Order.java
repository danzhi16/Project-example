package models;

import java.util.Date;

public class Order {
    private int id;
    private Date date;
    private int userId;
    private String address;

    public Order() {
    }

    public Order(Date date, int userId, String address) {
        setDate(date);
        setUserId(userId);
        setAddress(address);
    }

    public Order(int id, Date date, int userId, String address) {
        this(date, userId, address);
        this.id = id;
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
