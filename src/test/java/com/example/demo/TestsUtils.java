package com.example.demo;

import com.example.demo.project.Project;
import com.example.demo.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsUtils
{
    public static void compareProjects(Project project1, Project project2)
    {
        compareUsers(project1.getOwner(), project2.getOwner());
        assertEquals(project1.getProjectId(), project2.getProjectId());
        assertEquals(project1.getProjectName(), project2.getProjectName());
        assertEquals(project1.getClientName(), project2.getClientName());
    }

    public static void compareUsers(User user1, User user2)
    {
        assertEquals(user1.getUserId(), user2.getUserId());
        assertEquals(user1.getUsername(), user2.getUsername());
    }
}
