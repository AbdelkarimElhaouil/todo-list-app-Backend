package com.elhaouil.Todo_list_app.Repo;

import com.elhaouil.Todo_list_app.Model.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImageRepo extends JpaRepository<UserImage, Long> {
}
