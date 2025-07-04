package com.Manager.task_manager.Dto;

import com.Manager.task_manager.Entity.Project;
import lombok.Data;

import java.util.List;
@Data
public class UserDto {
    private Long id;
    private String username;
    private String lastName;
    private String firstname;
    private String email;
    private String password;
    private String phoneNumber;
    private com.Manager.task_manager.Enums.userRole userRole;
    private List<Project> projectListe;
}
