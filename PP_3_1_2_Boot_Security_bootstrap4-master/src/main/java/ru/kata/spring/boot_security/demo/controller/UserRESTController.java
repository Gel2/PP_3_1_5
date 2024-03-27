package ru.kata.spring.boot_security.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class UserRESTController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserRESTController.class);

    @Autowired
    public UserRESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public User getUser(Principal principal) {
        String username = principal.getName();
        logger.info("Запрос информации о пользователе для пользователя: {}", username);

        User user = userService.findByUsername(username);
        Long id = user.getId();
        User userById = userService.getById(id);

        logger.info("Информация о пользователе успешно получена для пользователя: {}", username);
        return userById;
    }
}
