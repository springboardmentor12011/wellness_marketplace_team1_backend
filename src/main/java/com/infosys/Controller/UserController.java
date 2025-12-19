package com.infosys.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.infosys.Service.userService;
import com.infosys.entity.User;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private final userService us;

    public UserController(userService us) {
        super();
        this.us = us;
    }

    // Existing register
    @PostMapping("register")
    public User register(@RequestBody User user) {
        return us.register(user);
    }

    // ✅ New: register as Patient
    @PostMapping("registerPatient")
    public User registerPatient(@RequestBody User user) {
        user.setRole("Patient");
        return us.register(user);
    }

    // ✅ New: register as Practitioner
    @PostMapping("registerPractitioner")
    public User registerPractitioner(@RequestBody User user) {
        user.setRole("Practitioner");
        return us.register(user);
    }

    // Existing login
    @PostMapping("login")
    public String login(@RequestBody User user) {
        boolean isValid = us.login(user.getEmail(), user.getPassword());
        return isValid ? "Login successful" : "Invalid email or password";
    }

    // Existing getUser
    @GetMapping("getUser/{id}")
    public User getUser(@PathVariable Long id) {
        return us.getUserById(id);
    }

    // Existing updateUser
    @PutMapping("updateUser/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return us.updateUser(id, user);
    }

    // Existing deleteUser
    @DeleteMapping("deleteUser/{id}")
    public String deleteUser(@PathVariable Long id) {
        us.deleteUser(id);
        return "User deleted";
    }

    // Existing getAllUsers
    @GetMapping("allUser")
    public List<User> getAllUsers() {
        return us.getAllUsers();
    }
}
