package com.Manager.task_manager.Dto.ProjectDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectRequestDto {
    private Long id;
    @NotBlank(message = "Project name must not be blank")
    private String projectName;
    private Long teamleadId;
}
