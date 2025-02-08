package service.interfaces;

import java.util.List;

public interface IConsoleApplication {

        String registerCustomer(String name, String email);
        String getCustomerDetails(int customerId);

        List<String> getAvailableProducts();
        String addProductToCart(int customerId, String productName);

        String viewCart(int customerId);
        String checkout(int customerId);

        String exitApplication();

}
