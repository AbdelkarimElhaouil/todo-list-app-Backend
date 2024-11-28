package com.elhaouil.Todo_list_app.Controller;

import com.elhaouil.Todo_list_app.DTO.UserRegistrationDTO;
import com.elhaouil.Todo_list_app.Jwt.JwtService;
import com.elhaouil.Todo_list_app.Model.User;
import com.elhaouil.Todo_list_app.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<String> home(@RequestBody UserRegistrationDTO user) {
        try {
            Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            if (auth.isAuthenticated())
                return ResponseEntity.ok(jwtService.generateJwt(user.getUsername()));
            else return ResponseEntity.status(401).body("UNAUTHORIZED");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("layn3elwaldikalkdab");
        }
    }

    @PatchMapping("user/{id}")
    public String patchUser(@PathVariable int id, @RequestBody UserRegistrationDTO user) {
        return service.patchUser(id, user);
    }

    @PutMapping("user/{id}")
    public String updateUserById(@PathVariable int id, @RequestBody User user) {
        return service.updateById(user);
    }


}
