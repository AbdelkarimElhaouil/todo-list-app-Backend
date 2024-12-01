package com.elhaouil.Todo_list_app.Controller;

import com.elhaouil.Todo_list_app.DTO.UserRegistrationDTO;
import com.elhaouil.Todo_list_app.Exception.UserInvalidRegistration;
import com.elhaouil.Todo_list_app.Jwt.JwtService;
import com.elhaouil.Todo_list_app.Service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegistrationDTO user) {
        try {
            authenticationService.register(user);
            return ResponseEntity
                    .ok("Registered successfully, Check Your Email to verify your account");
        } catch (UserInvalidRegistration | MessagingException e) {
            throw new UserInvalidRegistration(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> home(@RequestBody UserRegistrationDTO user) {
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


    @PostMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestBody Map<String, String> token){
        String extractedToken = token.get("token");
        if(authenticationService.verifyToken(extractedToken)){
            return ResponseEntity.ok("Account Verified successfully");
        }
        return ResponseEntity.badRequest().body("Invalid verification");
    }

    @PostMapping("/verification-code")
    public ResponseEntity<?> resendCode(@RequestBody Map<String, String> email){
        try {
            String extractedEmail = email.get("email");
            authenticationService.resendCode(extractedEmail);
            return ResponseEntity.ok("new code sent, Check your email");
        }
        catch (UsernameNotFoundException e){
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
