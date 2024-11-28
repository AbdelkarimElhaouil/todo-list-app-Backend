package com.elhaouil.Todo_list_app.Service;

import com.elhaouil.Todo_list_app.DTO.UserRegistrationDTO;
import com.elhaouil.Todo_list_app.Model.User;
import com.elhaouil.Todo_list_app.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


public String patchUser(int id, UserRegistrationDTO newUser) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
    User existingUser = userRepo.findById(id);
    if (existingUser == null) {
        return "User Not Found";
    }


    if (newUser.getUsername() == null) {
        existingUser.setPassword(encoder.encode(newUser.getPassword()));
        userRepo.save(existingUser);
        return "Updated Successfully";
    } else if (!newUser.getUsername().equals(existingUser.getUsername()) &&
            userRepo.findByUsername(newUser)) { // findByUsername returns bool(Overloaded method)
        existingUser.setUsername(newUser.getUsername());
        if (newUser.getPassword() != null) {
            newUser.setPassword(encoder.encode(newUser.getPassword()));
        }
        userRepo.save(existingUser);
        return "Updated Successfully";
    }

    return "Invalid, the new Username is already exist.";
}

//********************Update By Id **************************

public String updateById(User user) {

    User user1 = userRepo.findById(user.getUser_id());

    try {
        if (user1 == null)
            throw new Exception("Invalid, This user is not exist.");
    } catch (Exception e) {
        return e.getMessage();
    }

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);

    if (user.getUsername() == null) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        return "Updated Successfully";
    } else if (!user.getUsername().equals(user1.getUsername()) &&
            userRepo.findByUsername(user.getUsername()) == null) {

        if (user.getPassword() != null) {
            user.setPassword(encoder.encode(user.getPassword()));
        }

        userRepo.save(user);
        return "Updated Successfully";
    }

    user.setPassword(encoder.encode(user.getPassword()));
    userRepo.save(user);
    return "The User Updated Successfully";
    }
}
