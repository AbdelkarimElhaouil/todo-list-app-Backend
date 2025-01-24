package com.elhaouil.Todo_list_app.Controller;

import com.elhaouil.Todo_list_app.DTO.UserPatchDTO;
import com.elhaouil.Todo_list_app.DTO.UserRegistrationDTO;
import com.elhaouil.Todo_list_app.Jwt.JwtService;
import com.elhaouil.Todo_list_app.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("User")
public class UserController {

    private final UserService service;
    private final JwtService jwtService;

    @PatchMapping("/patchUser")
    public ResponseEntity<String> patchUser(@RequestBody UserPatchDTO user, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);
        return service.patchUser(user, username);
    }

    @PostMapping("/uploadProfilePicture")
    public ResponseEntity<String> uploadPic(@RequestBody MultipartFile profilePicture){
        return service.savePicture(profilePicture);
    }

    @PutMapping("/user")
    public ResponseEntity<String> updateUser(@RequestBody UserRegistrationDTO user, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);
        return service.updateUser(user, username);
    }


}
