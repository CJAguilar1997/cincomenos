package com.store.cincomenos.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
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
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import com.store.cincomenos.domain.dto.invoice.DataRegisterInvoice;
import com.store.cincomenos.domain.dto.invoice.DataResponseInvoice;
import com.store.cincomenos.domain.dto.invoice.InvoiceItemsDTO;
import com.store.cincomenos.domain.dto.invoice.ProductDTO;
import com.store.cincomenos.domain.dto.invoice.RegisInvoiceItemDTO;
import com.store.cincomenos.domain.dto.persona.customer.ClienteDTO;
import com.store.cincomenos.infra.security.TokenService;

import Utils.TestUtils;

@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@SqlGroup({
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-customer.sql",
        "classpath:db/test/save-category.sql",
        "classpath:db/test/save-attribute.sql",
        "classpath:db/test/save-product.sql",
        "classpath:db/test/save-invoices.sql"
    }, executionPhase = ExecutionPhase.BEFORE_TEST_CLASS),
    @Sql(scripts = {
        "classpath:db/test/truncate-category.sql",
        "classpath:db/test/truncate-product.sql",
        "classpath:db/test/truncate-attribute.sql",
        "classpath:db/test/truncate-invoices.sql",
        "classpath:db/test/truncate-customers.sql",
        "classpath:db/test/truncate-user.sql"
    }, executionPhase = ExecutionPhase.AFTER_TEST_CLASS)
})
public class InvoiceControllerTest {

    @Autowired
    private JacksonTester<DataRegisterInvoice> dataRegisterJacksonTester;
    
