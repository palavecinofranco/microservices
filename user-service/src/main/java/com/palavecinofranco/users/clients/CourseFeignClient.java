package com.palavecinofranco.users.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "course-service", url = "localhost:8002")
public interface CourseFeignClient {

    @DeleteMapping("/remove-courseuser/{id}")
    void deleteCourseUser(@PathVariable Long id);
}
