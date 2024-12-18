package com.elhaouil.Todo_list_app.Service;

import com.elhaouil.Todo_list_app.DTO.UserDetailsDTO;
import com.elhaouil.Todo_list_app.Model.Role;
import com.elhaouil.Todo_list_app.Model.User;
import com.elhaouil.Todo_list_app.Repo.RoleRepo;
import com.elhaouil.Todo_list_app.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void deleteById(long id) {
        userRepo.deleteById(id);
    }

    public List<UserDetailsDTO> getUsers() {
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
        return usersDto;
    }

    public ResponseEntity<UserDetailsDTO> setAdmin(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        Role role = roleRepo.findById(1);
        user.getRoles().add(role);
        UserDetailsDTO userDto = UserDetailsDTO.builder()
                .id(user.getUser_id())
                .roles(user.getRoles()
                        .stream()
                        .map(rl -> rl.getName()).toList())
                .username(username)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
}
