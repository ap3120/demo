package com.example.demo.project;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProjectService
{
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository)
    {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public List<Project> getProjects()
    {
        return projectRepository.findAll();
    }

    public List<Project> getProjectsByOwnerId(Integer owner_id)
    {
        return projectRepository.findAll().stream().filter(project -> project.getOwner().getUserId().equals(owner_id))
                .collect(
                        Collectors.toList());
    }

    public Project getProjectsByProjectName(String projectName)
    {
        return projectRepository.findByProjectName(projectName);
    }

    /* To make operation one unit, all changes succeeds, or it rolls back */
    @Transactional
    public Project addProject(Project project)
    {
        Integer user_id = project.getOwner().getUserId();
        User owner = userRepository.findById(user_id)
                .orElseThrow(() -> new EntityNotFoundException("User user_id" + user_id + " not found"));
        project.setOwner(owner);
        return projectRepository.save(project);
    }

    public Project updateProject(Project newProject)
    {
        Optional<Project> existingProject = projectRepository.findById(newProject.getProjectId());
        if (existingProject.isPresent())
        {
            Project project = existingProject.get();
            project.setProjectName(newProject.getProjectName());
            project.setClientName(newProject.getClientName());
            project.setOwner(newProject.getOwner());
            projectRepository.save(project);
            return project;
        }
        return null;
    }

    @Transactional
    public boolean deleteProject(String project_name)
    {
        long numberOfDeletedRecords = projectRepository.deleteByProjectName(project_name);
        return numberOfDeletedRecords > 0;
    }
}