    @Autowired
    private JacksonTester<DataResponseInvoice> dataResponseJacksonTester;
    
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
    @DisplayName("The following test should be return a HTTP code 200 if the user enter valid data")
    void testRegisterInvoice200FirstScenario() throws IOException, Exception {
        List<RegisInvoiceItemDTO> items = List.of(
            new RegisInvoiceItemDTO("6546798796", 5),
            new RegisInvoiceItemDTO("69877521456", 5)
        );

        List<InvoiceItemsDTO> itemsResponse = List.of(
            new InvoiceItemsDTO(3l, 
                5,
                new BigDecimal(15.00), 
                new BigDecimal(75.00), 
                new ProductDTO("6546798796", "Sustancia X", "Las super nenas papaaaaa", "Profe Utonio")),

            new InvoiceItemsDTO(4l, 
                5,
                new BigDecimal(15.00), 
                new BigDecimal(75.00), 
                new ProductDTO("69877521456", "Sustancia Y", "Las super chicas mamaaaaaa", "Profe Plutonio"))
        );

        DataRegisterInvoice data = new DataRegisterInvoice(1l, items);

        DataResponseInvoice responseDto = new DataResponseInvoice(
            2l, 
            LocalDate.now(), 
            new ClienteDTO(
                1l,
                "Perrito Fiel",
                "5879569",
                "+50488935469"
            ),
            itemsResponse,
            new BigDecimal(150)
        );

        var testResponse = mockMvc.perform(post("/invoices")
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
    @DisplayName("The following test should be return a HTTP code 200 when the user enters two times one product")
    void testRegisterInvoice200SecondScenario() throws IOException, Exception {
        List<RegisInvoiceItemDTO> items = List.of(
            new RegisInvoiceItemDTO("6546798796", 5),
            new RegisInvoiceItemDTO("6546798796", 5),
            new RegisInvoiceItemDTO("69877521456", 5)
        );

        List<InvoiceItemsDTO> itemsResponse = List.of(
            new InvoiceItemsDTO(1l, 
                10,
                new BigDecimal(15.00), 
                new BigDecimal(150.00), 
                new ProductDTO("6546798796", "Sustancia X", "Las super nenas papaaaaa", "Profe Utonio")),

            new InvoiceItemsDTO(2l, 
                5,
                new BigDecimal(15.00), 
                new BigDecimal(75.00), 
                new ProductDTO("69877521456", "Sustancia Y", "Las super chicas mamaaaaaa", "Profe Plutonio"))
        );

        DataRegisterInvoice data = new DataRegisterInvoice(1l, items);

        DataResponseInvoice responseDto = new DataResponseInvoice(
            1l, 
            LocalDate.now(), 
            new ClienteDTO(
                1l,
                "Perrito Fiel",
                "5879569",
                "+50488935469"
            ),
            itemsResponse,
            new BigDecimal(225)
        );

        var testResponse = mockMvc.perform(post("/invoices")
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
    @DisplayName("The following test should be return a HTTP code 400 when the user enters an invalid barcode")
    void testRegisterInvoice400FirstScenario() throws IOException, Exception {
        List<RegisInvoiceItemDTO> items = List.of(
            new RegisInvoiceItemDTO("6798796", 5),
            new RegisInvoiceItemDTO("69877521456", 5)
        );

        DataRegisterInvoice data = new DataRegisterInvoice(1l, items);

        var testResponse = mockMvc.perform(post("/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), testResponse.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("The following test should be return a HTTP code 400 when the user enters an customer id that does not exist")
    void testRegisterInvoice400SecondScenario() throws IOException, Exception {
        List<RegisInvoiceItemDTO> items = List.of(
            new RegisInvoiceItemDTO("69877521456", 5)
        );

        DataRegisterInvoice data = new DataRegisterInvoice(155500l, items);

        var testResponse = mockMvc.perform(post("/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), testResponse.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("The following test should be return a HTTP code 400 when the user does not enter a token")
    void testRegisterInvoice403() throws IOException, Exception {
        List<RegisInvoiceItemDTO> items = List.of(
            new RegisInvoiceItemDTO("69877521456", 5)
        );

        DataRegisterInvoice data = new DataRegisterInvoice(1l, items);

        var testResponse = mockMvc.perform(post("/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), testResponse.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("The following test should be return a HTTP code 409 when the user enters an amount out of stock to a product")
    void testRegisterInvoice409() throws IOException, Exception {
        List<RegisInvoiceItemDTO> items = List.of(
            new RegisInvoiceItemDTO("69877521456", 100)
        );

        DataRegisterInvoice data = new DataRegisterInvoice(1l, items);
        
        var testResponse = mockMvc.perform(post("/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andReturn().getResponse();

        assertEquals(HttpStatus.CONFLICT.value(), testResponse.getStatus());
    }

    @Test
    @SqlGroup({
        @Sql(scripts = "classpath:db/test/save-invoices.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:db/test/truncate-invoices.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    })
    @Rollback
    @DisplayName("The following test should be return a HTTP code 200 when the user list the invoices with invoice id")
    void testListInvoiceByParameters200FirstScenario() throws Exception {
        
        mockMvc.perform(get("/invoices")
            .header("Authorization", token)
            .param("invoice_id", "1"))
            .andExpect(status().isOk());
        
    }

    @Test
    @SqlGroup({
        @Sql(scripts = "classpath:db/test/save-invoices.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:db/test/truncate-invoices.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    })
    @Rollback
    @DisplayName("The following test should be return a HTTP code 200 when the user list the invoices with customer id")
    void testListInvoiceByParameters200SecondScenario() throws Exception {
        
        mockMvc.perform(get("/invoices")
            .header("Authorization", token)
            .param("customer_id", "1"))
            .andExpect(status().isOk());
        
    }

    @Test
    @SqlGroup({
        @Sql(scripts = "classpath:db/test/save-invoices.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:db/test/truncate-invoices.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    })
    @Rollback
    @DisplayName("The following test should be return a HTTP code 400 when the user tries to list invoices with a customer ID that does not exist")
    void testListInvoiceByParameters400FirstScenario() throws Exception {
        
        mockMvc.perform(get("/invoices")
            .header("Authorization", token)
            .param("customer_id", "100000"))
            .andExpect(status().isBadRequest());
        
    }

    @Test
    @SqlGroup({
        @Sql(scripts = "classpath:db/test/save-invoices.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:db/test/truncate-invoices.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    })
    @Rollback
    @DisplayName("The following test should be return a HTTP code 400 when the user tries to list invoices with a invoice ID that does not exist")
    void testListInvoiceByParameters400SecondScenario() throws Exception {
        
        mockMvc.perform(get("/invoices")
            .header("Authorization", token)
            .param("invoice_id", "100000"))
            .andExpect(status().isOk());
        
    }

    @Test
    @SqlGroup({
        @Sql(scripts = "classpath:db/test/save-invoices.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:db/test/truncate-invoices.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    })
    @Rollback
    @DisplayName("The following test should be return a HTTP code 403 when the user does not enter a token")
    void testListInvoiceByParameters403() throws Exception {
        
        mockMvc.perform(get("/invoices")
            .param("invoice_id", "1"))
            .andExpect(status().isForbidden());
        
    }
    
}
