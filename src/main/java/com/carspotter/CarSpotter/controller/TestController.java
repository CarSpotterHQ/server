package com.carspotter.CarSpotter.controller;

import com.carspotter.CarSpotter.model.User;
import com.carspotter.CarSpotter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping
    public String test(){
        return "Hello! CarSpotter";
    }

    private final UserRepository userRepository;

    @GetMapping("/test-db")
    public String testDbConnection() {
        try {
            List<User> all = userRepository.findAll();
            return "Database connected successfully!" + all;
        } catch (Exception e) {
            return "Database connection failed: " + e.getMessage();
        }
    }
}
