package com.example.demo.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
/* configures the Spring MVC components needed to test the controller without starting the entire application. */
public class UserControllerTest
{
    @Autowired
    /* MockMvc lets you simulate HTTP requests without starting a real web server. */
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void shouldReturnAllUsers() throws Exception
    {
        User user1 = new User("testUser1");
        User user2 = new User("testUser2");

        when(userService.getUsers()).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/api/v1/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testUser1"))
                .andExpect(jsonPath("$[1].username").value("testUser2"));
    }

    @Test
    void shouldReturnFilteredUsers() throws Exception
    {
        User user1 = new User("testUser1");
        User user2 = new User("testUser2");

        when(userService.getUsersByUsername("test")).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/api/v1/user?username=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].username").value("testUser1"))
                .andExpect(jsonPath("$[1].username").value("testUser2"));
    }

    @Test
    void shouldAddUser() throws Exception
    {
        User user1 = new User("testUser1");

        /* Spring creates a new User instance when deserializing the request */
        when(userService.addUser(any(User.class))).thenReturn(user1);

        String json = "{\"username\": \"testUser1\"}";

        mockMvc.perform(
                        post("/api/v1/user")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("testUser1"));
    }

    @Test
    void shouldDeleteUser() throws Exception
    {
        when(userService.deleteUser("testUser")).thenReturn(true);

        mockMvc.perform(delete("/api/v1/user/testUser")).andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }
}
