package com.elhaouil.Todo_list_app.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class Task {
    @Id
    private long id;
    private String description;
    @ManyToOne()
    @JoinColumn(name = "user_task")
    private User user;
}
