package com.Manager.task_manager.Services;

import com.Manager.task_manager.Entity.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    public Long getCurrentUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//        return userDetails.getId();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new RuntimeException("Utilisateur non authentifié");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails customUser) {
            return customUser.getId();
        } else if (principal instanceof com.Manager.task_manager.Entity.User user) {
            return user.getId();
        } else {
            throw new RuntimeException("Type de principal inattendu : " + principal.getClass());
        }

    }
}
