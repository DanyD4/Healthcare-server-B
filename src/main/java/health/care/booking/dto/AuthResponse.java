package health.care.booking.dto;

import health.care.booking.models.ERole;
import java.util.Set;

public class AuthResponse {
    private String jwtToken;
    private String username;
    private Set<ERole> roles;

    public AuthResponse() {
    }

    public AuthResponse(String jwtToken, String username, Set<ERole> roles) {
        this.jwtToken = jwtToken;
        this.username = username;
        this.roles = roles;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<ERole> getRoles() {
        return roles;
    }

    public void setRoles(Set<ERole> roles) {
        this.roles = roles;
    }
}

