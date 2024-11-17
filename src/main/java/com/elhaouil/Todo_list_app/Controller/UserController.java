package com.elhaouil.Todo_list_app.Controller;

import com.elhaouil.Todo_list_app.DTO.UserDetailsDTO;
import com.elhaouil.Todo_list_app.DTO.UserSecurityDTO;
import com.elhaouil.Todo_list_app.Exception.UserInvalidRegistration;
import com.elhaouil.Todo_list_app.Model.Role;
import com.elhaouil.Todo_list_app.Model.User;
import com.elhaouil.Todo_list_app.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    @Autowired
    UserService service;

    @GetMapping("/login")
    public String home(){
        return  "Welcome buddy";
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody User user){
        try {
            service.save(user);
            return ResponseEntity.ok("Registered successfully");
        }
        catch (UserInvalidRegistration e){
            throw new UserInvalidRegistration(e.getMessage());
        }

    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/userRoles/{id}")
    public Set<Role> getRoles(@PathVariable int id){
        return service.getRoles(id);
    }

    @DeleteMapping("admin/user/{id}")
    public void deleteById(@PathVariable int id){
        service.deleteById(id);
    }

    @PatchMapping("user/{id}")
    public String patchUser(@PathVariable int id, @RequestBody UserSecurityDTO user){
        return service.patchUser(id, user);
    }

    @PutMapping("user/{id}")
    public String updateUserById(@PathVariable int id, @RequestBody User user){
        return service.updateById(user);
    }

    @GetMapping("admin/user")
    public List<UserDetailsDTO> getAllUsers(){
        return service.findAll();
    }

}
