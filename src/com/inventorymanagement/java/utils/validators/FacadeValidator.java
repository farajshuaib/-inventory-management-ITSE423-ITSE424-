
package com.inventorymanagement.java.utils.validators;

public class FacadeValidator {

    EmailValidation    emailValidation;
    UserNameValidation userNameValidation;


    FacadeValidator(){}

    public FacadeValidator(EmailValidation emailValidation, UserNameValidation userNameValidation ){
        this.emailValidation = emailValidation;
        this.userNameValidation = userNameValidation;

    }


    public  Boolean isEmailValid(String value) {
        return emailValidation.isValid(value);
    }


    public  Boolean isUserNameValid(String value) {
        return userNameValidation.isValid(value);
    }


    public  Boolean isDouble(String value) {
        char[] letters = value.toCharArray();

        // check if empty
        if (letters.length < 1)
            return false;

        for (char letter : letters) {
            if (Double.isNaN(letter)) {
                return true;
            }
//            if (!Character.isDigit(letter)) {
//                return false;
//            }
        }
        return true;
    }

    public  Boolean isNumber(String value) {
        char[] letters = value.toCharArray();

        // check if empty
        if (letters.length < 1)
            return false;

        for (char letter : letters) {
            if (!Character.isDigit(letter)) {
                return false;
            }
        }
        return true;
    }



}