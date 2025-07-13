package com.Manager.task_manager.Dto.TaskDto;

import com.Manager.task_manager.Enums.taskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class TaskRequestDto {
    private Long id;
    private String title;
    private String descripition;
    private Long teamLeadId;
    private Long userId;
    private Long projectId;
//    private taskStatus taskStatus;
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
private LocalDateTime dateExecute;
}
