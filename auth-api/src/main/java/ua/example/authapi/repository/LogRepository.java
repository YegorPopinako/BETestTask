package ua.example.authapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.example.authapi.model.ProcessingLog;

@Repository
public interface LogRepository extends JpaRepository<ProcessingLog, Long> {
}
