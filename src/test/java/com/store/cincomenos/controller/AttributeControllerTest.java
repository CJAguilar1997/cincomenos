package com.store.cincomenos.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

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
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

import com.store.cincomenos.domain.dto.product.attribute.DataRegisterAttribute;
import com.store.cincomenos.domain.dto.product.attribute.DataResponseAttribute;
import com.store.cincomenos.domain.dto.product.attribute.DataUpdateAttribute;
import com.store.cincomenos.infra.security.TokenService;

import Utils.TestUtils;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
@SqlGroup({
    @Sql(scripts = {
        "classpath:db/test/save-attribute.sql",
        "classpath:db/test/save-user.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_CLASS),
    @Sql(scripts = {
        "classpath:db/test/truncate-attribute.sql",
        "classpath:db/test/truncate-user.sql"
    }, executionPhase = ExecutionPhase.AFTER_TEST_CLASS)
})
public class AttributeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DataRegisterAttribute> dataRegisterJacksonTester;

    @Autowired
    private JacksonTester<DataUpdateAttribute> dataUpdateJacksonTester;
    
    @Autowired
    private JacksonTester<DataResponseAttribute> dataResponseJacksonTester;
    
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
    void testRegister201() throws IOException, Exception {
        
        DataRegisterAttribute data = new DataRegisterAttribute("peso");
        DataResponseAttribute responseDto = new DataResponseAttribute(Long.valueOf(4), "PESO");
        
        var testResponse = mockMvc.perform(post("/attribute")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), testResponse.getStatus());

        var exceptedValue = dataResponseJacksonTester.write(responseDto).getJson();
        assertEquals(exceptedValue, testResponse.getContentAsString());
    }
    
    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 403")
    void testRegister403() throws IOException, Exception {
        
        DataRegisterAttribute data = new DataRegisterAttribute("peso");
        
        var testResponse = mockMvc.perform(post("/attribute")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), testResponse.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 400")
    void testRegister400() throws IOException, Exception {
        
        DataRegisterAttribute data = new DataRegisterAttribute("1");
        
        var testResponse = mockMvc.perform(post("/attribute")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), testResponse.getStatus());
    }
    
    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 200")
    void testGetList200() throws IOException, Exception {

        var testResponse = mockMvc.perform(get("/attribute")
            .header("Authorization", token))    
            .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), testResponse.getStatus());

    }

    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 403")
    void testGetList403() throws IOException, Exception {

        var testResponse = mockMvc.perform(get("/attribute"))    
            .andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), testResponse.getStatus());

    }
    
    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 200")
    void testUpdate200() throws IOException, Exception {
        
        DataUpdateAttribute data = new DataUpdateAttribute(Long.valueOf(1), "EMBASE");
        DataResponseAttribute responseDto = new DataResponseAttribute(Long.valueOf(1), "EMBASE");
        
        var testResponse = mockMvc.perform(put("/attribute")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataUpdateJacksonTester.write(data).getJson()))
            .andReturn().getResponse();
    
        assertEquals(HttpStatus.OK.value(), testResponse.getStatus());
    
        var exceptedValue = dataResponseJacksonTester.write(responseDto).getJson();
        assertEquals(exceptedValue, testResponse.getContentAsString());
    }
    
    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 403")
    void testUpdate403() throws IOException, Exception {
        
        DataUpdateAttribute data = new DataUpdateAttribute(Long.valueOf(1), "peso");
        
        var testResponse = mockMvc.perform(put("/attribute")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataUpdateJacksonTester.write(data).getJson()))
            .andReturn().getResponse();
    
        assertEquals(HttpStatus.FORBIDDEN.value(), testResponse.getStatus());
    }
    
    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 400")
    void testUpdate400() throws IOException, Exception {
        
        DataUpdateAttribute data = new DataUpdateAttribute(Long.valueOf(-1), "name");
        
        var testResponse = mockMvc.perform(put("/attribute")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataUpdateJacksonTester.write(data).getJson()))
            .andReturn().getResponse();
    
        assertEquals(HttpStatus.BAD_REQUEST.value(), testResponse.getStatus());
    }
    
    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 200")
    void testLogicalDelete200() throws IOException, Exception {
        
        var testResponse = mockMvc.perform(delete("/attribute")
        .header("Authorization", token)
            .param("id", "1"))
            .andReturn().getResponse();
    
        assertEquals(HttpStatus.NO_CONTENT.value(), testResponse.getStatus());
    
    }
    
    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 403")
    void testLogicalDelete403() throws IOException, Exception {
    
        var testResponse = mockMvc.perform(delete("/attribute"))
            .andReturn().getResponse();
    
        assertEquals(HttpStatus.FORBIDDEN.value(), testResponse.getStatus());
    
    }

}
