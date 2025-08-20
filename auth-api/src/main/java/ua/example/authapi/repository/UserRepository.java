package ua.example.authapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.example.authapi.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String username);
}
