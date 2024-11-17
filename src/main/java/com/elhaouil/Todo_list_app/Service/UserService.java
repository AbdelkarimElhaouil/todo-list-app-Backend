package com.elhaouil.Todo_list_app.Service;

import com.elhaouil.Todo_list_app.DTO.UserDetailsDTO;
import com.elhaouil.Todo_list_app.DTO.UserSecurityDTO;
import com.elhaouil.Todo_list_app.Exception.UserInvalidRegistration;
import com.elhaouil.Todo_list_app.Model.Role;
import com.elhaouil.Todo_list_app.Model.User;
import com.elhaouil.Todo_list_app.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class UserService {
    @Autowired
    UserRepo repo;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(User user){

        try {
            if(user.getUsername() == null){
                throw new UserInvalidRegistration("Cannot Added, username = null");
            }
            else if(user.getPassword() == null){
                throw new UserInvalidRegistration("Cannot Added, password = null");
            }
            else if(repo.existsByUsername(user.getUsername())){
                throw new UserInvalidRegistration("User is Already Exist");
            }
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            repo.save(user);
        }
        catch (UserInvalidRegistration e){
            throw new UserInvalidRegistration(e.getMessage());
        }

    }
    //********************************************//
    public List<UserDetailsDTO> findAll() {
        List<User> users = repo.findAll();
        List<UserDetailsDTO> usersDetails = new ArrayList<>();

        for(User user : users){
            UserDetailsDTO u = new UserDetailsDTO();
            u.setId(user.getUser_id());
            u.setUsername(user.getUsername());
            usersDetails.add(u);
        }

        return usersDetails;
    }

    //********************************************//

    public void deleteById(long id){
        repo.deleteById(id);
    }

    //********************************************//



    //********************************************//

    public String patchUser(int id, UserSecurityDTO newUser) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
        User existingUser = repo.findById(id);
        if(existingUser == null){
            return "User Not Found";
        }


        if(newUser.getUsername() == null){
                existingUser.setPassword(encoder.encode(newUser.getPassword()));
                repo.save(existingUser);
                return "Updated Successfully";
        }
        else if (!newUser.getUsername().equals(existingUser.getUsername()) &&
                repo.findByUsername(newUser)) { // findByUsername returns bool(Overloaded method)
                existingUser.setUsername(newUser.getUsername());
                if (newUser.getPassword() != null){
                    newUser.setPassword(encoder.encode(newUser.getPassword()));
                }
                repo.save(existingUser);
                return "Updated Successfully";
        }

        return "Invalid, the new Username is already exist.";
    }

//********************Update By Id **************************

    public String updateById(User user) {

        User user1 = repo.findById(user.getUser_id());

        try{
            if(user1 == null)
                throw new Exception("Invalid, This user is not exist.");
        }
        catch (Exception e)
        {
            return e.getMessage();
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);

        if(user.getUsername() == null){
            user.setPassword(encoder.encode(user.getPassword()));
            repo.save(user);
            return "Updated Successfully";
        }
        else if (!user.getUsername().equals(user1.getUsername()) &&
                repo.findByUsername(user.getUsername()) == null) {

            if (user.getPassword() != null){
                user.setPassword(encoder.encode(user.getPassword()));
            }

            repo.save(user);
            return "Updated Successfully";
        }
        
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return "The User Updated Successfully";
    }

    public Set<Role> getRoles(int id) {
        User user = repo.findById(id);
        return user.getRoles();
    }
}
