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

import java.io.IOException;

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
    public ResponseEntity<String> uploadPic(@RequestParam("file") MultipartFile profilePicture, HttpServletRequest request) throws IOException {
        String token = request.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);
        return service.savePicture(profilePicture, username);
    }

    @GetMapping("/getProfilePic")
    public ResponseEntity<?> displayProfilePicture(HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);
        return service.displayPicture(username);
    }

    @PutMapping("/user")
    public ResponseEntity<String> updateUser(@RequestBody UserRegistrationDTO user, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);
        return service.updateUser(user, username);
    }


}
