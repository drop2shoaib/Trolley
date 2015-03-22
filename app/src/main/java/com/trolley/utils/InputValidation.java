package com.trolley.utils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Created by sunny on 22/3/2015.
 */
public class InputValidation {
    public static class ValidationException extends Exception {
        private String message;

        @Override
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public ValidationException(String message) {
            super(message);
            this.message = message;
        }
    }

    public static boolean isEmpty(String text) {
        return text == null || text.trim().length() == 0;
    }

    public static boolean validateName(String name) throws ValidationException {
        if (isEmpty(name))
            throw new ValidationException(CONSTS.SIGNUP_NAME__EMPTY);
        if (name.trim().length() < 3)
            throw new ValidationException(CONSTS.SIGNUP_NOT_VALID_NAME);

        return true;
    }

    public static boolean validatePassword(String password) throws ValidationException {
        if (isEmpty(password))
            throw new ValidationException(CONSTS.SIGNUP_PASSWORD_EMPTY);
        if (password.trim().length() < 6)
            throw new ValidationException(CONSTS.SIGNUP_PASSWORD_LESS_DIGITS);

        return true;
    }

    public static boolean validatePhone(String phone) throws ValidationException {
        if (isEmpty(phone))
            throw new ValidationException(CONSTS.SIGNUP_PHONE_EMPTY);
        if(!phone.matches("((\\+*)((0[ -]+)*|(91 )*)(\\d{12}+|\\d{10}+))|\\d{5}([- ]*)\\d{6}"))
            throw new ValidationException(CONSTS.SIGNUP_NOT_VALID_PHONE);
        return true;
    }

    public static boolean validateEmailPassword(String email) throws ValidationException {
        if (isEmpty(email))
            throw new ValidationException(CONSTS.SIGNUP_EMAIL_EMPTY);
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            throw new ValidationException(CONSTS.SIGNUP_NOT_VALID_EMAIL);

        }
        return true;
    }

    public static boolean validateFlat(String flat) throws ValidationException {
        if (isEmpty(flat))
            throw new ValidationException(CONSTS.SIGNUP_FLAT_EMPTY);
        return true;
    }

    public static boolean validateApartment(String apartment) throws ValidationException {
        if (isEmpty(apartment))
            throw new ValidationException(CONSTS.SIGNUP_APARTMENT_EMPTY);
        return true;
    }

    public static boolean validateLocality(String locality) throws ValidationException {
        if (isEmpty(locality))
            throw new ValidationException(CONSTS.SIGNUP_LOCALITY_EMPTY);
        return true;
    }
}
