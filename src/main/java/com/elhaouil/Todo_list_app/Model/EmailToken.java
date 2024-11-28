package com.elhaouil.Todo_list_app.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Component
@EntityListeners(EntityListeners.class)
public class EmailToken {
    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String token;

    @CreatedBy
    private LocalDateTime generatedAt;
    private LocalDateTime expiresAt;
    private LocalDateTime validateAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
