/*
 * @creator: Oswaldo Montes
 * @date: December 07, 2023
 *
 */
package com.koombea.twitterclone.web.app.services;

import com.koombea.twitterclone.web.app.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
class ValidatorServiceTest extends AbstractIntegrationTest {
    @Autowired
    private ValidatorService validatorService;

    @Test
    void shouldExistsBy() {
        assertTrue(validatorService.existsBy("User", "email", "user@example.com"));
    }

    @Test
    void shouldNotExistsBy() {
        assertFalse(validatorService.existsBy("Post", "message", "whatever"));
    }
}