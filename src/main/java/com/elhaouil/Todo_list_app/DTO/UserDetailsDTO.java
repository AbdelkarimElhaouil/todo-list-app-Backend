package com.elhaouil.Todo_list_app.DTO;

import com.elhaouil.Todo_list_app.Model.Role;
import com.elhaouil.Todo_list_app.Model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsDTO {
    private long id;
    private String username;
    private List<String> roles;
    private List<String> tasks;
}
