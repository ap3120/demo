package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController
{
    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers(
            @RequestParam(required = false) String username
    )
    {
        if (username != null)
        {
            return userService.getUsersByUsername(username);
        }
        return userService.getUsers();
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user)
    {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username)
    {
        boolean deleted = userService.deleteUser(username);
        if (deleted)
        {
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
}
