package com.Manager.task_manager.Controller;
import com.Manager.task_manager.Dto.ProjectDto.ProjectRequestDto;
import com.Manager.task_manager.Dto.ProjectDto.ProjectResponceDto;
import com.Manager.task_manager.Entity.CustomUserDetails;
import com.Manager.task_manager.Exceptions.RessourceNotFound;
import com.Manager.task_manager.Security.Jwt.JwtService;
import com.Manager.task_manager.Services.CurrentUserService;
import com.Manager.task_manager.Services.ProjectServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    ProjectServices projectServices;
    @Autowired
    private JwtService JwtService;

    @Autowired
    private CurrentUserService currentUserService;
//    public ProjectController(){
//
//    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createProject(@RequestBody(required = false) ProjectRequestDto projectDto ){
        System.err.println("🎯 Contrôleur atteint avec body: " + projectDto);
    try{

        projectServices.createPorject(projectDto ,projectDto.getTeamleadId());
        System.err.println("⚠️ success: " );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("success","Project Created "));
    } catch (IllegalArgumentException e) {
        System.err.println("⚠️ IllegalArgumentException: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error",e.getMessage()));
    } catch (RessourceNotFound e) {
        System.err.println("⚠️ not found: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error" , e.getMessage()));
    }
    };

    private Long getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            return null;
        }
        try{
            Object credentials = authentication.getCredentials();
            if(credentials != null && credentials instanceof String) {
                String token = (String) credentials ;
                try{
                    Long userId = Long.parseLong(JwtService.extractClaim(token ,
                            claims -> claims.get("userId")).toString());
                    return userId;
                } catch (Exception e) {
                    System.out.println(" l'erreur est :"+ e.getMessage());
                    throw new RuntimeException(e);
                }
            }
//            if (authentication.getPrincipal() instanceof UserDetails) {
//                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//                String usern
//            }
        }catch (Exception e) {
            System.out.println("Error getting current user ID: {}"+ e.getMessage()+ e);
            return null;
        }
        return  1L;
    }

    //chnage the parametre
    @PatchMapping("/{action}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateProject(@PathVariable String action ,
                                           @RequestBody ProjectRequestDto projectDto ,
                                           @AuthenticationPrincipal CustomUserDetails userDetails){
        try{
            Long currentUserId = currentUserService.getCurrentUserId();  // ✅ propre

//            if( currentUserId != projectDto.getId()){
//                return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                        .body(Map.of("error", "You can only create courses for yourself"));
//            }
            if (!currentUserId.equals(projectDto.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Vous ne pouvez modifier que votre propre compte.");
            }
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

}
