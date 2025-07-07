package com.Manager.task_manager.Controller;
import com.Manager.task_manager.Dto.ProjectDto.ProjectRequestDto;
import com.Manager.task_manager.Dto.ProjectDto.ProjectResponceDto;
import com.Manager.task_manager.Exceptions.RessourceNotFound;
import com.Manager.task_manager.Services.ProjectServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    ProjectServices projectServices;
    public ProjectController(){

    }

    @PostMapping("/create")
    public ResponseEntity<?> createProject(@RequestBody ProjectRequestDto projectDto ){
    try{
        projectServices.createPorject(projectDto ,projectDto.getTeamleadId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("success","Project Created "));
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error",e.getMessage()));
    } catch (RessourceNotFound e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error" , e.getMessage()));
    }
    };

    //chnage the parametre
    @PatchMapping("/{action}")
    public ResponseEntity<?> updateProject(@PathVariable String action ,@RequestBody ProjectRequestDto projectDto ){
        try{
            if(Objects.equals(action , "update")){
                projectServices.updateProject(projectDto.getId() , projectDto.getTeamleadId(), projectDto.getProjectName());
                return ResponseEntity.ok("Project updated with success ");
            } else if (Objects.equals(action, "delete")) {
                projectServices.softDelete(projectDto.getId() , projectDto.getTeamleadId());
                return ResponseEntity.ok("Project deleted with success ");
            }else {
                return ResponseEntity.badRequest().body(
                        String.format("Invalid action '%s'. Expected 'update' or 'delete'.", action));
            }
        } catch (AccessDeniedException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error",e.getMessage()));
        } catch (RessourceNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error" , e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error",e.getMessage()));
        }
    }
main

//    @GetMapping("/getAll")
//    public  ResponseEntity<?> getAllProjects(){
//        try{
//
//        }
//    }
    //get all projects
    //softdelete project
    //

Feature/ProjectSysteme
}
