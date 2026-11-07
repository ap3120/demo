package com.example.demo.project;

import com.example.demo.user.User;
import com.example.demo.user.UserService;
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

/*
Mockito is an open-source mocking framework used for unit testing in Java.
It allows developers to create mock objects (fake objects) that simulate the behavior of real dependencies.
By replacing real objects with mocks, Mockito helps test code in isolation without calling actual databases, APIs, or external services.
    - Faster Tests: Avoid slow operations like database or network calls.
    - Cleaner Code: Reduce boilerplate with annotations like @Mock and @InjectMocks.
 */

@WebMvcTest(ProjectController.class)
/* configures the Spring MVC components needed to test the controller without starting the entire application. */
public class ProjectControllerTest
{
    @Autowired
    /* MockMvc lets you simulate HTTP requests without starting a real web server. */
    private MockMvc mockMvc;

    /* Spring testing annotation that replaces a real bean in the test’s ApplicationContext with a Mockito mock */
    @MockitoBean
    private ProjectService projectService;
    @MockitoBean
    private UserService userService;

    @Test
    void shouldReturnAllProjects() throws Exception
    {
        User user = new User("testUser");
        Project project1 = new Project(1, "testProject1", "testClient", user);
        Project project2 = new Project(2, "testProject2", "testClient", user);

        when(projectService.getProjects()).thenReturn(List.of(project1, project2));

        mockMvc.perform(get("/api/v1/project")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].projectId").value(1))
                .andExpect(jsonPath("$[0].projectName").value("testProject1"))
                .andExpect(jsonPath("$[0].clientName").value("testClient"))
                .andExpect(jsonPath("$[0].owner.username").value("testUser"))
                .andExpect(jsonPath("$[1].projectId").value(2))
                .andExpect(jsonPath("$[1].projectName").value("testProject2"))
                .andExpect(jsonPath("$[1].clientName").value("testClient"))
                .andExpect(jsonPath("$[1].owner.username").value("testUser"));
    }

    @Test
    void shouldReturnProjectByOwnerId() throws Exception
    {
        User user1 = new User(1, "testUser1");
        Project project1 = new Project(1, "testProject1", "testClient", user1);

        when(projectService.getProjectsByOwnerId(1)).thenReturn(List.of(project1));

        mockMvc.perform(get("/api/v1/project/{owner_id}", "1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].projectId").value(1))
                .andExpect(jsonPath("$[0].projectName").value("testProject1"))
                .andExpect(jsonPath("$[0].clientName").value("testClient"))
                .andExpect(jsonPath("$[0].owner.username").value("testUser1"));
    }

    @Test
    void shouldReturnProjectByProjectName() throws Exception
    {
        User user1 = new User(1, "testUser1");
        Project project1 = new Project(1, "testProject1", "testClient", user1);

        when(projectService.getProjectsByProjectName("testProject1")).thenReturn(project1);

        mockMvc.perform(get("/api/v1/project?projectName=testProject1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].projectId").value(1))
                .andExpect(jsonPath("$[0].projectName").value("testProject1"))
                .andExpect(jsonPath("$[0].clientName").value("testClient"))
                .andExpect(jsonPath("$[0].owner.username").value("testUser1"));
    }

    @Test
    void shouldAddProject() throws Exception
    {
        User user = new User(1, "testUser");
        Project project = new Project(1, "testProject", "testClient", user);

        when(projectService.addProject(any(Project.class))).thenReturn(project);

        String json = "{\"projectId\":1,\"projectName\":\"testProject\",\"clientName\":\"testClient\",\"owner\":{\"userId\":1,\"username\":\"testUser\"}}";
        mockMvc.perform(post("/api/v1/project").content(json).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.projectId").value(1))
                .andExpect(jsonPath("$.projectName").value("testProject"))
                .andExpect(jsonPath("$.clientName").value("testClient"))
                .andExpect(jsonPath("$.owner.username").value("testUser"));
    }

    @Test
    void shouldUpdateProject() throws Exception
    {
        User user = new User(1, "testUser");
        Project project = new Project(1, "testProject", "testClient", user);

        when(projectService.updateProject(any(Project.class))).thenReturn(project);

        String json = "{\"projectId\":1,\"projectName\":\"testProject\",\"clientName\":\"testClient\",\"owner\":{\"userId\":1,\"username\":\"testUser\"}}";
        mockMvc.perform(put("/api/v1/project").content(json).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.projectId").value(1))
                .andExpect(jsonPath("$.projectName").value("testProject"))
                .andExpect(jsonPath("$.clientName").value("testClient"))
                .andExpect(jsonPath("$.owner.username").value("testUser"));
    }

    @Test
    void shouldDeleteProject() throws Exception
    {
        when(projectService.deleteProject("testProject")).thenReturn(true);
        mockMvc.perform(delete("/api/v1/project/testProject")).andExpect(status().isOk())
                .andExpect(content().string("Project deleted successfully"));
    }
}
