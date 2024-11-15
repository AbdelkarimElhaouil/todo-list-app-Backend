package com.elhaouil.Todo_list_app.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSecurityDTO {
    private String username;
    private String password;
}
