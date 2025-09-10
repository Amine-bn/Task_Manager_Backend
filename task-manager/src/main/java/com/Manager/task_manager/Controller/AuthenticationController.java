package com.Manager.task_manager.Controller;

import com.Manager.task_manager.Dto.AuthDto.AuthRequest;
import com.Manager.task_manager.Dto.AuthDto.AuthResponce;
import com.Manager.task_manager.Dto.AuthDto.RegisterRequest;
import com.Manager.task_manager.Services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ){
        try{
            authenticationService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("success","Authentification success"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error",e.getMessage()));
        }
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponce> authenticate(
            @RequestBody AuthRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
