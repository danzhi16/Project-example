package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Console_Application {
    static class Product {
        String name;
        double price;

        Product(String name, double price) {
            this.name = name;
            this.price = price;
        }
    }

    // Класс для представления покупателя
    static class Customer {
        int customeriId = 0;
        String name;
        String email;
        double totalAmount;

        public Customer(String name, String email, double totalAmount) {
            this.name = name;
            this.email = email;
            this.totalAmount = totalAmount;

        }

        Customer(int customeriId, String name, String email, double totalAmount) {
            this.customeriId = customeriId;
            this.name = name;
            this.email = email;
            this.totalAmount = totalAmount;
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
        public int getCustomeriId() {
            return customeriId;
        }
        void setCustomeriId(int customeriId) {
            this.customeriId = customeriId;
        }
        void setName(String name) {
            this.name = name;
        }
        void setEmail(String email) {
            this.email = email;
        }


        void addAmount(double amount) {
            this.totalAmount += amount;
        }

        void addToCart(double amount) {
            this.totalAmount += amount;
        }

        double applyDiscount() {
            // Применение скидки 15%, если сумма больше 10000
            if (this.totalAmount > 10000) {
                return this.totalAmount * 0.85; // 15% discount
            }
            return this.totalAmount;
        }
    }

    // Класс для управления корзиной покупок
    static class ShoppingCart {
        List<Product> cart;

        ShoppingCart() {
            cart = new ArrayList<>();
        }

        void addProduct(Product product) {
            cart.add(product);
        }

        void removeProduct(Product product) {
            cart.remove(product);
        }

        double calculateTotal() {
            double total = 0;
            for (Product p : cart) {
                total += p.price;
            }
            return total;
        }

        void clearCart() {
            cart.clear();
        }

        String viewCart() {
            StringBuilder cartDetails = new StringBuilder("Products in Cart:\n");
            for (Product p : cart) {
                cartDetails.append(p.name).append(" - ").append(p.price).append(" Tenge\n");
            }
            cartDetails.append("Total: ").append(calculateTotal()).append(" Tenge");
            return cartDetails.toString();
        }
    }


    public static void main(String[] args) {

        // Пример товаров
        Product tea1 = new Product("Darjeeling Tea", 1200);
        Product tea2 = new Product("Assam Tea", 1500);
        Product tea3 = new Product("Dian Hong", 2000);

        // Создание покупателя
        Customer customer = null;
        ShoppingCart cart = new ShoppingCart();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the The Tea Heaven!");
        System.out.println("In our shop we have over 70 types of tea from different countries, so we hope everyone can find their favorite ones!");
        System.out.println("Instructions are quite simple, but if you need any help with order, please contact support");
        System.out.println("Please enter your name:");
        String name = scanner.nextLine();
        System.out.println("Please enter your email:");
        String email = scanner.nextLine();

        customer = new Customer(customer.customeriId, name, email, customer.totalAmount);

        boolean shopping = true;

        while (shopping) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. View Products");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Checkout");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline character

            switch (choice) {
                case 1:
                    System.out.println("Available Products:");
                    System.out.println("1. Darjeeling Tea - 1200 Tenge");
                    System.out.println("2. Assam Tea - 1500 Tenge");
                    System.out.println("3. Dian Hong - 2000 Tenge");
                    break;

                case 2:
                    System.out.println("Which product would you like to add to your cart?");
                    System.out.println("1. Darjeeling Tea - 1200 Tenge");
                    System.out.println("2. Assam Tea - 1500 Tenge");
                    System.out.println("3. Dian Hong - 2000 Tenge");
                    int productChoice = scanner.nextInt();

                    if (productChoice == 1) {
                        cart.addProduct(tea1);
                        customer.addToCart(tea1.price);
                        System.out.println("Added Darjeeling Tea to cart.");
                    } else if (productChoice == 2) {
                        cart.addProduct(tea2);
                        customer.addToCart(tea2.price);
                        System.out.println("Added Assam Tea to cart.");
                    } else if (productChoice == 3) {
                        cart.addProduct(tea3);
                        customer.addToCart(tea3.price);
                        System.out.println("Added Dian Hong to cart.");
                    } else {
                        System.out.println("Invalid choice.");
                    }
                    break;

                case 3:
                    System.out.println(cart.viewCart());
                    break;

                case 4:
                    double totalAmountAfterDiscount = customer.applyDiscount();
                    System.out.println("Total amount after discount (if applicable): " + totalAmountAfterDiscount + " Tenge");
                    cart.clearCart(); // Clear the cart after checkout
                    customer.totalAmount = 0; // Reset total amount after checkout
                    System.out.println("Thank you for your purchase!");
                    shopping = false; // End the shopping session
                    break;

                case 5:
                    shopping = false; // Exit the shopping session
                    System.out.println("Thank you for visiting the Tea Shop!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
        scanner.close();
    }

}
