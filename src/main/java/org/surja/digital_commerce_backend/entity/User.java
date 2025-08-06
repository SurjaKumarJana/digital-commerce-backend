package org.surja.digital_commerce_backend.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    // by default it takes the index based values
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    private Company company;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
