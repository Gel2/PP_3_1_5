package ru.kata.spring.boot_security.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminRESTController {
    private final UserService userService;
    private final RoleService roleService;
    private static final Logger logger = LoggerFactory.getLogger(AdminRESTController.class);

    @Autowired
    public AdminRESTController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> usersList = userService.getAllUsers();
        logger.info("Retrieved all users");
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    @PostMapping(value = "/create-user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        userService.create(user);
        logger.info("Created user with id: {}", user.getId());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User userById = userService.getById(id);
        logger.info("Retrieved user with id: {}", id);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @PutMapping(value = "/update-user/{id}")
    public ResponseEntity<HttpStatus> updateUser(@Valid @RequestBody User user, @PathVariable Long id) {
        userService.update(user);
        logger.info("Updated user with id: {}", id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete-user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        logger.info("Deleted user with id: {}", id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/get-roles")
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = roleService.getAllRoles();
        logger.info("Retrieved all roles");
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
