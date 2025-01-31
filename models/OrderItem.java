package models;

public class OrderItem {
    private int id;
    private int orderId;
    private int productId;
    private int quantity;

    public OrderItem() {
    }

    public OrderItem(int orderId, int productId, int quantity) {
        setOrderId(orderId);
        setProductId(productId);
        setQuantity(quantity);
    }

    public OrderItem(int id, int orderId, int productId, int quantity) {
        this(orderId, productId, quantity);
        this.id = id;
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
