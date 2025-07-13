package com.Manager.task_manager.Controller;

import com.Manager.task_manager.Dto.TaskDto.TaskRequestDto;
import com.Manager.task_manager.Exceptions.RessourceNotFound;
import com.Manager.task_manager.Services.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    TaskServices taskServices;

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskRequestDto taskRequestDto){
        try{
            taskServices.CreateTask(taskRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("success","Task Created "));
        } catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error" , ex.getMessage()));
        } catch (RessourceNotFound ex){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error",ex.getMessage()));
        }
    }

    @PatchMapping("/{action}")
    public ResponseEntity<?> updateTask(@PathVariable String action ,@RequestBody TaskRequestDto taskRequestDto){
        try{
            if (Objects.equals(action,"update")){
                taskServices.UpdateTask(taskRequestDto);
                return ResponseEntity.ok("Task updated with success ");
            } else if (Objects.equals(action, "delete")) {
                taskServices.softDelete(taskRequestDto.getId());
                return  ResponseEntity.ok("Task deleted with success ");
            } else {
                return ResponseEntity.badRequest().body(
                        String.format("Invalid action '%s'. Expected 'update' or 'delete'.", action));
            }
        } catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error" , ex.getMessage()));
        } catch (RessourceNotFound ex){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error",ex.getMessage()));
        }
    }
}
