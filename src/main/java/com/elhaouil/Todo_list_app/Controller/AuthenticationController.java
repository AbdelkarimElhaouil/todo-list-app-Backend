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

    @PostMapping("reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> userCred){
        String token = userCred.get("token");
        String email = userCred.get("email");
        String password = userCred.get("password");
        if(authenticationService.verifyToken(token)){
            return authenticationService.resetPassword(email, password);
        }
        return ResponseEntity.badRequest().body("something went wrong");
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestBody Map<String, String> token){
        String extractedToken = token.get("token");
        if(authenticationService.verifyToken(extractedToken)){
            return ResponseEntity.ok("Account Verified successfully");
        }
        return ResponseEntity.badRequest().body("Invalid verification");
    }

    @PostMapping("/resend-code")
    public ResponseEntity<?> resendCode(@RequestBody Map<String, String> email){
        String extractedEmail = email.get("email");
        return authenticationService.resendCode(extractedEmail);
    }
}
