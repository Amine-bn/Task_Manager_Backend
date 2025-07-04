package com.Manager.task_manager.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String projectName;
    @ManyToOne (fetch = FetchType.LAZY , optional = false , cascade = CascadeType.ALL)
    private User teamLead;
    @OneToMany(mappedBy = "email" , fetch = FetchType.LAZY , orphanRemoval = true )
    private List<User> team;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
}
