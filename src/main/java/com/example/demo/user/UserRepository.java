package com.example.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    /* Spring Data JPA (Java Persistent API) recognizes the method name and derives the corresponding database operation from it */
    void deleteById(Integer user_id);
    long deleteByUsername(String username);
    Optional<User> findByUsername(String username);
}
