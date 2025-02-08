package service;

import service.interfaces.IConsoleApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleApplication implements IConsoleApplication {

    private final List<Product> products;
    private final Map<Integer, Customer> customers;
    private final Map<Integer, ShoppingCart> carts;
    private int customerIdCounter;

    public ConsoleApplication() {
        products = new ArrayList<>();
        customers = new HashMap<>();
        carts = new HashMap<>();
        customerIdCounter = 1;

        products.add(new Product("Darjeeling Tea", 1200));
        products.add(new Product("Assam Tea", 1500));
        products.add(new Product("Dian Hong", 2000));
    }

    @Override
    public String registerCustomer(String name, String email) {
        Customer newCustomer = new Customer(customerIdCounter++, name, email, 0);
        customers.put(newCustomer.getCustomeriId(), newCustomer);
        carts.put(newCustomer.getCustomeriId(), new ShoppingCart());
        return "Customer registered successfully with ID: " + newCustomer.getCustomeriId();
    }

    @Override
    public String getCustomerDetails(int customerId) {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            return "Customer not found.";
        }
        return "Customer Details: ID: " + customer.getCustomeriId() + ", Name: " + customer.getName() + ", Email: " + customer.getEmail();
    }

    @Override
    public List<String> getAvailableProducts() {
        List<String> productDetails = new ArrayList<>();
        for (Product product : products) {
            productDetails.add(product.name + " - " + product.price + " Tenge");
        }
        return productDetails;
    }

    @Override
    public String addProductToCart(int customerId, String productName) {
        ShoppingCart cart = carts.get(customerId);
        if (cart == null) {
            return "Customer not found.";
        }

        for (Product product : products) {
            if (product.name.equalsIgnoreCase(productName)) {
                cart.addProduct(product);
                Customer customer = customers.get(customerId);
                customer.addToCart(product.price);
                return productName + " added to cart.";
            }
        }
        return "Product not found.";
    }

    @Override
    public String viewCart(int customerId) {
        ShoppingCart cart = carts.get(customerId);
        if (cart == null) {
            return "Customer not found.";
        }
        return cart.viewCart();
    }

    @Override
    public String checkout(int customerId) {
        Customer customer = customers.get(customerId);
        ShoppingCart cart = carts.get(customerId);

        if (customer == null || cart == null) {
            return "Customer not found.";
        }

        double totalAmountAfterDiscount = customer.applyDiscount();
        cart.clearCart();
        customer.totalAmount = 0;
        return "Checkout complete. Total after discount: " + totalAmountAfterDiscount + " Tenge.";
    }

    @Override
    public String exitApplication() {
        return "Thank you for using the application!";
    }

    static class Product {
        String name;
        double price;

        Product(String name, double price) {
            this.name = name;
            this.price = price;
        }
    }

    static class Customer {
        int customeriId;
        String name;
        String email;
        double totalAmount;

        Customer(int customeriId, String name, String email, double totalAmount) {
            this.customeriId = customeriId;
            this.name = name;
            this.email = email;
            this.totalAmount = totalAmount;
        }

        public int getCustomeriId() {
            return customeriId;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        void addToCart(double amount) {
            this.totalAmount += amount;
        }

        double applyDiscount() {
            return totalAmount > 10000 ? totalAmount * 0.85 : totalAmount;
        }
    }

    static class ShoppingCart {
        List<Product> cart;

        ShoppingCart() {
            cart = new ArrayList<>();
        }

        void addProduct(Product product) {
            cart.add(product);
        }

        void clearCart() {
            cart.clear();
        }

        String viewCart() {
            StringBuilder cartDetails = new StringBuilder("Products in Cart:\n");
            for (Product product : cart) {
                cartDetails.append(product.name).append(" - ").append(product.price).append(" Tenge\n");
            }
            cartDetails.append("Total: ").append(calculateTotal()).append(" Tenge");
            return cartDetails.toString();
        }

        double calculateTotal() {
            return cart.stream().mapToDouble(product -> product.price).sum();
        }
    }
}
