package com.elhaouil.Todo_list_app.Repo;

import com.elhaouil.Todo_list_app.Model.EmailToken;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailTokenRepo extends JpaRepository<EmailToken, Long> {

    Optional<EmailToken> findByToken(String token);
}
