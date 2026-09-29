package com.mams.backend.security;

import com.mams.backend.exception.UnauthorizedBaseAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public UserDetailsImpl getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
            return (UserDetailsImpl) authentication.getPrincipal();
        }
        return null;
    }

    public void checkBaseAccess(Long targetBaseId) {
        UserDetailsImpl currentUser = getCurrentUser();
        if (currentUser == null) {
            throw new UnauthorizedBaseAccessException("Not authenticated");
        }
        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            return;
        }
        if (!targetBaseId.equals(currentUser.getBaseId())) {
            throw new UnauthorizedBaseAccessException("You do not have access to base ID: " + targetBaseId);
        }
    }
}
