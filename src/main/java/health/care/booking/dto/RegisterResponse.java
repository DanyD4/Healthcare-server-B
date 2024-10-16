package health.care.booking.dto;


import health.care.booking.models.ERole;

import java.util.Set;

public class RegisterResponse {

    private String message;
    private String username;
    private Set<ERole> ERoles;

    public RegisterResponse() {
    }

    public RegisterResponse(String message, String username, Set<ERole> ERoles) {
        this.message = message;
        this.username = username;
        this.ERoles = ERoles;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<ERole> getRoles() {
        return ERoles;
    }

    public void setRoles(Set<ERole> ERoles) {
        this.ERoles = ERoles;
    }
}
