package com.elhaouil.Todo_list_app.DTO;

import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class UserPatchDTO {
    private String username;
    private String password;
    private String email;

    public boolean checkEmailFormat(String email){
        if (email == null || email.isBlank()) return false;
        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@gmail.com$";
        Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }
}
