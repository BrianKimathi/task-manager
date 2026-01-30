package com.briankimathi.taskmanager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Data
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(unique = true)
    @Email
    private String email;

    private String password;

}
