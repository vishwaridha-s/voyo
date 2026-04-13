package com.example.travellapp.controller;

import com.example.travellapp.models.User;
import com.example.travellapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/voyo")
public class userController {
    @Autowired
    UserService userservice;

    @PostMapping("/register")
    private ResponseEntity<?>register(@RequestBody User u){
        try{
            User user=userservice.register(u);
            return new ResponseEntity<>(user, HttpStatus.CREATED);

        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        try {
            User user = userservice.getUserById(id);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/login")
    private ResponseEntity<?>login(@RequestBody User u){
        String username=u.getUsername();
        String password=u.getPassword();
        try {
            User existingUser = userservice.login(u.getUsername(), u.getPassword());
            if (existingUser != null) {
                String role = existingUser.getRole();
                Map<String, Object> response = new HashMap<>();
                response.put("userId", existingUser.getId());

                if ("user".equalsIgnoreCase(role)) {
                    response.put("path", "/user-places");
                } else {
                    response.put("path", "/places");
                }

                return ResponseEntity.ok(response);
            }
            else {
                return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

