package com.Manager.task_manager.Services;

import com.Manager.task_manager.Dto.AuthDto.AuthRequest;
import com.Manager.task_manager.Dto.AuthDto.AuthResponce;
import com.Manager.task_manager.Dto.AuthDto.RegisterRequest;
import com.Manager.task_manager.Entity.User;
import com.Manager.task_manager.Enums.userRole;
import com.Manager.task_manager.Repository.UserRepository;
import com.Manager.task_manager.Security.Jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
     private final UserRepository userRepository;
     private final PasswordEncoder passwordEncoder;
     private final JwtService jwtService;
     private final AuthenticationManager authenticationManager;

     public void register(RegisterRequest request){
         if (userRepository.findByEmail(request.getEmail()).isPresent()) {
             throw new IllegalArgumentException("Email already exists!");
         }
         var user = User.builder()
                 .firstname(request.getFirstname())
                 .lastName((request.getLastName()))
                 .email(request.getEmail())
                 .password(passwordEncoder.encode(request.getPassword()))
                 .userRole(userRole.ROLE_USER)
                 .username(request.getUsername())
                 .build();
         userRepository.save(user);
     }

     public AuthResponce authenticate(AuthRequest request){
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            )
    );
    var user = userRepository.findByEmail(request.getEmail())
            .orElseThrow();
         var jwtToken = jwtService.generateToken(user);
         return AuthResponce.builder()
                 .token(jwtToken)
                 .build();
     }
}
