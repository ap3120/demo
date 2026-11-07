package com.example.demo.project;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.demo.TestsUtils.compareProjects;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest
{
    @Mock
    private ProjectRepository projectRepository;
    private ProjectService projectService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp()
    {
        projectService = new ProjectService(projectRepository, userRepository);
    }

    @Test
    void getProjects() {
        User user = new User();
        Project project1 = new Project(1, "Project1", "TotoClient", user);
        Project project2 = new Project(2, "Project2", "TotoClient", user);
        when(projectRepository.findAll()).thenReturn(Arrays.asList(project1, project2));
        List<Project> projects = projectService.getProjects();
        assertEquals(2, projects.size());
        compareProjects(project1, projects.get(0));
        compareProjects(project2, projects.get(1));
    }

    @Test
    void getProjectByOwnerId()
    {
        User user1 = new User(1, "user1");
        User user2 = new User(2, "user2");
        Project project1 = new Project(1, "Project1", "TotoClient", user1);
        Project project2 = new Project(2, "Project2", "TotoClient", user1);
        Project project3 = new Project(3, "Project3", "TotoClient", user2);
        when(projectRepository.findAll()).thenReturn(Arrays.asList(project1, project2,  project3));
        List<Project> projects = projectService.getProjectsByOwnerId(user1.getUserId());
        assertEquals(2, projects.size());
        compareProjects(project1, projects.get(0));
        compareProjects(project2, projects.get(1));
    }
}
