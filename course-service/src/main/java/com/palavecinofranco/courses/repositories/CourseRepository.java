package com.palavecinofranco.courses.repositories;

import com.palavecinofranco.courses.models.entities.Course;
import com.palavecinofranco.courses.models.entities.CourseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByNameIgnoreCase(String name);
    @Modifying
    @Query("delete from CourseUser cu where cu.userId=?1")
    void deleteCourseUser(Long id);
}
