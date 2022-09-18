package ru.timutkin.restfulapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.timutkin.restfulapplication.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);

    boolean existsById(Long id);
}