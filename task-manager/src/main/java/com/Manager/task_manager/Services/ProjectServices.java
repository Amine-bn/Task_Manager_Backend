package com.Manager.task_manager.Services;

import com.Manager.task_manager.Dto.ProjectDto;
import com.Manager.task_manager.Entity.Project;
import com.Manager.task_manager.Entity.User;
import com.Manager.task_manager.Exceptions.RessourceNotFound;
import com.Manager.task_manager.Repository.ProjectRepository;
import com.Manager.task_manager.Repository.UserRepository;
import com.Manager.task_manager.Utils.ValidationsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServices {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;
    public ProjectServices (){

    }
    public void createPorject (ProjectDto projectDto  , Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RessourceNotFound("Teamlead "));
        List<Project> projects = projectRepository.findByProjectNameAndTeamLead_Email(projectDto.getProjectName() ,user.getEmail());
        if(!projects.isEmpty()){
            throw  new IllegalArgumentException("exists project with this name ");
        }
        String cleannedProjectName = ValidationsUtils.requireNonBlank(projectDto.getProjectName(),"Project Name");

        Project newProject =new Project();
        newProject.setProjectName(cleannedProjectName);
        newProject.setTeamLead(user);
        newProject.setCreatedAt(LocalDateTime.now());
        projectRepository.save(newProject);
    }

    public void updateProject(Long projectId,Long teamLeadId , String NewProjectName) throws AccessDeniedException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RessourceNotFound("Project "));

        if (!project.getTeamLead().getId().equals(teamLeadId)) {
            throw new AccessDeniedException("You are not allowed to modify this project.");
        }
        String cleannedNewProjectName = ValidationsUtils.requireNonBlank(NewProjectName,"Project Name");
        List<Project> projects = projectRepository.findByProjectNameAndTeamLead_Email(cleannedNewProjectName ,project.getTeamLead().getEmail());
        if(!projects.isEmpty()){
            throw  new IllegalArgumentException("exists project with this name ");
        }
        project.setProjectName(cleannedNewProjectName);
        projectRepository.save(project);
    }

    public void softDelete(Long projectId,Long teamLeadId , String NewProjectName) throws AccessDeniedException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RessourceNotFound("Project "));

        if (!project.getTeamLead().getId().equals(teamLeadId)) {
            throw new AccessDeniedException("You are not allowed to modify this project.");
        }

        project.setDeletedAt(LocalDateTime.now());
        projectRepository.save(project);
    }

}
