package com.palavecinofranco.courses.services;

import com.palavecinofranco.courses.clients.UserFeignClient;
import com.palavecinofranco.courses.models.User;
import com.palavecinofranco.courses.models.entities.Course;
import com.palavecinofranco.courses.models.entities.CourseUser;
import com.palavecinofranco.courses.repositories.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImp implements ICourseService {

    private final CourseRepository repository;
    private final UserFeignClient client;

    public CourseServiceImp(CourseRepository repository, UserFeignClient feignClient){
        this.repository = repository;
        this.client = feignClient;
    }
    @Override
    public List<Course> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Course> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Course> getByIdWithUsers(Long id) {
        Optional<Course> o = repository.findById(id);
        if (o.isPresent()){
            Course course = o.get();
            if (!course.getCourseUsers().isEmpty()){
                List<Long> ids = course.getCourseUsers()
                        .stream()
                        .map(c->c.getUserId())
                        .toList();
                List<User> users = client.getAllById(ids);
                course.setUsers(users);
            }
            return Optional.of(course);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Course> getByName(String name) {
        return repository.findByNameIgnoreCase(name);
    }

    @Override
    public Course save(Course course) {
        return repository.save(course);
    }

    @Override
    public Course update(Course course, Long id) {
        Course courseDB = repository.findById(id).orElse(null);
        if(courseDB!=null){
            courseDB.setName(course.getName());
            return repository.save(courseDB);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteCourseUser(Long id) {
        repository.deleteCourseUser(id);
    }

    @Override
    public Page<Course> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<User> addUser(User user, Long id) {
        Optional<Course> course = repository.findById(id);

        if (course.isPresent()){
            User userService = client.getById(user.getId());

            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userService.getId());

            course.get().addCourseUser(courseUser);
            repository.save(course.get());
            return Optional.of(userService);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> removeUser(User user, Long id) {
        Optional<Course> course = repository.findById(id);

        if (course.isPresent()){
            User userService = client.getById(user.getId());

            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userService.getId());

            course.get().removeCourseUser(courseUser);
            repository.save(course.get());
            return Optional.of(userService);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> saveUser(User user, Long id) {
        Optional<Course> course = repository.findById(id);

        if (course.isPresent()){
            User userService = client.save(user);

            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userService.getId());

            course.get().addCourseUser(courseUser);
            repository.save(course.get());
            return Optional.of(userService);
        }
        return Optional.empty();
    }
}
