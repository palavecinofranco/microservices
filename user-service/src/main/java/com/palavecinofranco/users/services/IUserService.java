package com.palavecinofranco.users.services;

import com.palavecinofranco.users.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAll();
    Page<User> findAll(Pageable pageable);
    Optional<User> getById(Long id);
    Optional<User> getByUsername(String username);
    Optional<User> getByEmail(String email);
    User save(User user);
    User update(User user, Long id);
    void delete(Long id);

    List<User> findAllById(List<Long> ids);
}
