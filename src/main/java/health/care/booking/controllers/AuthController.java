package health.care.booking.controllers;

import health.care.booking.dto.AuthRequest;
import health.care.booking.dto.AuthResponse;
import health.care.booking.dto.RegisterRequest;
import health.care.booking.dto.RegisterResponse;
import health.care.booking.models.ERole;
import health.care.booking.models.Role;
import health.care.booking.models.User;
import health.care.booking.respository.RoleRepository;
import health.care.booking.respository.UserRepository;
import health.care.booking.services.CustomUserDetailsService;
import health.care.booking.services.UserService;
import health.care.booking.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request,
                                   HttpServletResponse response) {
        Logger logger = LoggerFactory.getLogger(AuthController.class);

        try {
            logger.info("Authenticating user: {}", request.getUsername());
            logger.info("Password from request: {}", request.getPassword());
            // authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            // set authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // get UserDetails
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            logger.info("User authenticated: {}", userDetails.getUsername());

            // generate JWT token
            String jwt = jwtUtil.generateToken(userDetails);

            // generate JWT cookie
            ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwt)
                    .httpOnly(true)
                    .secure(false) // OBS! set to true in production with HTTPS
                    .path("/")
                    .maxAge(10 * 60 * 60) // 10 hours
                    .sameSite("Strict") // "Strict", "Lax", or "None"
                    .build();

            // add cookie to response
            response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

            // return response without JWT in body
            User user = userService.findByUsername(userDetails.getUsername());
            Set<ERole> roles = user.getRoles().stream()
                    .map(Role::getRoleP)
                    .collect(Collectors.toSet());

            AuthResponse authResponse = new AuthResponse(
                    jwt, // JWT token
                    userDetails.getUsername(),
                    roles
            );

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(authResponse);

        }  catch (AuthenticationException e) {
            logger.error("Authentication failed for user: {}", request.getUsername(), e);
            // Authentication failed
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect username or password");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        Logger logger = LoggerFactory.getLogger(AuthController.class);

        // check if the username already exists
        if (userService.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Username is already taken");
        }

        // map the registration request to a User entity
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        user.setPassword(registerRequest.getPassword());
        logger.info("Encoded password: {}", encodedPassword);
        //user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setStreet(registerRequest.getStreet());
        user.setCity(registerRequest.getCity());
        user.setState(registerRequest.getState());
        user.setZipcode(registerRequest.getZipcode());



        // assign roles
        if (registerRequest.getRoles() == null || registerRequest.getRoles().isEmpty()) {
            Optional<Role> userRole = roleRepository.findByRoleP(ERole.ROLE_USER);
            userRole.ifPresent(role -> user.setRoles(Set.of(role)));
        } else {
            Set<Role> roles = registerRequest.getRoles().stream()
                    .map(role -> roleRepository.findByRoleP(role).orElseThrow(() -> new RuntimeException("Role not found")))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        // register the user using UserService
        userService.registerUser(user);

        // create a response object
        RegisterResponse regResponse = new RegisterResponse(
                "User registered successfully",
                user.getUsername(),
                user.getRoles().stream()
                        .map(Role::getRoleP)
                        .collect(Collectors.toSet())
        );

        return ResponseEntity.ok(regResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // clear the JWT cookie by setting its maxAge to 0
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", null)
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        // clear the SecurityContext
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("Logged out successfully");
    }

    // check if user is authenticated
    @GetMapping("/check")
    public ResponseEntity<?> checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        Set<ERole> roles = user.getRoles().stream()
                .map(Role::getRoleP)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new AuthResponse(
                "Authenticated",
                user.getUsername(),
                roles
        ));
    }

}
