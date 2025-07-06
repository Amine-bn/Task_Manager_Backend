package com.Manager.task_manager.Dto.ProjectDto;
//import jakarta.validation.constraints.NotBlank;
import com.Manager.task_manager.Entity.User;
import lombok.Data;

@Data
public class ProjectResponceDto {
    private Long id;
    private String projectName;
    private User teamLead;
}
