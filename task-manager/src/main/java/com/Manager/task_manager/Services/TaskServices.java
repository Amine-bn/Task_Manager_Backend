package com.Manager.task_manager.Services;

import com.Manager.task_manager.Dto.TaskDto.TaskRequestDto;
import com.Manager.task_manager.Entity.Project;
import com.Manager.task_manager.Entity.Task;
import com.Manager.task_manager.Entity.User;
import com.Manager.task_manager.Enums.taskStatus;
import com.Manager.task_manager.Exceptions.RessourceNotFound;
import com.Manager.task_manager.Repository.ProjectRepository;
import com.Manager.task_manager.Repository.TaskRepository;
import com.Manager.task_manager.Repository.UserRepository;
import com.Manager.task_manager.Utils.ValidationsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServices {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ProjectRepository projectRepository ;
    @Autowired
    UserRepository userRepository;

    public void CreateTask(TaskRequestDto taskDto){
        //changer le teste d'existance si ne pas le dateExicusion change vas creer
        String cleannedNewTaskName = ValidationsUtils.requireNonBlank(taskDto.getTitle(),"Task title");
        String cleannedTaskDescripition= ValidationsUtils.requireNonBlank(taskDto.getDescripition(),"Task descripition");
        User teamLead = userRepository.findById(taskDto.getTeamLeadId())
                .orElseThrow(()->new RessourceNotFound("Teamlead"));
        User worker = userRepository.findById(taskDto.getTeamLeadId())
                .orElseThrow(()->new RessourceNotFound("user"));
        Project project = projectRepository.findById(taskDto.getProjectId())
                .orElseThrow(() -> new RessourceNotFound("Projet "));
        List<Task> tasks = taskRepository.findByTitleAndTeamLeadIdAndUserId(cleannedNewTaskName,
                taskDto.getTeamLeadId(),taskDto.getUserId());
        if(!tasks.isEmpty()){
            throw new IllegalArgumentException("Exists task with this name");
        }
        Task task =new Task();
        task.setTitle(cleannedNewTaskName);
        task.setDescripition(cleannedTaskDescripition);
        task.setCreatedAt(LocalDateTime.now());
        task.setTaskStatus(taskStatus.PENDING);
        task.setProject(project);
        task.setTeamLeadId(taskDto.getTeamLeadId());
        task.setUserId(taskDto.getUserId());
        if(taskDto.getDateExecute() == null){
            throw new IllegalArgumentException("Add date excute please .");
        }
        task.setDateExecute(taskDto.getDateExecute());
        taskRepository.save(task);
    }

    public void UpdateTask(TaskRequestDto taskDto){
        Task task = taskRepository.findById(taskDto.getId())
                .orElseThrow(() -> new RessourceNotFound("Task"));
        if (!taskDto.getDescripition().isEmpty()){
            String cleannedTaskDescripition= ValidationsUtils.requireNonBlank(taskDto.getDescripition(),"Task descripition");
            task.setDescripition(cleannedTaskDescripition);
        }
        if (!taskDto.getTitle().isEmpty()){
            String cleannedNewTaskName = ValidationsUtils.requireNonBlank(taskDto.getTitle(),"Task title");
            task.setTitle(cleannedNewTaskName);
        }
        if(taskDto.getDateExecute() != null){
            task.setDateExecute(taskDto.getDateExecute());
        }
        if (taskDto.getProjectId() != null){
            Project project = projectRepository.findById(taskDto.getProjectId())
                    .orElseThrow(() -> new RessourceNotFound("Projet "));
            task.setProject(project);
        }
        taskRepository.save(task);
    }
    public void softDelete(Long taskId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RessourceNotFound("Task"));

        task.setDeletedAt(LocalDateTime.now());
        taskRepository.save(task);
    }
}
