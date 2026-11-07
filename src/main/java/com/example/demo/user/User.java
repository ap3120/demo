package com.example.demo.user;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", unique=true)
    private Integer userId;
    private String username;

    public User() {}

    public User(String username) {
        this.username = username;
    }

    public User(Integer userId, String username)
    {
        this.userId = userId;
        this.username = username;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public String getUsername()
    {
        return username;
    }
}
