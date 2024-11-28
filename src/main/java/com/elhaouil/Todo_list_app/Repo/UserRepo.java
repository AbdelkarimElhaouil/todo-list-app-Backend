package com.elhaouil.Todo_list_app.Repo;

import com.elhaouil.Todo_list_app.DTO.UserRegistrationDTO;
import com.elhaouil.Todo_list_app.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User findByUsername(String username); // todo -> change return type to Optional

    Optional<User> findByEmail(String email);

    boolean findByUsername(User userDTO);

    boolean findByUsername(UserRegistrationDTO userRegistrationDTO);

    User findById(long id); // todo -> change return type to Optional

}
