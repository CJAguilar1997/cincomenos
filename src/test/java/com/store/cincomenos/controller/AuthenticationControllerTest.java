package com.store.cincomenos.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.store.cincomenos.domain.dto.persona.login.DataAuthenticationUser;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
@Sql(scripts = {
    "classpath:db/test/save-user.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DataAuthenticationUser> dataAuthenticationJacksonTester;

    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 200")
    void testAuthenticateUser200() throws IOException, Exception {

        DataAuthenticationUser data = new DataAuthenticationUser("teodoro@gmail.com", "12345678");
        
        mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataAuthenticationJacksonTester.write(data).getJson()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").exists())
            .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 403")
    void testAuthenticateUser403() throws IOException, Exception {

        DataAuthenticationUser data = new DataAuthenticationUser("teodoro-gonzales@gmail.com", "12345678");
        
        mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataAuthenticationJacksonTester.write(data).getJson()))
            .andExpect(status().isForbidden())
            .andExpect(content().string(""));
    }
}
