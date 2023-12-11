package com.koombea.twitterclone.web.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TwitterCloneApplicationTests extends AbstractIntegrationTest {
    private final MockMvc mockMvc;

    @Autowired
    public TwitterCloneApplicationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @WithMockUser
    public void shouldShowHomePageWhenUserIsAuthenticated() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldNotShowHomePageWhenUserIsUnauthenticated() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isUnauthorized());
    }
}
