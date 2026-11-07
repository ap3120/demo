package com.example.demo.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/project")
public class ProjectController
{
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService)
    {
        this.projectService = projectService;
    }

    @GetMapping({"", "/{owner_id}"})
    public List<Project> getProjects(
            @PathVariable(required = false) Integer owner_id,
            @RequestParam(required = false) String projectName
    ) {
        if (owner_id != null) {
            return projectService.getProjectsByOwnerId(owner_id);
        }
        if (projectName != null) {
            return List.of(projectService.getProjectsByProjectName(projectName));
        }
        return projectService.getProjects();
    }

    @PostMapping
    public ResponseEntity<Project> addProject(@RequestBody Project project)
    {
        return new ResponseEntity<>(projectService.addProject(project), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Project> updateProject(@RequestBody Project project)
    {
        Project updatedProject = projectService.updateProject(project);
        if  (updatedProject != null) {
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{projectName}")
    public ResponseEntity<String> deleteProject(@PathVariable String projectName)
    {
        boolean deleted = projectService.deleteProject(projectName);
        if (deleted) {
            return new ResponseEntity<>("Project deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
    }
}
