package com.example.playstationdemo.repository;

import com.example.playstationdemo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

    Page<User> findAllByFio(@NotBlank(message = "FIO is mandatory") String fio, Pageable pageable);
}
