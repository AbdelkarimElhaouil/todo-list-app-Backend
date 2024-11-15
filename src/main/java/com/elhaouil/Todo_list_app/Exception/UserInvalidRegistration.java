package com.elhaouil.Todo_list_app.Exception;

public class UserInvalidRegistration extends RuntimeException{
    public UserInvalidRegistration(String message) {
        super(message);
    }
}
