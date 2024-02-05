package com.palavecinofranco.users.controllers;

import com.palavecinofranco.users.models.entities.User;
import com.palavecinofranco.users.services.UserServiceImp;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final UserServiceImp service;

    public UserController(UserServiceImp service){
        this.service = service;
    }

    @GetMapping("/list")
    public List<User> getAll(){
        return service.getAll();
    }

    @GetMapping("/page")
    public Page<User> getAll(@PageableDefault(page = 0, size = 20) Pageable pageable){
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        if(service.getById(id).isPresent()){
            return ResponseEntity.ok(service.getById(id).get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario con el id " + id + " no encontrado");
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable("username") String username){
        if(service.getByUsername(username).isPresent()){
            return ResponseEntity.ok(service.getByUsername(username).get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario " + username + " no encontrado");
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getByEmail(@PathVariable("email") String email){
        if(service.getByEmail(email).isPresent()){
            return ResponseEntity.ok(service.getByEmail(email).get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario " + email + " no encontrado");
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllById(@RequestParam List<Long> ids){
       return ResponseEntity.ok(service.findAllById(ids));
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody User user, BindingResult result){
        if (result.hasErrors()){
            return fieldValidation(result);
        }
        if (service.getByEmail(user.getEmail()).isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "Ya existe un usuario con ese email"));
        }
        if (service.getByUsername(user.getUsername()).isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "Ya existe un usuario con ese nombre de usuario"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id){
        if(service.getById(id).isPresent()){
            if (result.hasErrors()){
                return fieldValidation(result);
            }
            if (service.getByEmail(user.getEmail()).isPresent()){
                if (!id.equals(service.getByEmail(user.getEmail()).get().getId())){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "Ya existe un usuario con ese email"));
                }
            }
            if (service.getByUsername(user.getUsername()).isPresent()){
                if (!id.equals(service.getByUsername(user.getUsername()).get().getId())){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "Ya existe un usuario con ese username"));
                }
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(service.update(user, id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario con el id " + id + " no encontrado");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(service.getById(id).isPresent()){
            service.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario con el id " + id + " no encontrado");
    }

    private ResponseEntity<Map<String, String>> fieldValidation(BindingResult result) {
        Map<String, String> errors =  new HashMap<>();
        result.getFieldErrors().forEach(e->{
            errors.put(e.getField(), "El campo " + e.getField() + " " + e.getDefaultMessage() );
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
