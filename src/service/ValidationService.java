package service;

import service.interfaces.IValidationService;

public class ValidationService implements IValidationService {

    @Override
    public boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    @Override
    public boolean isValidPrice(double price) {
        return price > 0;
    }
}

