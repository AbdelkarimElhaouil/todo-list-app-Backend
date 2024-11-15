package com.elhaouil.Todo_list_app.Repo;

import com.elhaouil.Todo_list_app.Model.Role;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
}
