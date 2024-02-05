package com.palavecinofranco.courses.services;

import com.palavecinofranco.courses.models.User;
import com.palavecinofranco.courses.models.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICourseService {
    List<Course> getAll();
    Optional<Course> getById(Long id);
    Optional<Course> getByIdWithUsers(Long id);


    Optional<Course> getByName(String name);
    Course save(Course course);
    Course update(Course course, Long id);
    void delete(Long id);
    void deleteCourseUser(Long id);

    Page<Course> findAll(Pageable pageable);

    Optional<User> addUser(User user, Long id);
    Optional<User> removeUser(User user, Long id);
    Optional<User> saveUser(User user, Long id);
}
