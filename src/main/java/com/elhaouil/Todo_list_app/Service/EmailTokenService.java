package com.elhaouil.Todo_list_app.Service;

import com.elhaouil.Todo_list_app.Model.EmailToken;
import com.elhaouil.Todo_list_app.Model.User;
import com.elhaouil.Todo_list_app.Repo.EmailTokenRepo;
import com.elhaouil.Todo_list_app.Repo.UserRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailTokenService {

    private final EmailTokenRepo emailTokenRepo;
    private final UserRepo userRepo;

    public String generateToken(int length){
        String characters = "0123456789";
        StringBuilder code = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for(int i = 0; i < length; i++){
            int randIndex = secureRandom.nextInt(characters.length());
            code.append(characters.charAt(randIndex));
        }
        return code.toString();
    }

    public String createToken(User user){
        String token = generateToken(6);
        EmailToken emailToken = EmailToken.builder()
                .token(token)
                .generatedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(3))
                .user(user)
                .build();
        emailTokenRepo.save(emailToken);
        return token;
    }

    public boolean validateToken(String token){
       EmailToken emailToken = emailTokenRepo.findByToken(token)
               .orElseThrow(() -> new IllegalStateException("Invalid Token"));

       User user = emailToken.getUser();

       if(emailToken.getExpiresAt().isAfter(LocalDateTime.now())){
           emailToken.setValidateAt(LocalDateTime.now());
           user.setEnabled(true);
           userRepo.save(user);
           emailTokenRepo.save(emailToken);
//           emailTokenRepo.delete(emailToken);
           return true;
       }
//       emailTokenRepo.delete(emailToken);
       return false;

    }

}
