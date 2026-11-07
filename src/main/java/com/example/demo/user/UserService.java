package com.example.demo.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserService
{
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public List<User> getUsers()
    {
        return userRepository.findAll();
    }

    public List<User> getUsersByUsername(String searchField)
    {
        return userRepository.findAll().stream().filter(user -> user.getUsername().toLowerCase().contains(searchField.toLowerCase())).collect(
                Collectors.toList());
    }

    public User addUser(User user)
    {
        return userRepository.save(user);
    }

    @Transactional
    public boolean deleteUser(String username)
    {
        long numberOfDeletedRecords = userRepository.deleteByUsername(username);
        return numberOfDeletedRecords > 0;
    }
}
