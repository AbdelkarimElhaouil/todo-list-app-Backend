package com.elhaouil.Todo_list_app.Controller;

import com.elhaouil.Todo_list_app.DTO.UserDetailsDTO;
import com.elhaouil.Todo_list_app.DTO.UserSecurityDTO;
import com.elhaouil.Todo_list_app.Exception.UserInvalidRegistration;
import com.elhaouil.Todo_list_app.Model.User;
import com.elhaouil.Todo_list_app.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService service;

    @GetMapping("/login")
    public String home() {
        return "Welcome buddy";
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            service.save(user);
            return ResponseEntity.ok("Registered successfully");
        } catch (UserInvalidRegistration e) {
            throw new UserInvalidRegistration(e.getMessage());
        }

    }


    @PatchMapping("user/{id}")
    public String patchUser(@PathVariable int id, @RequestBody UserSecurityDTO user) {
        return service.patchUser(id, user);
    }

    @PutMapping("user/{id}")
    public String updateUserById(@PathVariable int id, @RequestBody User user) {
        return service.updateById(user);
    }


}
