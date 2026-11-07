package com.example.demo.user;

import com.example.demo.TestsUtils;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest
{
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        User user1 = new  User("testUser1");
        User user2 = new  User("testUser2");

        userRepository.save(user1);
        userRepository.save(user2);
        User user = userRepository.findByUsername("testUser1").orElseThrow(() -> new EntityNotFoundException("User testUser1 not found"));

        TestsUtils.compareUsers(user1, user);
    }

    @Test
    public void testDeleteUserById()
    {
        User user = new User("testUser");

        userRepository.save(user);
        userRepository.deleteById(1);

        assertFalse(userRepository.findById(1).isPresent());
    }
}
