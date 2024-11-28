package com.elhaouil.Todo_list_app.Controller;

import com.elhaouil.Todo_list_app.DTO.UserRegistrationDTO;
import com.elhaouil.Todo_list_app.Exception.UserInvalidRegistration;
import com.elhaouil.Todo_list_app.Service.AuthenticationService;
import com.elhaouil.Todo_list_app.Service.EmailTokenService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("account")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final EmailTokenService emailTokenService;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegistrationDTO user) {
        try {
            authenticationService.register(user);
            return ResponseEntity
                    .ok("Registered successfully, Check Your Email to verify your account");
        } catch (UserInvalidRegistration | MessagingException e) {
            throw new UserInvalidRegistration(e.getMessage());
        }
    }


    @PostMapping("verify")
    public ResponseEntity<?> verifyEmail(@RequestBody Map<String, String> token){
        String extractedToken = token.get("token");
        if(authenticationService.verifyToken(extractedToken)){
            return ResponseEntity.ok("Account Verified successfully");
        }
        return ResponseEntity.badRequest().body("Invalid verification");
    }

    @PostMapping("resend-verification-code")
    public ResponseEntity<?> resendCode(@RequestBody String email){
        try {
            authenticationService.resendCode(email);
            return ResponseEntity.ok("new code sent, Check your email");
        }
        catch (UsernameNotFoundException e){
            e.printStackTrace();
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
