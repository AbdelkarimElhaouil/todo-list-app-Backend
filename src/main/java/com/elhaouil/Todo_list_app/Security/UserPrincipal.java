package com.elhaouil.Todo_list_app.Security;

import com.elhaouil.Todo_list_app.Model.Role;
import com.elhaouil.Todo_list_app.Model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrincipal implements UserDetails {

    User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<Role> getAuthorities() {
        return user.getRoles();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
