package com.elhaouil.Todo_list_app.Controller;

import com.elhaouil.Todo_list_app.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService service;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
            return service.getUsers();
    }

    @PostMapping("/set-Admin")
    public ResponseEntity<?> setRole(@RequestBody String username) {
        return service.setAdmin(username);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        return service.deleteById(id);
    }

}
