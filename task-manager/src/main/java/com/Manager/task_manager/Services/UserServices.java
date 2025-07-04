package com.Manager.task_manager.Services;
import com.Manager.task_manager.Entity.User;
import com.Manager.task_manager.Enums.userRole;
import com.Manager.task_manager.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServices {
    @Autowired
    UserRepository userRepository;

    public String createUser( String username , String email){
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setUserRole(userRole.ROLE_MANAGER);
        userRepository.save(newUser);
        return "user created success ";
    }

}
