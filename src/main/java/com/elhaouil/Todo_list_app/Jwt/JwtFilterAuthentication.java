package com.elhaouil.Todo_list_app.Jwt;

import com.elhaouil.Todo_list_app.Model.User;
import com.elhaouil.Todo_list_app.Repo.UserRepo;
import com.elhaouil.Todo_list_app.Security.MyUserDetailsService;
import com.elhaouil.Todo_list_app.Security.UserPrincipal;
import jakarta.persistence.Access;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtFilterAuthentication extends OncePerRequestFilter {
    @Autowired
    UserRepo userRepo;
    @Autowired
    JwtService service;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token= null;
        String username = null;
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = service.extractUsername(token);
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserPrincipal userPrincipal = new UserPrincipal(userRepo.findByUsername(username));
            if(service.validateToken(token, userPrincipal)){
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}
