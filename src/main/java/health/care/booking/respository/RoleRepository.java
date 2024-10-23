package health.care.booking.respository;

import health.care.booking.models.ERole;
import health.care.booking.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {


    Optional<Role> findByRoleP (ERole roleP);
}
