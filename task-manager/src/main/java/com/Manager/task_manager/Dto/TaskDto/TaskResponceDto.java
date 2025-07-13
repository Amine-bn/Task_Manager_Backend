package com.Manager.task_manager.Dto.TaskDto;

import com.Manager.task_manager.Enums.taskStatus;

import java.util.Date;

public class TaskResponceDto {
    private String title;
    private String descripition;
    private taskStatus taskStatus;
    private Long projectId;
    private Date dateExecute;
    private Date createdAt;
    private Date deletedAt;
}
