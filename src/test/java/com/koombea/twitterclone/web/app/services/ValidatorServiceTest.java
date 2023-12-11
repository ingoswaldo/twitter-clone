/*
 * @creator: Oswaldo Montes
 * @date: December 07, 2023
 *
 */
package com.koombea.twitterclone.web.app.services;

import com.koombea.twitterclone.web.app.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ValidatorServiceTest extends AbstractIntegrationTest {
    private final ValidatorService validatorService;

    @Autowired
    public ValidatorServiceTest(ValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    @Test
    void shouldExistsBy() {
        assertTrue(validatorService.existsBy("User", "email", "user@example.com"));
    }

    @Test
    void shouldNotExistsBy() {
        assertFalse(validatorService.existsBy("Post", "message", "whatever"));
    }
}