package com.palavecinofranco.courses.clients;

import com.palavecinofranco.courses.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="user-service", url = "user-service:8001")
public interface UserFeignClient {

    @GetMapping("/users")
    List<User> getAllById(@RequestParam List<Long> ids);
    @GetMapping("/{id}")
    User getById(@PathVariable Long id);
    @PostMapping("/save")
    User save(@RequestBody User user);
}
