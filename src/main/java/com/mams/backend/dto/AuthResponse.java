package com.mams.backend.dto;

public class AuthResponse {
    private String token;
    private String role;
    private Long baseId;
    private String name;

    public AuthResponse(String token, String role, Long baseId, String name) {
        this.token = token;
        this.role = role;
        this.baseId = baseId;
        this.name = name;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Long getBaseId() { return baseId; }
    public void setBaseId(Long baseId) { this.baseId = baseId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
