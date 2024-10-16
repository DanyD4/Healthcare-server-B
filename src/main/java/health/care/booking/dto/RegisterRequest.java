package health.care.booking.dto;

import health.care.booking.models.ERole;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class RegisterRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private Set<ERole> ERoles;

    public RegisterRequest() {}

    public RegisterRequest(String username, String password, Set<ERole> ERoles) {
        this.username = username;
        this.password = password;
        this.ERoles = ERoles;
    }

    public @NotBlank String getUsername() {
        return username;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public Set<ERole> getRoles() {
        return ERoles;
    }

}
