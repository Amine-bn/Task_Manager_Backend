package com.Manager.task_manager.Dto;
//import jakarta.validation.constraints.NotBlank;
import com.Manager.task_manager.Entity.User;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
@Data
public class ProjectDto {
    private Long id;
    @NotBlank (message = "Project name must not be blank")
    private String projectName;
    private User teamLead;
}
