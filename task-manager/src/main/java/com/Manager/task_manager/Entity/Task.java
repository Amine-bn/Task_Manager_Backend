package com.Manager.task_manager.Entity;

import com.Manager.task_manager.Enums.taskStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String descripition;
    private Long teamLeadId;
    private Long userId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_Id", nullable = false)
    private Project project;
    private taskStatus taskStatus;
    private LocalDateTime dateExecute;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
}
