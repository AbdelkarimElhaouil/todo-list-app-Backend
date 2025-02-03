package com.elhaouil.Todo_list_app.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    User user;

    @Column(columnDefinition = "BYTEA")
    private byte[] imageData;

    @Column(nullable = false)
    private String imageName;

    @Column(nullable = false)
    private String contentType;
}
