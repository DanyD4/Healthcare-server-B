package health.care.booking.dto;

import health.care.booking.models.ERole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;
import java.util.Set;

public class RegisterRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank //Is not activated, need @Valid
    @Email
    @Indexed(unique = true)
    private String email;

    @NotBlank
    @Pattern(regexp =
            "^" + //Början av strängan.
                    "(?=.*\\d)" + // En positiv lookahead som kräver minst en siffra i strängen.
                    "(?=.*[a-z])" + // En positiv lookahead som kräver minst en lite bokstav i strängen.
                    "(?=.*[A-Z])" + //En positiv lookahead som kräver minst en stor bokstav i strängen.
                    ".{8,80}" + // En positiv lookahead som kräver på att strängen måste innehålla mellan 8 och 40 tecken lång.
                    "$", //Slutet av strängen.
            message = "The password must contain at least one uppercase letter and one digit.")

    private String password;

    @NotBlank
    @Size(max = 20)
    private String firstName;
    @NotBlank
    @Size(max = 20)
    private String lastName;
    private Set<ERole> roles;
    @Size(max = 40)
    private String street;
    @Size(max = 40)
    private String city;
    @Size(max = 40)
    private String state;
    @Size(max = 10)
    private String zipcode;

    @CreatedDate
    private Date createdAt;



    public RegisterRequest() {}


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public Set<ERole> getRoles() {
        return roles;
    }
    public void setRoles(Set<ERole> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
