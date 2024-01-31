package com.palavecinofranco.users.services;

import com.palavecinofranco.users.models.entities.User;
import com.palavecinofranco.users.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements IUserService {

    private final UserRepository repository;

    public UserServiceImp(UserRepository repository){
        this.repository = repository;
    }
    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<User> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return repository.findByUsernameIgnoreCase(username);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User update(User user, Long id) {
        User userDB = repository.findById(id).orElse(null);
        if(userDB!=null) {
            userDB.setUsername(user.getUsername());
            userDB.setEmail(user.getEmail());
            userDB.setPassword(user.getPassword());

            return repository.save(userDB);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
