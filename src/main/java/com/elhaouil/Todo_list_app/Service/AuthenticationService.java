package com.elhaouil.Todo_list_app.Service;

import com.elhaouil.Todo_list_app.DTO.UserPatchDTO;
import com.elhaouil.Todo_list_app.DTO.UserRegistrationDTO;
import com.elhaouil.Todo_list_app.Exception.UserInvalidRegistration;
import com.elhaouil.Todo_list_app.Model.Role;
import com.elhaouil.Todo_list_app.Model.User;
import com.elhaouil.Todo_list_app.Repo.RoleRepo;
import com.elhaouil.Todo_list_app.Repo.UserRepo;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final EmailTokenService emailTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailSenderService emailSenderService;

    public  boolean verifyToken(String token) {
        return emailTokenService.validateToken(token);
    }

    public void register(UserRegistrationDTO user) throws MessagingException {

        if(userRepo.existsByUsername(user.getUsername())) {
            throw new UserInvalidRegistration("User is already exist with that username");
        }
        if(userRepo.existsByEmail(user.getEmail())) {
            throw new UserInvalidRegistration("Email is taken");
        }
        Role role = roleRepo.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Roles Not initialized yet"));
        User myUser = User.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .roles(Set.of(role))
                .accountLocked(false)
                .enabled(false)
                .createdDate(LocalDateTime.now())
                .build();
        userRepo.save(myUser);
        String token = emailTokenService.createToken(myUser);
        emailSenderService.sendVerificationEmail(myUser.getEmail(), token);
    }

    public ResponseEntity<String> resetPassword(String email, String newPassword){
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No such User with that email"));
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepo.save(user);
        return ResponseEntity.ok("Password has been changed successfully");
    }

    public ResponseEntity<String> resendCode(String email) {
        if(!UserPatchDTO.checkEmailFormat(email)){
            throw new RuntimeException("Email is not Formatted correctly");
        }
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        String token = emailTokenService.createToken(user);
        emailSenderService.sendVerificationEmail(email, token);
        return ResponseEntity.ok("check your Email");
    }

}
