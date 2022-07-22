package com.inventorymanagement.java.utils.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserNameValidation extends FacadeValidator {

    public Boolean isValid(String value) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

}
