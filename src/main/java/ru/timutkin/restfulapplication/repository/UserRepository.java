package ru.timutkin.restfulapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.timutkin.restfulapplication.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);

    boolean existsById(Long id);

    @Override
    Optional<UserEntity> findById(Long aLong);
}