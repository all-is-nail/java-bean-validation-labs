package org.example.webapp.validation.user.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.example.webapp.validation.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class UserController {

    @GetMapping("/user")
    public ResponseEntity<User> getUser(@NotNull String name) {
        User defaultUser = new User();
        defaultUser.setName(name != null ? name : "defaultUser");
        defaultUser.setAge(25);

        return ResponseEntity.ok(defaultUser);
    }

    @PostMapping("/user")
    public ResponseEntity<String> saveUser(@RequestParam @NotNull String name,
                                           @RequestParam @Min(0) @Max(200) Integer age) {
        User user = new User();
        user.setName(name);
        user.setAge(age != null ? age : 18);

        // save the user to a database
        // ...
        return ResponseEntity.ok("User saved: " + user.getName() + ", Age: " + user.getAge());
    }

}
