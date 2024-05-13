package org.takehomeassessment.data.entities;

import jakarta.persistence.*;
import lombok.*;

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

    private boolean verified = false;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Otp otp;

    private LocalDateTime updatedAt = LocalDateTime.now();

    private LocalDateTime createdAt = LocalDateTime.now();
}
