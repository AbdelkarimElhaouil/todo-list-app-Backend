package com.elhaouil.Todo_list_app.Repo;

import com.elhaouil.Todo_list_app.DTO.UserSecurityDTO;
import com.elhaouil.Todo_list_app.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    User findByUsername(String username);

    Boolean findByUsername(User userDTO);

    Boolean findByUsername(UserSecurityDTO userSecurityDTO);

    User findById(long id);

}
