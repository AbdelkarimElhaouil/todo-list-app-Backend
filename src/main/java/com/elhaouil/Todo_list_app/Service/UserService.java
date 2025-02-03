package com.elhaouil.Todo_list_app.Service;

import com.elhaouil.Todo_list_app.DTO.UserPatchDTO;
import com.elhaouil.Todo_list_app.DTO.UserRegistrationDTO;
import com.elhaouil.Todo_list_app.Model.User;
import com.elhaouil.Todo_list_app.Model.UserImage;
import com.elhaouil.Todo_list_app.Repo.UserImageRepo;
import com.elhaouil.Todo_list_app.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final UserImageRepo imageRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public ResponseEntity<String> patchUser(UserPatchDTO newUser, String username) {

        if (newUser == null){
            return ResponseEntity.badRequest().body("User Data is Null");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
        User existingUser = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        boolean isUsernameChanged = !newUser.getUsername().equals(existingUser.getUsername());
        boolean isNewUsernameExist = userRepo.existsByUsername(newUser.getUsername());
        boolean isEmailChanged= !newUser.getEmail().equals(existingUser.getEmail());
        boolean isNewEmailsExist = userRepo.existsByEmail(existingUser.getEmail());

        if(isUsernameChanged && !isNewUsernameExist && newUser.getUsername() != null){
            changeUsername(existingUser, newUser.getUsername());
        }
        if(isEmailChanged && !isNewEmailsExist && newUser.checkEmailFormat(newUser.getEmail())){
            changeEmail(existingUser, newUser.getEmail());
        }

        if(newUser.getPassword() != null && newUser.getPassword().length() < 8){
            changePassword(existingUser, newUser.getPassword());
        }
        return ResponseEntity.ok("Profile updated successfully");
    }

    private void changePassword(User user, String newPassword){
        user.setPassword(newPassword);
        userRepo.save(user);
    }

    private void changeUsername(User user, String newUsername){
        user.setUsername(newUsername);
        userRepo.save(user);
    }

    private void changeEmail(User user, String newEmail){
        user.setEmail(newEmail);
        userRepo.save(user);
    }


//******************** Update By Id **************************

    public ResponseEntity<String> updateUser(UserRegistrationDTO updatedUser, String oldUsername) {

        User user = userRepo.findByUsername(oldUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        boolean isUsernameChanged = !updatedUser.getUsername().equals(oldUsername);
        boolean isNewUsernameExist = userRepo.existsByUsername(updatedUser.getUsername());
        boolean isEmailChanged= !updatedUser.getEmail().equals(user.getEmail());
        boolean isNewEmailsExist = userRepo.existsByEmail(updatedUser.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);

         if (isUsernameChanged && !isNewUsernameExist) {
                 user.setUsername(updatedUser.getUsername());
         }
         else if(isNewUsernameExist){
             throw new InvalidParameterException("The new username is already taken");
         }

         if (isEmailChanged && !isNewEmailsExist){
             user.setEmail(updatedUser.getEmail());
             user.setEnabled(false);
         }
         else if (isNewEmailsExist){
             throw new InvalidParameterException("The new Email is already taken");
         }
         user.setPassword(encoder.encode(updatedUser.getPassword()));
         userRepo.save(user);
         return ResponseEntity.ok("The User Updated Successfully");
    }

    public ResponseEntity<String> savePicture(MultipartFile profilePicture, String username) throws IOException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User 404"));
        if(profilePicture.isEmpty()){
            throw new FileUploadException("File is empty");
        }

        if(profilePicture.getContentType().toLowerCase().contains("jpeg") || profilePicture.getContentType().toLowerCase().contains("png") )
        {
            if(imageRepo.existsByUser(user)){
                imageRepo.deleteByUser(user);
            }
            UserImage userPic = new UserImage();
            userPic.setUser(user);
            userPic.setContentType(profilePicture.getContentType());
            userPic.setImageName(profilePicture.getOriginalFilename());
            userPic.setImageData(profilePicture.getInputStream().readAllBytes());
            imageRepo.save(userPic);
            return ResponseEntity.ok("Profile picture Added Successfully");
        }
        else
            throw new FileUploadException("Image Type is not recommended");

    }

    public ResponseEntity<?> displayPicture(String username) throws FileNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User 404"));
        UserImage image = imageRepo.findByUser(user)
                .orElseThrow(() -> new FileNotFoundException("Profile Picture doesn't exist, You should add a profile picture"));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .body(image.getImageData());
    }

    public ResponseEntity<?> deleteProfile(String username) throws FileNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User 404"));
        if(!imageRepo.existsByUser(user)){
            throw new FileNotFoundException("There is no profile picture to delete");
        }
        imageRepo.deleteByUser(user);
        return ResponseEntity.ok()
                .body("Your profile picture Deleted successfully");

    }
}

