package com.elhaouil.Todo_list_app.Controller;

import com.elhaouil.Todo_list_app.DTO.UserDetailsDTO;
import com.elhaouil.Todo_list_app.Model.User;
import com.elhaouil.Todo_list_app.Service.AdminService;
import com.elhaouil.Todo_list_app.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService service;

    @GetMapping("users")
    public List<UserDetailsDTO> getUsers() {
        return service.getUsers();
    }

    @PostMapping("setAdmin/{username}")
    public ResponseEntity<UserDetailsDTO> setRole(@PathVariable String username) {
        return service.setAdmin(username);
    }

    @DeleteMapping("user/{id}")
    public void deleteById(@PathVariable long id) {
        service.deleteById(id);
    }

}