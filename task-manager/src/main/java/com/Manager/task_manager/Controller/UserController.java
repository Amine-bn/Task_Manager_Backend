package com.Manager.task_manager.Controller;

import com.Manager.task_manager.Dto.UserDto;
import com.Manager.task_manager.Services.ProjectServices;
import com.Manager.task_manager.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    ProjectServices projectServices;
    @Autowired
    UserServices userServices;

//    public UserController()
    @PostMapping("/create")
    public ResponseEntity <?> createUser(@RequestBody UserDto userDto){

            String CreateUser = userServices.createUser(userDto.getUsername() , userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body("sucess");
    }

}
