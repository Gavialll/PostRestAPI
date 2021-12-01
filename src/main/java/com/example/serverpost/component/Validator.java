package com.example.serverpost.component;

public class Validator {

    public static boolean telNumber(String telNumber){
        return (telNumber.matches("^\\+[\\(\\-]?(\\d[\\(\\)\\-]?){11}\\d$") ||
                telNumber.matches("^\\(?(\\d[\\-\\(\\)]?){9}\\d$")) &&
                telNumber.matches("[\\+]?\\d*(\\(\\d{3}\\))?\\d*\\-?\\d*\\-?\\d*\\d$");
    }

    public static boolean email(String email){
        return (email.matches("[a-zA-Z0-9]{1,}[@]{1}[a-z]{5,}[.]{1}+[a-z]{3}"));
    }
}
