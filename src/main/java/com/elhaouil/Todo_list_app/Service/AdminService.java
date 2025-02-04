package com.elhaouil.Todo_list_app.Service;

import com.elhaouil.Todo_list_app.DTO.UserDetailsDTO;
import com.elhaouil.Todo_list_app.Model.Role;
import com.elhaouil.Todo_list_app.Model.User;
import com.elhaouil.Todo_list_app.Repo.RoleRepo;
import com.elhaouil.Todo_list_app.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminService {

    public final UserRepo userRepo;
    public final RoleRepo roleRepo;

    public ResponseEntity<?> deleteById(long id) {
        userRepo.deleteById(id);
        return ResponseEntity.ok()
                .body("User with id = " + id + "has been deleted successfully");
    }

    public ResponseEntity<List<UserDetailsDTO>> getUsers() {
        List<User> users = userRepo.findAll();
        List<UserDetailsDTO> usersDto = new ArrayList<>();
        for (User user : users) {
            UserDetailsDTO u = new UserDetailsDTO();
            u.setId(user.getUser_id());
            u.setUsername(user.getUsername());
            u.setRoles(user.getRoles().
                    stream().
                    map(rl -> rl.getName()).toList());
            u.setTasks(user.getTasks()
                    .stream()
                    .map(ts -> ts.getDescription()).toList());
            usersDto.add(u);
        }
        return ResponseEntity.ok()
                .body(usersDto);
    }

    public ResponseEntity<UserDetailsDTO> setAdmin(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        Role role = roleRepo.findById(1); // id = 1 -> admin / id = 2 -> user
        user.getRoles().add(role);
        UserDetailsDTO userDto = UserDetailsDTO.builder()
                .id(user.getUser_id())
                .roles(user.getRoles()
                        .stream()
                        .map(roles -> roles.getName()).toList())
                .username(username)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
}
