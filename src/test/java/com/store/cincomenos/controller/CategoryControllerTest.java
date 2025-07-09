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
import org.springframework.test.web.servlet.MockMvc;

import com.store.cincomenos.domain.dto.product.category.DataRegisterCategory;
import com.store.cincomenos.domain.dto.product.category.DataResponseCategory;
import com.store.cincomenos.domain.dto.product.category.DataUpdateCategory;
import com.store.cincomenos.infra.security.TokenService;

import Utils.TestUtils;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
@SqlGroup({
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql"
    }, 
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS),
    @Sql(scripts = {
        "classpath:db/test/truncate-user.sql",
        "classpath:db/test/truncate-category.sql"
    }, 
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
})
public class CategoryControllerTest {

    @Autowired
    private JacksonTester<DataRegisterCategory> dataRegisterJacksonTester;

    @Autowired
    private JacksonTester<DataUpdateCategory> dataUpdateJacksonTester;

    @Autowired
    private JacksonTester<DataResponseCategory> dataResponseJacksonTester;

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
    void testRegisterCategory201() throws IOException, Exception {

        DataRegisterCategory data = new DataRegisterCategory("carne");
        DataResponseCategory responseDto = new DataResponseCategory(Long.valueOf(5), "CARNE");

        var testResponse = mockMvc.perform(post("/category")
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
    void testRegisterCategory403() throws IOException, Exception {

        DataRegisterCategory data = new DataRegisterCategory("carne");

        var testResponse = mockMvc.perform(post("/category")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), testResponse.getStatus());

    }

    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 400")
    void testRegisterCategory400() throws IOException, Exception {

        DataRegisterCategory data = new DataRegisterCategory("1");

        var testResponse = mockMvc.perform(post("/category")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), testResponse.getStatus());

    }
    
    @Test
    @DisplayName("The following test should return an HTTP code 200")
    void testGetListCategories200() throws Exception {

        var testResponse = mockMvc.perform(get("/category")
            .header("Authorization", token))
            .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), testResponse.getStatus());
    }

    @Test
    @Sql(scripts = "classpath:db/test/save-user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("The following test should return an HTTP code 403")
    void testGetListCategories403() throws Exception {

        var testResponse = mockMvc.perform(get("/category"))
            .andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), testResponse.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 200")
    void testUpdateCategory200() throws IOException, Exception {
        
        DataUpdateCategory data = new DataUpdateCategory(Long.valueOf(1), "lacteos");
        DataResponseCategory responseDto = new DataResponseCategory(Long.valueOf(1), "LACTEOS");

        var testResponse = mockMvc.perform(put("/category")
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
    @DisplayName("The following test should return an HTTP code 403")
    void testUpdateCategory403() throws IOException, Exception {
        
        DataUpdateCategory data = new DataUpdateCategory(Long.valueOf(1), "lacteos");

        var testResponse = mockMvc.perform(put("/category")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataUpdateJacksonTester.write(data).getJson()))
            .andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), testResponse.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 400")
    void testUpdateCategory400() throws IOException, Exception {
        
        DataUpdateCategory data = new DataUpdateCategory(Long.valueOf(-1), "lacteos");

        var testResponse = mockMvc.perform(put("/category")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataUpdateJacksonTester.write(data).getJson()))
            .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), testResponse.getStatus());

    }

    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 203")
    void testDeleteCategory203() throws Exception {
        
        var testResponse = mockMvc.perform(delete("/category")
            .header("Authorization", token)
            .param("id", "1"))
            .andReturn().getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), testResponse.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("The following test should return an HTTP code 403")
    void testDeleteCategory403() throws Exception {
        
        var testResponse = mockMvc.perform(delete("/category"))
            .andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), testResponse.getStatus());
    }
}
