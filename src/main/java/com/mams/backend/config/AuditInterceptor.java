package com.mams.backend.config;

import com.mams.backend.entity.AuditLog;
import com.mams.backend.repository.AuditLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.time.LocalDateTime;

@Component
public class AuditInterceptor implements HandlerInterceptor {

    private final AuditLogRepository auditLogRepository;

    public AuditInterceptor(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return;
        }

        String uri = request.getRequestURI();
        if (uri.startsWith("/api/")) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user = (auth != null && auth.getName() != null) ? auth.getName() : "anonymous";
            
            AuditLog log = new AuditLog();
            log.setUserId(user);
            log.setAction("API_CALL");
            log.setEndpoint(uri);
            log.setMethod(request.getMethod());
            log.setStatus(String.valueOf(response.getStatus()));
            log.setTimestamp(LocalDateTime.now());
            
            auditLogRepository.save(log);
        }
    }
}
