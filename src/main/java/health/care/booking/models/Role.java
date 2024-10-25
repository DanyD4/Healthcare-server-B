package health.care.booking.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
public class Role {
    @Id
    private String id;

    private ERole roleP;

    public Role() {

    }

    public Role(ERole roleP) {
        this.roleP = roleP;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ERole getRoleP() {
        return roleP;
    }

    public void setRoleP(ERole roleP) {
        this.roleP = roleP;
    }
}

