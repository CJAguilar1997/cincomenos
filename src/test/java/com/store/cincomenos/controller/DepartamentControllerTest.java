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

import com.store.cincomenos.domain.dto.departament.DataRegisterDepartament;
import com.store.cincomenos.domain.dto.departament.DataResponseDepartament;
import com.store.cincomenos.domain.dto.departament.DepartamentFilterDTO;
import com.store.cincomenos.infra.security.TokenService;

import Utils.TestUtils;

@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@SqlGroup({
    @Sql(scripts = {
        "classpath:db/test/save-user.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_CLASS),
    @Sql(scripts = {
        "classpath:db/test/truncate-user.sql"
    }, executionPhase = ExecutionPhase.AFTER_TEST_CLASS)
})
public class DepartamentControllerTest {

    @Autowired
    private JacksonTester<DataRegisterDepartament> dataRegisterJacksonTester;
    
    @Autowired
    private JacksonTester<DataResponseDepartament> dataResponseJacksonTester;
    
    @Autowired
    private JacksonTester<DepartamentFilterDTO> dataFilterJacksonTester;
    
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
    void testRegisterDepartament200() throws IOException, Exception {

        DataRegisterDepartament data = new DataRegisterDepartament("Aseo");

        DataResponseDepartament responseDto = new DataResponseDepartament(1l, "ASEO");

        var responseTest = mockMvc.perform(post("/departament")
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
    void testRegisterDepartament400() throws IOException, Exception {

        DataRegisterDepartament data = new DataRegisterDepartament("@seador");

        mockMvc.perform(post("/departament")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Rollback
    @DisplayName("The following test should be return a HTTP 403 when the user doesn't introduce a token")
    void testRegisterDepartament403() throws IOException, Exception {

        DataRegisterDepartament data = new DataRegisterDepartament("Aseo");

        mockMvc.perform(post("/departament")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andExpect(status().isForbidden());
    }
    
    @Test
    @Rollback
    @DisplayName("The following test should be return a HTTP 200 when the user doesn't introduce parameters to list the departaments")
    void testGetListOfDepartament200WithoutParameters() throws IOException, Exception {
        
        DepartamentFilterDTO filter = new DepartamentFilterDTO(null, null);

        mockMvc.perform(get("/departament")
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataFilterJacksonTester.write(filter).getJson()))
            .andExpect(status().isOk());
    }

    @Test
    @Rollback
    @DisplayName("The following test should be return a HTTP 200 when the user does introduce id parameter to list a departament")
    void testGetListOfDepartament200WithParametersFirstScenario() throws IOException, Exception {
        
        DepartamentFilterDTO filter = new DepartamentFilterDTO(1l, null);

        mockMvc.perform(get("/departament")
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataFilterJacksonTester.write(filter).getJson()))
            .andExpect(status().isOk());
    }

    @Test
    @Rollback
    @DisplayName("The following test should be return a HTTP 200 when the user does introduce name parameter to list a departament")
    void testGetListOfDepartament200WithParametersSecondScenario() throws IOException, Exception {
        
        DepartamentFilterDTO filter = new DepartamentFilterDTO(null, "Aseo");

        mockMvc.perform(get("/departament")
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataFilterJacksonTester.write(filter).getJson()))
            .andExpect(status().isOk());
    }

    @Test
    @Rollback
    @DisplayName("The following test should be return a HTTP 200 when the user does introduce all the parameters to list a departament")
    void testGetListOfDepartament200WithParametersThirthScenario() throws IOException, Exception {
        
        DepartamentFilterDTO filter = new DepartamentFilterDTO(1l, "Aseo");

        mockMvc.perform(get("/departament")
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataFilterJacksonTester.write(filter).getJson()))
            .andExpect(status().isOk());
    }

    @Test
    @Rollback
    @DisplayName("The following test should return HTTP 400 when the user enters an id that does not exist")
    void testGetListOfDepartament400FirstScenario() throws IOException, Exception {
        
        DepartamentFilterDTO filter = new DepartamentFilterDTO(10000l, null);

        mockMvc.perform(get("/departament")
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataFilterJacksonTester.write(filter).getJson()))
            .andExpect(status().isOk());
    }

    @Test
    @Rollback
    @DisplayName("The following test should return HTTP 400 when the user enters an name that does not exist")
    void testGetListOfDepartament400SecondScenario() throws IOException, Exception {
        
        DepartamentFilterDTO filter = new DepartamentFilterDTO(1l, "Traductor");

        mockMvc.perform(get("/departament")
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataFilterJacksonTester.write(filter).getJson()))
            .andExpect(status().isOk());
    }

    @Test
    @Rollback
    @DisplayName("The following test should be return a HTTP 403 when the user doesn't introduce a valid token")
    void testGetListOfDepartament403() throws IOException, Exception {
        
        DepartamentFilterDTO filter = new DepartamentFilterDTO(1l, "Aseo");

        mockMvc.perform(get("/departament")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataFilterJacksonTester.write(filter).getJson()))
            .andExpect(status().isForbidden());
    }
}
