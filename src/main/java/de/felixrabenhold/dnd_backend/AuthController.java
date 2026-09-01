package de.felixrabenhold.dnd_backend;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(AppUserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new IllegalArgumentException("Benutzername bereits vergeben");
        }

        String hashedPassword = passwordEncoder.encode(request.password());
        AppUser newUser = new AppUser(request.username(), hashedPassword);
        userRepository.save(newUser);

        String token = jwtService.generateToken(newUser.getUsername());
        return new AuthResponse(token);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        AppUser user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("Ungültige Anmeldedaten"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Ungültige Anmeldedaten");
        }

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }
}