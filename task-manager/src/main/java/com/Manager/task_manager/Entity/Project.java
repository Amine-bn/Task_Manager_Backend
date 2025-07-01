package com.Manager.task_manager.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String projectName;
//    @OneToMany(fetch = )
 //   private User teamLead;
}
