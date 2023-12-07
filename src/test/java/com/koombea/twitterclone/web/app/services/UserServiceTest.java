/*
 * @creator: Oswaldo Montes
 * @date: December 06, 2023
 *
 */
package com.koombea.twitterclone.web.app.services;

import com.koombea.twitterclone.web.app.models.entities.User;
import com.koombea.twitterclone.web.app.models.projections.user.NamesWithIdOnly;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    @Order(1)
    void shouldCreate() {
        User userCreated = userService.create("email@example.com", "username", "full name", "password");
        assertNotNull(userCreated.getId());
    }

    @Test
    void shouldNotCreateWithDuplicatedEmail() {
        try {
            userService.create("email@example.com", "whatever", "full name", "password");
            fail("Expected exception to be thrown");
        } catch (Exception exception) {
            assertInstanceOf(DataIntegrityViolationException.class, exception);
        }
    }

    @Test
    void shouldNotCreateWithDuplicatedUsername() {
        try {
            userService.create("new_email@example.com", "username", "full name", "password");
            fail("Expected exception to be thrown");
        } catch (Exception exception) {
            assertInstanceOf(DataIntegrityViolationException.class, exception);
        }
    }

    @Test
    void shouldFindIdByUsername() throws EntityNotFoundException {
        assertNotNull(userService.findIdByUsername("username").getId());
    }

    @Test
    void shouldThrowNotFoundWhenFindIdByUsername() throws EntityNotFoundException {
        assertThrows(EntityNotFoundException.class, () -> userService.findIdByUsername("non-registered"));
    }

    @Test
    void shouldFindNamesByUsername() throws EntityNotFoundException {
        NamesWithIdOnly user = userService.findNamesByUsername("username");
        assertInstanceOf(NamesWithIdOnly.class, user);
        assertFalse(user.getId().isEmpty());
        assertEquals(user.getUsername(), "username");
        assertEquals(user.getFullName(), "full name");
        assertEquals(user.getFullNameHumanized(), "Full name");
    }

    @Test
    void shouldThrowNotFoundWhenFindNamesByUsername() throws EntityNotFoundException {
        assertThrows(EntityNotFoundException.class, () -> userService.findNamesByUsername("non-registered"));
    }

    @Test
    void shouldFindUserByUsername() throws EntityNotFoundException {
        User user = userService.findUserByUsername("username");
        assertInstanceOf(User.class, user);
        assertFalse(user.getId().isEmpty());
        assertEquals(user.getUsername(), "username");
        assertEquals(user.getFullName(), "full name");
    }

    @Test
    void shouldThrowNotFoundWhenFindUserByUsername() throws EntityNotFoundException {
        assertThrows(EntityNotFoundException.class, () -> userService.findUserByUsername("non-registered"));
    }

    @Test
    void shouldFindUserById() throws EntityNotFoundException {
        User user = userService.findUserById(userService.findIdByUsername("username").getId());
        assertInstanceOf(User.class, user);
        assertFalse(user.getId().isEmpty());
        assertEquals(user.getUsername(), "username");
        assertEquals(user.getFullName(), "full name");
    }

    @Test
    void shouldThrowNotFoundWhenFindUserById() throws EntityNotFoundException {
        assertThrows(EntityNotFoundException.class, () -> userService.findUserById("whatever1234"));
    }
}
