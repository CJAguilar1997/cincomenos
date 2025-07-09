package com.store.cincomenos.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import com.store.cincomenos.domain.dto.persona.employee.jobPosition.DataRegisterJobPosition;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.DataResponseJobPosition;
import com.store.cincomenos.domain.dto.position.PositionFilterDTO;
import com.store.cincomenos.infra.security.TokenService;

import Utils.TestUtils;

@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@SqlGroup({
    @Sql(scripts = {
        "classpath:db/test/save-user.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
})
public class JobPositionControllerTest {

    @Autowired
    private JacksonTester<DataRegisterJobPosition> dataRegisterJacksonTester;
    
    @Autowired
    private JacksonTester<DataResponseJobPosition> dataResponseJacksonTester;
    
    @Autowired
    private JacksonTester<PositionFilterDTO> dataFilterJacksonTester;
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenService tokenService;

    private TestUtils testUtils;

    private String token;

    @BeforeEach
    void setup() {
        testUtils = new TestUtils(tokenService);
        token = testUtils.generateTestJWT();
    }

    @Test
    @Rollback
    @DisplayName("The following test should be return a HTTP 200 when the user introduce a valid name")
    void testRegisterJobPosition200() throws IOException, Exception {

        DataRegisterJobPosition data = new DataRegisterJobPosition("Aseador");

        DataResponseJobPosition responseDto = new DataResponseJobPosition(1l, "ASEADOR");

        var responseTest = mockMvc.perform(post("/position")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), responseTest.getStatus());

        var expectedValue = dataResponseJacksonTester.write(responseDto).getJson();

        assertEquals(responseTest.getContentAsString(), expectedValue);
    }

    @Test
    @Rollback
    @DisplayName("The following test should be return a HTTP 400 when the user introduce a invalid name")
    void testRegisterJobPosition400() throws IOException, Exception {

        DataRegisterJobPosition data = new DataRegisterJobPosition("@seador");

        mockMvc.perform(post("/position")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Rollback
    @DisplayName("The following test should be return a HTTP 403 when the user doesn't introduce a token")
    void testRegisterJobPosition403() throws IOException, Exception {

        DataRegisterJobPosition data = new DataRegisterJobPosition("Aseador");

        mockMvc.perform(post("/position")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andExpect(status().isForbidden());
    }
    
    @Test
    @Rollback
    @DisplayName("The following test should be return a HTTP 200 when the user doesn't introduce parameters to list the positions")
    void testGetListOfJobPosition200WithoutParameters() throws IOException, Exception {
        
        PositionFilterDTO filter = new PositionFilterDTO(null, null);

        mockMvc.perform(get("/position")
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataFilterJacksonTester.write(filter).getJson()))
            .andExpect(status().isOk());
    }

    @Test
    @Rollback
    @DisplayName("The following test should be return a HTTP 200 when the user does introduce id parameter to list a position")
    void testGetListOfJobPosition200WithParametersFirstScenario() throws IOException, Exception {
        
        PositionFilterDTO filter = new PositionFilterDTO(1l, null);

        mockMvc.perform(get("/position")
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataFilterJacksonTester.write(filter).getJson()))
            .andExpect(status().isOk());
    }

    @Test
    @Rollback
    @DisplayName("The following test should be return a HTTP 200 when the user does introduce name parameter to list a position")
    void testGetListOfJobPosition200WithParametersSecondScenario() throws IOException, Exception {
        
        PositionFilterDTO filter = new PositionFilterDTO(null, "Aseador");

        mockMvc.perform(get("/position")
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataFilterJacksonTester.write(filter).getJson()))
            .andExpect(status().isOk());
    }

    @Test
    @Rollback
    @DisplayName("The following test should be return a HTTP 200 when the user does introduce all the parameters to list a position")
    void testGetListOfJobPosition200WithParametersThirthScenario() throws IOException, Exception {
        
        PositionFilterDTO filter = new PositionFilterDTO(1l, "Aseador");

        mockMvc.perform(get("/position")
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataFilterJacksonTester.write(filter).getJson()))
            .andExpect(status().isOk());
    }

    @Test
    @Rollback
    @DisplayName("The following test should return HTTP 400 when the user enters an id that does not exist")
    void testGetListOfJobPosition400FirstScenario() throws IOException, Exception {
        
        PositionFilterDTO filter = new PositionFilterDTO(10000l, null);

        mockMvc.perform(get("/position")
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataFilterJacksonTester.write(filter).getJson()))
            .andExpect(status().isOk());
    }

    @Test
    @Rollback
    @DisplayName("The following test should return HTTP 400 when the user enters an name that does not exist")
    void testGetListOfJobPosition400SecondScenario() throws IOException, Exception {
        
        PositionFilterDTO filter = new PositionFilterDTO(1l, "Traductor");

        mockMvc.perform(get("/position")
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataFilterJacksonTester.write(filter).getJson()))
            .andExpect(status().isOk());
    }

    @Test
    @Rollback
    @DisplayName("The following test should be return a HTTP 403 when the user doesn't introduce a valid token")
    void testGetListOfJobPosition403() throws IOException, Exception {
        
        PositionFilterDTO filter = new PositionFilterDTO(1l, "Aseador");

        mockMvc.perform(get("/position")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataFilterJacksonTester.write(filter).getJson()))
            .andExpect(status().isForbidden());
    }
}
