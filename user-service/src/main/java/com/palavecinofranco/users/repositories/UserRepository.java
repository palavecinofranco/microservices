package com.palavecinofranco.users.repositories;

import com.palavecinofranco.users.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsernameIgnoreCase(String username);
    public Optional<User> findByEmailIgnoreCase(String email);
}
