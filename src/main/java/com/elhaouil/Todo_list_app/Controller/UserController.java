package com.elhaouil.Todo_list_app.Controller;

import com.elhaouil.Todo_list_app.DTO.UserDetailsDTO;
import com.elhaouil.Todo_list_app.DTO.UserSecurityDTO;
import com.elhaouil.Todo_list_app.Exception.UserInvalidRegistration;
import com.elhaouil.Todo_list_app.Jwt.JwtService;
import com.elhaouil.Todo_list_app.Model.User;
import com.elhaouil.Todo_list_app.Service.UserService;
import org.hibernate.resource.transaction.backend.jta.internal.synchronization.ExceptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService service;
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity<String> home(@RequestBody UserSecurityDTO user) {
        try {
            Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            if (auth.isAuthenticated())
                return ResponseEntity.ok(jwtService.generateJwtToken(user.getUsername()));
            else return ResponseEntity.status(401).body("UNAUTHORIZED");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("layn3elwaldikalkdab");
        }
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
