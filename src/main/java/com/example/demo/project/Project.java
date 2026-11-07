package com.example.demo.project;


import com.example.demo.user.User;
import jakarta.persistence.*;

@Entity
@Table(name="projects")
public class Project
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="project_id", unique=true)
    private Integer projectId;
    @Column(name = "project_name", nullable = false)
    private String projectName;
    @Column(name = "client_name")
    private String clientName;
    @ManyToOne()
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id")
    private User owner;

    public Project() {}

    public Project(Integer projectId, String projectName, String clientName, User owner)
    {
        this.projectId = projectId;
        this.projectName = projectName;
        this.clientName = clientName;
        this.owner = owner;
    }

    public Integer getProjectId()
    {
        return projectId;
    }

    public String getProjectName()
    {
        return projectName;
    }

    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public String getClientName()
    {
        return clientName;
    }

    public void setClientName(String clientName)
    {
        this.clientName = clientName;
    }

    public User getOwner()
    {
        return owner;
    }

    public void setOwner(User owner)
    {
        this.owner = owner;
    }
}
