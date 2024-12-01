package com.elhaouil.Todo_list_app.Repo;

import com.elhaouil.Todo_list_app.Model.Task;
import com.elhaouil.Todo_list_app.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Task, Integer> {
    Optional<Task> findByDescription(String desc);

    Task findById(long id);

    boolean existsById(long id);

    void deleteById(long id);
}
