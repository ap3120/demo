package com.example.demo.project;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import static com.example.demo.TestsUtils.compareProjects;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
/* Annotation that can be applied to a test class to configure a test database to use instead of the application-defined or auto-configured DataSource */
public class ProjectRepositoryTest
{
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetProjectByProjectName()
    {
        User user = userRepository.save(new User("testUser"));
        Project project = new Project(null, "toto", "client", user);

        projectRepository.save(project);
        Project result = projectRepository.findByProjectName("toto");

        assertNotNull(result);
        compareProjects(project, result);
    }

    @Test
    public void testDeleteProjectByProjectName()
    {
        User user = userRepository.save(new User("testUser"));
        Project project = new Project(null, "toto", "client", user);

        projectRepository.save(project);
        projectRepository.delete(project);
        Project deletedProject = projectRepository.findByProjectName("toto");

        assertNull(deletedProject);
    }

}
