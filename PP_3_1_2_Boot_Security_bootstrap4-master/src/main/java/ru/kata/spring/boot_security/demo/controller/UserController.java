package ru.kata.spring.boot_security.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getUserProfile(Model model, Principal principal) {
        String username = principal.getName();
        logger.info("Запрос профиля пользователя для пользователя: {}", username);

        model.addAttribute("user", userService.findByUsername(username));

        logger.info("Профиль пользователя успешно получен для пользователя: {}", username);
        return "userInfoPage";
    }
}
