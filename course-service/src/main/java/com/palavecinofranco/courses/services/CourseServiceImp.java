package com.palavecinofranco.courses.services;

import com.palavecinofranco.courses.models.entities.Course;
import com.palavecinofranco.courses.repositories.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImp implements ICourseService {

    private final CourseRepository repository;

    public CourseServiceImp(CourseRepository repository){
        this.repository = repository;
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
    public Page<Course> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
