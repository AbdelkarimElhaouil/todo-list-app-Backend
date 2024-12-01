package com.elhaouil.Todo_list_app.Security;

import com.elhaouil.Todo_list_app.Model.User;
import com.elhaouil.Todo_list_app.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        return new UserPrincipal(user);
    }
}
