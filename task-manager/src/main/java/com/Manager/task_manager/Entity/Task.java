package com.Manager.task_manager.Entity;

import com.Manager.task_manager.Enums.taskStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String descripition;
    private taskStatus taskStatus;
    private Date dateExecute;
    private Date createdAt;
    private Date deletedAt;
}
