package com.ajack.reactspringbootjpa.controller;

import com.ajack.reactspringbootjpa.model.api.UserApi;
import com.ajack.reactspringbootjpa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserController
{
    private final UserService userService;

    public UserController(
        UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<UserApi> getUser()
    {
        log.debug("Enter UserController.getUser;");

        return ResponseEntity.ok(userService.getUser());
    }
}
