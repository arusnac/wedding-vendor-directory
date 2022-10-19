package com.arusnac.weddingplanner.controllers;

import com.arusnac.weddingplanner.models.User;
import com.arusnac.weddingplanner.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000/")
@Controller
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @PostMapping(path="/add")
    public @ResponseBody String addNewUser(@RequestParam String username, @RequestParam String email,
                                           @RequestParam String role) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setRole(role);
        userRepo.save(newUser);
        return "User Saved";
    }

    //Get user by id
    @GetMapping(path="/{id}")
    public @ResponseBody Optional<User> getUserById(@PathVariable("id") Integer id){
        return userRepo.findById(id);
    }

    //Get user by email
    @GetMapping(path="/find/{email}")
    public @ResponseBody Optional<User> getUserByEmail(@PathVariable("email") String email){
        return userRepo.findByEmail(email);
    }
}
