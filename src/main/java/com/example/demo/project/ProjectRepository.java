package com.example.demo.project;

import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>
{
    long deleteByProjectName(String project_name);
    Project findByProjectName(String project_name);
    @Query("""
       SELECT u.username AS username,
              p.projectName AS projectName
       FROM Project p
       JOIN p.owner u
       WHERE p.projectName = projectName
       """)
    List<User> findUsersByProjectName(
            @Param("projectName") String projectName
    );
}
