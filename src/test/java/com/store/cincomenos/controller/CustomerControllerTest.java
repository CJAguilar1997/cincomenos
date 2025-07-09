package com.store.cincomenos.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.store.cincomenos.domain.dto.persona.ContactInformationDTO;
import com.store.cincomenos.domain.dto.persona.customer.DataRegisterCustomer;
import com.store.cincomenos.domain.dto.persona.customer.DataResponseCustomer;
import com.store.cincomenos.domain.dto.persona.customer.DataUpdateCustomer;
import com.store.cincomenos.infra.security.TokenService;

import Utils.TestUtils;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SqlGroup({
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-customer.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_CLASS),
    @Sql(scripts = {
        "classpath:db/test/truncate-customers.sql",
        "classpath:db/test/truncate-user.sql"
    }, executionPhase = ExecutionPhase.AFTER_TEST_CLASS)
})
public class CustomerControllerTest {
    
    @Autowired
    private JacksonTester<DataRegisterCustomer> dataRegisterJacksonTester;

    @Autowired
    private JacksonTester<DataUpdateCustomer> dataUpdateJacksonTester;

    @Autowired
    private JacksonTester<DataResponseCustomer> dataResponseJacksonTester;

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
    @DisplayName("The following test should return an HTTP code 201")
    void testRegisterCustomer201() throws IOException, Exception {
        DataRegisterCustomer data = new DataRegisterCustomer("Fabian Castro", "6546579697", new ContactInformationDTO("65467979", "llaskdf@gmail.com", "In some place"));
        DataResponseCustomer responseDto = new DataResponseCustomer(Long.valueOf(2), "Fabian Castro", "6546579697", new ContactInformationDTO("65467979", "llaskdf@gmail.com", "In some place"));
        
        var testResponse = mockMvc.perform(post("/customer")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), testResponse.getStatus());

        var expectedValue = dataResponseJacksonTester.write(responseDto).getJson();
        assertEquals(expectedValue, testResponse.getContentAsString());
    }

    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 403")
    void testRegisterCustomer403() throws IOException, Exception {
        DataRegisterCustomer data = new DataRegisterCustomer("Fabian Castro", "6546579697", new ContactInformationDTO("65467979", "llaskdf@gmail.com", "In some place"));
        
        var testResponse = mockMvc.perform(post("/customer")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), testResponse.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 400")
    void testRegisterCustomer400() throws IOException, Exception {
        DataRegisterCustomer data = new DataRegisterCustomer("Fabian C@stro", "6546579697", new ContactInformationDTO("65467979", "llaskdf@gmail.com", "In some place"));
        
        var testResponse = mockMvc.perform(post("/customer")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), testResponse.getStatus());
    }
    
    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 200")
    void testGetListOfCustomers200() throws Exception {

        var testResponse = mockMvc.perform(get("/customer")
            .param("id", "1")
            .header("Authorization", token))
            .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), testResponse.getStatus());
    }
    
    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 400")
    void testGetListOfCustomers400() throws Exception {
        
        var testResponse = mockMvc.perform(get("/customer")
        .header("Authorization", token))
        .andReturn().getResponse();
        
        assertEquals(HttpStatus.BAD_REQUEST.value(), testResponse.getStatus());
    }
    
    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 403")
    void testGetListOfCustomers403() throws Exception {

        var testResponse = mockMvc.perform(get("/customer")
            .param("id", "1"))
            .andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), testResponse.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 200")
    void testUpdateCustomer200() throws IOException, Exception {
        DataUpdateCustomer data = new DataUpdateCustomer(Long.valueOf(1), "Perrito dulce", "6547963", new ContactInformationDTO("+504 98763154", "laksdjfjk@gmail.com", "Another place"));
        DataResponseCustomer responseDto = new DataResponseCustomer(Long.valueOf(1), "Perrito dulce", "6547963", new ContactInformationDTO("+504 98763154", "laksdjfjk@gmail.com", "Another place"));

        var testResponse = mockMvc.perform(put("/customer")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataUpdateJacksonTester.write(data).getJson()))
            .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), testResponse.getStatus());

        var expectedValue = dataResponseJacksonTester.write(responseDto).getJson();
        assertEquals(expectedValue, testResponse.getContentAsString());
    }

    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 400")
    void testUpdateCustomer400() throws IOException, Exception {
        DataUpdateCustomer data = new DataUpdateCustomer(Long.valueOf(1), "Perrito-dulce", "6547963", new ContactInformationDTO("+504 98763154", "laksdjfjk@gmail.com", "someplace"));

        var testResponse = mockMvc.perform(put("/customer")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataUpdateJacksonTester.write(data).getJson()))
            .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), testResponse.getStatus());

    }

    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 403")
    void testUpdateCustomer403() throws IOException, Exception {
        DataUpdateCustomer data = new DataUpdateCustomer(Long.valueOf(1), "Perrito dulce", "6547963", new ContactInformationDTO("+504 98763154", "laksdjfjk@gmail.com", "someplace"));

        var testResponse = mockMvc.perform(put("/customer")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataUpdateJacksonTester.write(data).getJson()))
            .andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), testResponse.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 200")
    void testLogicalDeleteCustomer200() throws Exception {

        mockMvc.perform(delete("/customer")
        .param("id", "1")
        .header("Authorization", token))
        .andExpect(status().isNoContent());
    }

    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 400")
    void testLogicalDeleteCustomer400() throws Exception {

        mockMvc.perform(delete("/customer")
        .header("Authorization", token))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 403")
    void testLogicalDeleteCustomer403() throws Exception {

        mockMvc.perform(delete("/customer")
        .param("id", "1"))
        .andExpect(status().isForbidden());
    }


}
