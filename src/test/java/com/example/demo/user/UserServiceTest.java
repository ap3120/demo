package com.example.demo.user;

import com.example.demo.TestsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
/* This is to attach the Mockito extension */
public class UserServiceTest
{
    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void setup()
    {
        userService = new UserService(userRepository);
    }

    @Test
    public void shouldGetUsers()
    {
        User user1 = new User(1,"testUser1");
        User user2 = new User(2,"testUser2");
        when(userRepository.findAll()).thenReturn(List.of(user1,user2));

        List<User> users = userService.getUsers();

        assertEquals(2, users.size());
        TestsUtils.compareUsers(user1,users.get(0));
        TestsUtils.compareUsers(user2,users.get(1));
    }

    @Test
    public void shouldGetUserByUsername()
    {
        User user1 = new User(1,"testUser1");
        User user2 = new User(2,"testUser2");
        when(userRepository.findAll()).thenReturn(List.of(user1,user2));

        List<User> users = userService.getUsersByUsername("User1");

        assertEquals(1, users.size());
        TestsUtils.compareUsers(user1,users.get(0));
    }

    @Test
    public void shouldAddUser()
    {
        User user1 = new User(1,"testUser1");

        when(userRepository.save(user1)).thenReturn(user1);

        User user =  userService.addUser(user1);
        TestsUtils.compareUsers(user,user1);
    }

    @Test
    public void shouldDeleteUser()
    {
        when(userRepository.deleteByUsername("testUser")).thenReturn((long) 1);
        boolean userDeleted = userService.deleteUser("testUser");
        assertTrue(userDeleted);
    }
}
