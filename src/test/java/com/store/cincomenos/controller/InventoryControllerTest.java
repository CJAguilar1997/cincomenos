package com.store.cincomenos.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

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
import org.springframework.test.web.servlet.MockMvc;

import com.store.cincomenos.domain.dto.product.DataRegisterProduct;
import com.store.cincomenos.domain.dto.product.DataResponseProduct;
import com.store.cincomenos.domain.dto.product.DataUpdateProduct;
import com.store.cincomenos.domain.dto.product.ProductFilterDTO;
import com.store.cincomenos.domain.dto.product.attribute.AttributeDTO;
import com.store.cincomenos.domain.dto.product.attribute.value.ValueDTO;
import com.store.cincomenos.domain.dto.product.category.CategoryDTO;
import com.store.cincomenos.infra.security.TokenService;

import Utils.TestUtils;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class InventoryControllerTest {

    @Autowired
    private JacksonTester<DataRegisterProduct> dataRegisterJacksonTester;
    
    @Autowired
    private JacksonTester<DataUpdateProduct> dataUpdateJacksonTester;
    
    @Autowired
    private JacksonTester<DataResponseProduct> dataResponseJacksonTester;
    
    @Autowired
    private JacksonTester<ProductFilterDTO> dataFilterJacksonTester;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MockMvc mockMvc;

    private TestUtils testUtils;

    private String token;

    @BeforeEach
    void setup() {
        testUtils = new TestUtils(tokenService);
        token = testUtils.generateTestJWT();
    }
    
    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 201")
    void testRegisterProduct201FirstScenario() throws IOException, Exception {
        
        List<CategoryDTO> categories = List.of( //This list will be ordenated by name in descending order
            new CategoryDTO("Medicamento"),
            new CategoryDTO("Quimico")
        );
        List<AttributeDTO> attributes = List.of( //This list will be ordenated by name in descending order
            new AttributeDTO("Embase", new ValueDTO("PET")),
            new AttributeDTO("Peso", new ValueDTO("500ml"))
        );
        DataRegisterProduct data = new DataRegisterProduct("6546798796", "Sustancia X", "Las super nenas papaaaaa", "Profe Utonio", new BigDecimal(10.00), Long.valueOf(20), categories, attributes);

        DataResponseProduct responseDto = new DataResponseProduct(Long.valueOf(1), "6546798796", "Sustancia X", "Las super nenas papaaaaa", "Profe Utonio", new BigDecimal(10.00), Long.valueOf(20), categories, attributes);

        var testResponse = mockMvc.perform(post("/inventory")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), testResponse.getStatus());

        var expectedValue = dataResponseJacksonTester.write(responseDto).getJson();
        assertEquals(expectedValue, testResponse.getContentAsString());
    }

    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 201 alternating between uppercase and lowercase letters in Category and Attribute name")
    void testRegisterProduct201SecondScenario() throws IOException, Exception {
        
        List<CategoryDTO> categories = List.of( //This list will be ordenated by name in descending order
            new CategoryDTO("MeDiCaMeNtO"),
            new CategoryDTO("QuImIcO")
        );
        List<AttributeDTO> attributes = List.of( //This list will be ordenated by name in descending order
            new AttributeDTO("EmBaSe", new ValueDTO("PET")),
            new AttributeDTO("PeSo", new ValueDTO("500ml"))
        );
        DataRegisterProduct data = new DataRegisterProduct("6546798796", "Sustancia X", "Las super nenas papaaaaa", "Profe Utonio", new BigDecimal(10.00), Long.valueOf(20), categories, attributes);

        DataResponseProduct responseDto = new DataResponseProduct(Long.valueOf(1), "6546798796", "Sustancia X", "Las super nenas papaaaaa", "Profe Utonio", new BigDecimal(10.00), Long.valueOf(20), categories, attributes);

        var testResponse = mockMvc.perform(post("/inventory")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), testResponse.getStatus());

        var expectedValue = dataResponseJacksonTester.write(responseDto).getJson();
        assertEquals(expectedValue, testResponse.getContentAsString());
    }

    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 400 when the user use invalid characters in CategoryDTO")
    void testRegisterProduct400FirstScenario() throws IOException, Exception {
        
        List<CategoryDTO> categories = List.of( //This list will be ordenated by name in descending order
            new CategoryDTO("Medic@ment0"),
            new CategoryDTO("Quimic0")
        );
        List<AttributeDTO> attributes = List.of( //This list will be ordenated by name in descending order
            new AttributeDTO("EmBaSe", new ValueDTO("PET")),
            new AttributeDTO("PeSo", new ValueDTO("500ml"))
        );
        DataRegisterProduct data = new DataRegisterProduct("6546798796", "Sustancia X", "Las super nenas papaaaaa", "Profe Utonio", new BigDecimal(10.00), Long.valueOf(20), categories, attributes);

        mockMvc.perform(post("/inventory")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse();

    }

    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 400 when the user use invalid characters in AttributeDTO")
    void testRegisterProduct400SecondScenario() throws IOException, Exception {
        
        List<CategoryDTO> categories = List.of( //This list will be ordenated by name in descending order
            new CategoryDTO("Medicamento"),
            new CategoryDTO("Quimico")
        );
        List<AttributeDTO> attributes = List.of( //This list will be ordenated by name in descending order
            new AttributeDTO("Emb@se", new ValueDTO("PET")),
            new AttributeDTO("Pes0", new ValueDTO("500ml"))
        );
        DataRegisterProduct data = new DataRegisterProduct("6546798796", "Sustancia X", "Las super nenas papaaaaa", "Profe Utonio", new BigDecimal(10.00), Long.valueOf(20), categories, attributes);

        mockMvc.perform(post("/inventory")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse();

    }

    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql",
        "classpath:db/test/save-product.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-product.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 409 when the user introduce a barcode how is already exists")
    void testRegisterProduct409() throws IOException, Exception {
        
        List<CategoryDTO> categories = List.of( //This list will be ordenated by name in descending order
            new CategoryDTO("Medicamento"),
            new CategoryDTO("Quimico")
        );
        List<AttributeDTO> attributes = List.of( //This list will be ordenated by name in descending order
            new AttributeDTO("Embase", new ValueDTO("PET")),
            new AttributeDTO("Peso", new ValueDTO("500ml"))
        );

        DataRegisterProduct data = new DataRegisterProduct("6546798796", "Sustancia X", "Las super nenas papaaaaa", "Profe Utonio", new BigDecimal(10.00), Long.valueOf(20), categories, attributes);

        mockMvc.perform(post("/inventory")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andExpect(status().isConflict())
            .andReturn().getResponse();

    }

    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql",
        "classpath:db/test/save-product.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-product.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 200 when the user not introduce values")
    void testListProductsWithoutParameters200() throws Exception {

        ProductFilterDTO filter = new ProductFilterDTO(null, null, null, null, null, null, null);
        
        mockMvc.perform(get("/inventory")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataFilterJacksonTester.write(filter).getJson()))
            .andExpect(status().isOk());

    }
    
    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql",
        "classpath:db/test/save-product.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-product.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 200 when the user introduce some values")
    void testListProductsWithParameters200() throws Exception {

        ProductFilterDTO filter = new ProductFilterDTO(1l, "Sustancia X", null, null, null, null, null);
        
        mockMvc.perform(get("/inventory")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataFilterJacksonTester.write(filter).getJson()))
            .andExpect(status().isOk());

    }

    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql",
        "classpath:db/test/save-product.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-product.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 403 when the token field is empty")
    void testListProducts403() throws Exception {

        mockMvc.perform(get("/inventory"))
            .andExpect(status().isForbidden());

    }
    
    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql",
        "classpath:db/test/save-product.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-product.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 200 when the user introduce all params correctly")
    void testUpdateProduct200FirstScenario() throws IOException, Exception {

        List<CategoryDTO> categories = List.of(
            new CategoryDTO("CARNE"),
            new CategoryDTO("procesados")
        );

        List<AttributeDTO> attributes = List.of(
            new AttributeDTO("empaque", new ValueDTO("Paquete plástico")),
            new AttributeDTO("Peso", new ValueDTO("350g"))
        );

        DataUpdateProduct data = new DataUpdateProduct(Long.valueOf(1), "654679763", "Salchicha", "Salchicha con queso", "Frank", new BigDecimal(35.00), categories, attributes);

        DataResponseProduct responseDto = new DataResponseProduct(Long.valueOf(1), "654679763", "Salchicha", "Salchicha con queso", "Frank", new BigDecimal(35.00), Long.valueOf(60), categories, attributes);

        var testResponse = mockMvc.perform(put("/inventory")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataUpdateJacksonTester.write(data).getJson())
            .header("Authorization", token))
            .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), testResponse.getStatus());

        var expectedValue = dataResponseJacksonTester.write(responseDto).getJson();

        assertEquals(expectedValue, testResponse.getContentAsString());
    }

    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql",
        "classpath:db/test/save-product.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-product.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 200 when the user introduce some params correctly")
    void testUpdateProduct200SecondScenario() throws IOException, Exception {

        List<CategoryDTO> categories = List.of(
            new CategoryDTO("carne"),
            new CategoryDTO("PROCESADOS")
        );

        List<AttributeDTO> attributes = List.of(
            new AttributeDTO("eMpAqUe", new ValueDTO("Paquete plástico")),
            new AttributeDTO("PESO", new ValueDTO("350g"))
        );

        DataUpdateProduct data = new DataUpdateProduct(Long.valueOf(1), null, "Salchicha", "Salchicha con queso", null, null, categories, attributes);

        DataResponseProduct responseDto = new DataResponseProduct(Long.valueOf(1), "6546798796", "Salchicha", "Salchicha con queso", "Profe Utonio", new BigDecimal(15.00), Long.valueOf(60), categories, attributes);

        var testResponse = mockMvc.perform(put("/inventory")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataUpdateJacksonTester.write(data).getJson())
            .header("Authorization", token))
            .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), testResponse.getStatus());

        var expectedValue = dataResponseJacksonTester.write(responseDto).getJson();

        assertEquals(expectedValue, testResponse.getContentAsString());
    }

    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql",
        "classpath:db/test/save-product.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-product.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 200 when the user introduce some params correctly second scenario")
    void testUpdateProduct200ThirthScenario() throws IOException, Exception {

        List<CategoryDTO> categories = List.of(
            new CategoryDTO("Quimico")
        );

        List<AttributeDTO> attributes = List.of(
            new AttributeDTO("eMpAqUe", new ValueDTO("Paquete plástico")),
            new AttributeDTO("PESO", new ValueDTO("350g"))
        );

        DataUpdateProduct data = new DataUpdateProduct(Long.valueOf(1), null, "Salchicha", "Salchicha con queso", null, null, null, attributes);

        DataResponseProduct responseDto = new DataResponseProduct(Long.valueOf(1), "6546798796", "Salchicha", "Salchicha con queso", "Profe Utonio", new BigDecimal(15.00), Long.valueOf(60), categories, attributes);

        var testResponse = mockMvc.perform(put("/inventory")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataUpdateJacksonTester.write(data).getJson())
            .header("Authorization", token))
            .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), testResponse.getStatus());

        var expectedValue = dataResponseJacksonTester.write(responseDto).getJson();

        assertEquals(expectedValue, testResponse.getContentAsString());
    }

    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql",
        "classpath:db/test/save-product.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-product.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 200 when the user introduce some params correctly thirth scenario")
    void testUpdateProduct200FourthScenario() throws IOException, Exception {

        List<CategoryDTO> categories = List.of(
            new CategoryDTO("carne"),
            new CategoryDTO("PROCESADOS")
        );

        List<AttributeDTO> attributes = List.of(
            new AttributeDTO("peso", new ValueDTO("350g"))
        );

        DataUpdateProduct data = new DataUpdateProduct(Long.valueOf(1), null, "Salchicha", "Salchicha con queso", null, null, categories, null);

        DataResponseProduct responseDto = new DataResponseProduct(Long.valueOf(1), "6546798796", "Salchicha", "Salchicha con queso", "Profe Utonio", new BigDecimal(15.00), Long.valueOf(60), categories, attributes);

        var testResponse = mockMvc.perform(put("/inventory")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataUpdateJacksonTester.write(data).getJson())
            .header("Authorization", token))
            .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), testResponse.getStatus());

        var expectedValue = dataResponseJacksonTester.write(responseDto).getJson();

        assertEquals(expectedValue, testResponse.getContentAsString());
    }
    
    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql",
        "classpath:db/test/save-product.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-product.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 400 when the user introduce invalid characters in categories")
    void testUpdateProduct400FirstScenario() throws IOException, Exception {
        
        List<CategoryDTO> categories = List.of(
            new CategoryDTO("c@rne"),
            new CategoryDTO("PROCESAD0S")
        );
        
        List<AttributeDTO> attributes = List.of(
            new AttributeDTO("eMpAqUe", new ValueDTO("Paquete plástico")),
            new AttributeDTO("PESO", new ValueDTO("350g"))
        );

        DataUpdateProduct data = new DataUpdateProduct(Long.valueOf(1), null, "Salchicha", "Salchicha con queso", null, null, categories, attributes);

        var testResponse = mockMvc.perform(put("/inventory")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataUpdateJacksonTester.write(data).getJson())
            .header("Authorization", token))
            .andReturn().getResponse();

            assertEquals(HttpStatus.BAD_REQUEST.value(), testResponse.getStatus());
    }

    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql",
        "classpath:db/test/save-product.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-product.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 400 when the user introduce invalid characters in attributes")
    void testUpdateProduct400SecondScenario() throws IOException, Exception {

        List<CategoryDTO> categories = List.of(
            new CategoryDTO("carne"),
            new CategoryDTO("PROCESADOS")
        );
        
        List<AttributeDTO> attributes = List.of(
            new AttributeDTO("Empaque", new ValueDTO("Paquete plástico")),
            new AttributeDTO("PES0", new ValueDTO("350g"))
        );

        DataUpdateProduct data = new DataUpdateProduct(Long.valueOf(1), null, "Salchicha", "Salchicha con queso", null, null, categories, attributes);

        var testResponse = mockMvc.perform(put("/inventory")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataUpdateJacksonTester.write(data).getJson())
            .header("Authorization", token))
            .andReturn().getResponse();

            assertEquals(HttpStatus.BAD_REQUEST.value(), testResponse.getStatus());
    }

    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql",
        "classpath:db/test/save-product.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-product.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 400 when the user introduce invalid characters in barcode")
    void testUpdateProduct400ThirdScenario() throws IOException, Exception {

        List<CategoryDTO> categories = List.of(
            new CategoryDTO("carne"),
            new CategoryDTO("PROCESADOS")
        );

        List<AttributeDTO> attributes = List.of(
            new AttributeDTO("Empaque", new ValueDTO("Paquete plástico")),
            new AttributeDTO("PESO", new ValueDTO("350g"))
        );

        DataUpdateProduct data = new DataUpdateProduct(Long.valueOf(1), "979549-6932", "Salchicha", "Salchicha con queso", null, null, categories, attributes);

        var testResponse = mockMvc.perform(put("/inventory")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataUpdateJacksonTester.write(data).getJson())
            .header("Authorization", token))
            .andReturn().getResponse();

            assertEquals(HttpStatus.BAD_REQUEST.value(), testResponse.getStatus());
    }

    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql",
        "classpath:db/test/save-product.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-product.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 400 when the user introduce invalid characters in name product")
    void testUpdateProduct400FourthScenario() throws IOException, Exception {
        
        List<CategoryDTO> categories = List.of(
            new CategoryDTO("carne"),
            new CategoryDTO("PROCESADOS")
        );
        
        List<AttributeDTO> attributes = List.of(
            new AttributeDTO("Empaque", new ValueDTO("Paquete plástico")),
            new AttributeDTO("PESO", new ValueDTO("350g"))
        );
        
        DataUpdateProduct data = new DataUpdateProduct(Long.valueOf(1), null, "Salchich@", "Salchicha con queso", null, null, categories, attributes);
        
        var testResponse = mockMvc.perform(put("/inventory")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataUpdateJacksonTester.write(data).getJson())
            .header("Authorization", token))
            .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), testResponse.getStatus());
    }

    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql",
        "classpath:db/test/save-product.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-product.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 400 when the user introduce invalid characters in brand product")
    void testUpdateProduct400FifthScenario() throws IOException, Exception {

        List<CategoryDTO> categories = List.of(
            new CategoryDTO("carne"),
            new CategoryDTO("PROCESADOS")
        );
        
        List<AttributeDTO> attributes = List.of(
            new AttributeDTO("Empaque", new ValueDTO("Paquete plástico")),
            new AttributeDTO("PESO", new ValueDTO("350g"))
        );

        DataUpdateProduct data = new DataUpdateProduct(Long.valueOf(1), null, "Salchicha", "Salchicha con queso", "te$t", null, categories, attributes);
        
        var testResponse = mockMvc.perform(put("/inventory")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataUpdateJacksonTester.write(data).getJson())
            .header("Authorization", token))
            .andReturn().getResponse();
            
            assertEquals(HttpStatus.BAD_REQUEST.value(), testResponse.getStatus());
        }

    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql",
        "classpath:db/test/save-product.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-product.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 400 when the user introduce negative values in price variable")
    void testUpdateProduct400SixthScenario() throws IOException, Exception {
        
        List<CategoryDTO> categories = List.of(
            new CategoryDTO("carne"),
            new CategoryDTO("PROCESADOS")
            );
            
            List<AttributeDTO> attributes = List.of(
                new AttributeDTO("Empaque", new ValueDTO("Paquete plástico")),
            new AttributeDTO("PESO", new ValueDTO("350g"))
            );
            
        DataUpdateProduct data = new DataUpdateProduct(Long.valueOf(1), null, "Salchicha", "Salchicha con-ques0", null, new BigDecimal(-51.20), categories, attributes);
        
        var testResponse = mockMvc.perform(put("/inventory")
        .contentType(MediaType.APPLICATION_JSON)
        .content(dataUpdateJacksonTester.write(data).getJson())
        .header("Authorization", token))
            .andReturn().getResponse();
            
        assertEquals(HttpStatus.BAD_REQUEST.value(), testResponse.getStatus());
    }

    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql",
        "classpath:db/test/save-product.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-product.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 403 when the front-end doesn't send a token")
    void testUpdateProduct403FisthScenario() throws IOException, Exception {
    
        List<CategoryDTO> categories = List.of(
            new CategoryDTO("carne"),
            new CategoryDTO("PROCESADOS")
        );
    
        List<AttributeDTO> attributes = List.of(
            new AttributeDTO("eMpAqUe", new ValueDTO("Paquete plástico")),
            new AttributeDTO("PESO", new ValueDTO("350g"))
        );
    
        DataUpdateProduct data = new DataUpdateProduct(Long.valueOf(1), null, "Salchicha", "Salchicha con queso", null, null, categories, attributes);
        
        var testResponse = mockMvc.perform(put("/inventory")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataUpdateJacksonTester.write(data).getJson()))
            .andReturn().getResponse();
    
        assertEquals(HttpStatus.FORBIDDEN.value(), testResponse.getStatus());

    }

    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql",
        "classpath:db/test/save-product.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-product.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 203 when the user enter a valid parameter")
    void testDeleteProduct203() throws Exception {
        
        mockMvc.perform(delete("/inventory")
            .param("id", "1")
            .header("Authorization", token))
            .andExpect(status().isNoContent());
    }
    
    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql",
        "classpath:db/test/save-product.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-product.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 400 when the user enter a invalid parameter")
    void testDeleteProduct400() throws Exception {
        
        mockMvc.perform(delete("/inventory")
            .param("id", "-1")
            .header("Authorization", token))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql",
        "classpath:db/test/save-product.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-product.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 403 when the front-end doesn't send a token")
    void testDeleteProduct403() throws Exception {
        
        mockMvc.perform(delete("/inventory")
            .param("id", "1"))
            .andExpect(status().isForbidden());
    }

    @Test
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql",
        "classpath:db/test/save-product.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-product.sql",
        "classpath:db/test/truncate-attribute.sql"
    },
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Rollback
    @DisplayName("The following test should be return a HTTP code 409 when the user enter a valid parameter but the product not exist")
    void testDeleteProduct409() throws Exception {
        
        mockMvc.perform(delete("/inventory")
            .param("id", "10000")
            .header("Authorization", token))
            .andExpect(status().isConflict());
    }
}
