package org.example.webapp.validation.user.controller;

import jakarta.validation.Valid;
import org.example.webapp.validation.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userWithValidAnnotation")
public class UserWithValidAnnotationController {

    @PostMapping("/user")
    public ResponseEntity<String> update(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                // handle validation errors
                System.out.println("Validation error: " + error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body("Validation errors occurred");
        }

        User defaultUser = new User();
        return ResponseEntity.ok("User updated: " + user.getName() + ", Age: " + user.getAge());
    }

}
