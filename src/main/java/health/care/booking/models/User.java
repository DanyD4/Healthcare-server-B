package health.care.booking.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String username;
    private String password;
    @DBRef
    private Set<ERole> ERoles;

    public User() {
    }

    public User(String username, String password, Set<ERole> ERoles) {
        this.username = username;
        this.password = password;
        this.ERoles = ERoles;
    }


    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Set<ERole> getRoles() {
        return ERoles;
    }

    public void setRoles(Set<ERole> ERoles) {
        this.ERoles = ERoles;
    }
}
