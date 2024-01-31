package com.palavecinofranco.courses.controllers;

import com.palavecinofranco.courses.models.entities.Course;
import com.palavecinofranco.courses.services.CourseServiceImp;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CourseController {

    private final CourseServiceImp service;

    public CourseController(CourseServiceImp service){
        this.service = service;
    }

    @GetMapping("/list")
    public List<Course> getAll(){
        return service.getAll();
    }

    @GetMapping("/page")
    public Page<Course> getAll(@PageableDefault(page = 0, size = 20) Pageable pageable){
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathParam("id") Long id){
        if(service.getById(id).isPresent()){
            return ResponseEntity.ok(service.getById(id).get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getByName(@PathParam("name") String name){
        if(service.getByName(name).isPresent()){
            return ResponseEntity.ok(service.getByName(name).get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody Course course, BindingResult result){
        if(result.hasErrors()){
           return fieldValidation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(course));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Course course, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return fieldValidation(result);
        }
        if(service.getById(id).isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(service.update(course, id));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(service.getById(id).isPresent()){
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<Map<String, String>> fieldValidation(BindingResult result) {
        Map<String, String> errors =  new HashMap<>();
        result.getFieldErrors().forEach(e->{
            errors.put(e.getField(), "El campo " + e.getField() + " " + e.getDefaultMessage() );
        });
        return ResponseEntity.badRequest().body(errors);
    }
}

