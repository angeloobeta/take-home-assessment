package org.takehomeassessment.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String profilePic;

    @Column(unique = true)
    private String phoneNumber;

    private String firstName;

    private String lastName;

    private LocalDateTime updatedAt = LocalDateTime.now();

    private LocalDateTime createdAt = LocalDateTime.now();
}
