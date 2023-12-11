/*
 * @creator: Oswaldo Montes
 * @date: December 07, 2023
 *
 */
package com.koombea.twitterclone.web.app;

import com.koombea.twitterclone.web.app.repositories.UserRepository;
import com.koombea.twitterclone.web.app.services.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@AutoConfigureTestDatabase
public abstract class AbstractIntegrationTest {
    @BeforeAll
    static void beforeAll(@Autowired UserService userService) {
        userService.create("user@example.com", "user", "full name", "password");
    }

    @AfterAll
    static void afterAll(@Autowired UserRepository userRepository) {
        userRepository.deleteAll();
    }

    protected Pageable getPageable() {
        return PageRequest.of(0,2);
    }
}
