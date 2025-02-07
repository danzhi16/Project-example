package service.interfaces;

public interface IValidationService {
    boolean isValidEmail(String email);
    boolean isValidPrice(double price);
}
