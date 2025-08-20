package ua.example.authapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.example.authapi.dto.UserDto;
import ua.example.authapi.exception.EmailAlreadyInUseException;
import ua.example.authapi.exception.InvalidCredentialsException;
import ua.example.authapi.model.User;
import ua.example.authapi.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserDto userDto) {
        if(userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new EmailAlreadyInUseException("Email already in use");
        }
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void checkCredentials(User user, UserDto userDto) {
        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
    }
}
