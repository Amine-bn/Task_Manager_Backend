package com.Manager.task_manager.Entity;

import com.Manager.task_manager.Enums.userRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String lastName;
    private String firstname;
    @Column(unique = true)
    private String email;
    private String password;
    private String phoneNumber;
    private userRole userRole;

}
