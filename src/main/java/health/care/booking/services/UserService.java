package health.care.booking.services;

import health.care.booking.models.ERole;
import health.care.booking.models.Role;
import health.care.booking.models.User;
import health.care.booking.respository.RoleRepository;
import health.care.booking.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(User user) {
        // hash the password
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        //user.setCreatedAt(new Date());

        user.setCreatedAt(new Date());

        // ensure the user has at least the default role
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Optional<Role> userRole = roleRepository.findByRoleP(ERole.ROLE_USER);
            userRole.ifPresent(role -> user.setRoles(Set.of(role)));
        }

        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
