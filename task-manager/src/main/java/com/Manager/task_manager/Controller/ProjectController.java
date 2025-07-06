package com.Manager.task_manager.Controller;
import com.Manager.task_manager.Dto.ProjectDto;
import com.Manager.task_manager.Entity.Project;
import com.Manager.task_manager.Entity.User;
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

    @PostMapping("/create/{teamLeadId}")
    public ResponseEntity<?> createProject(@RequestBody ProjectDto projectDto , @PathVariable Long teamLeadId){
    try{
        projectServices.createPorject(projectDto ,teamLeadId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("success","Project Created "));
        //return new ResponseEntity<>(, HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error",e.getMessage()));
    } catch (RessourceNotFound e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error" , e.getMessage()));
    }
    };

    //correct this

    @PutMapping("/{update}/{projectId}/{teamLeadId}")
    public ResponseEntity<?> updateProject(@PathVariable String update, @PathVariable Long projectId, @PathVariable Long teamLeadId ,@RequestBody ProjectDto projectDto ){
        try{
            if(Objects.equals(update , "update")){
                projectServices.updateProject(projectId , teamLeadId ,projectDto.getProjectName());
                return ResponseEntity.ok("Project updated with success ");
            } else if (Objects.equals(update, "delete")) {
                projectServices.softDelete(projectId , teamLeadId ,projectDto.getProjectName());
                return ResponseEntity.ok("Project deleted with success ");
            }else {
                return ResponseEntity.badRequest().body(
                        String.format("Invalid action '%s'. Expected 'update' or 'delete'.", update));
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

    //get all projects
    //softdelete project
    //
}
