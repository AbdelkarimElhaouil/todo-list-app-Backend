package com.elhaouil.Todo_list_app.Repo;

import com.elhaouil.Todo_list_app.Model.User;
import com.elhaouil.Todo_list_app.Model.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserImageRepo extends JpaRepository<UserImage, Long> {
    Optional<UserImage> findByUser(User user);

    boolean existsByUser(User user);

    void deleteByUser(User user);
}
