package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAdminPanel(Model model, @AuthenticationPrincipal User user) {
        logger.info("Accessed admin panel by user: {}", user.getUsername());
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("roleList", roleService.getAllRoles());
        model.addAttribute("newUser", new User());
        return "adminPanel";
    }

    @GetMapping("/admin-info")
    public String getAdminProfile(Model model, Principal principal) {
        logger.info("Accessed admin profile by user: {}", principal.getName());
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "adminInfoPage";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") User user) {
        userService.create(user);
        logger.info("User created: {}", user.getUsername());
        return "redirect:/admin";
    }

    @PutMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        logger.info("User updated: {}", user.getUsername());
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        userService.delete(id);
        logger.info("User deleted: {}", user.getUsername());
        return "redirect:/admin";
    }
}

