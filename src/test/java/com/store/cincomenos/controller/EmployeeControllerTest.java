package com.store.cincomenos.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

import com.jayway.jsonpath.JsonPath;
import com.store.cincomenos.domain.dto.persona.ContactInformationDTO;
import com.store.cincomenos.domain.dto.persona.employee.DataRegisterEmployee;
import com.store.cincomenos.domain.dto.persona.employee.DataResponseEmployee;
import com.store.cincomenos.domain.dto.persona.employee.DataUpdateEmployee;
import com.store.cincomenos.domain.dto.persona.employee.EmployeeDeptPositionDTO;
import com.store.cincomenos.domain.dto.persona.employee.departament.RegisEmployeeDepartamentDTO;
import com.store.cincomenos.domain.dto.persona.employee.departament.ResponseDepartamentDTO;
import com.store.cincomenos.domain.dto.persona.employee.departament.UpdateEmployeeDepartamentDTO;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.RegisEmployeePositionDTO;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.ResponsePositionDTO;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.UpdateEmployeePositionDTO;
import com.store.cincomenos.infra.security.TokenService;

import Utils.TestUtils;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
public class EmployeeControllerTest {
    
    @Autowired
    private JacksonTester<DataRegisterEmployee> dataRegisterJacksonTester;

    @Autowired
    private JacksonTester<DataUpdateEmployee> dataUpdateJacksonTester;

    @Autowired
    private JacksonTester<DataResponseEmployee> dataResponseJacksonTester;

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
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql"
        },
         executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-employee.sql",
        "classpath:db/test/truncate-departament-and-position.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP code 201 with upper case in RegisEmployeeDepartamentDTO data characters")
    void testRegisterEmployee201FirstScenario() throws IOException, Exception {

        Set<String> roles = new HashSet<>(List.of("admin"));

        List<RegisEmployeeDepartamentDTO> departaments = new ArrayList<>(List.of(
            new RegisEmployeeDepartamentDTO(
                "CONTABILIDAD", 
                new RegisEmployeePositionDTO("AUXILIAR")
            )
        ));

        DataRegisterEmployee data = new DataRegisterEmployee("Federico Alvarez", "654797963946", roles, new ContactInformationDTO("65477995", "federico.alvarez@gmail.com", "lsdakjflsksdfa"), departaments);
        
        var testResponse = mockMvc.perform(post("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andExpect(status().isCreated())
            .andReturn().getResponse();

        String json = testResponse.getContentAsString();
        String password = JsonPath.read(json, "$.employee_login_data.password");
        assertNotNull(password);
        assertFalse(password.isEmpty());
        assertTrue(password.length() >= 8);
    }

    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql"
        },
         executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-employee.sql",
        "classpath:db/test/truncate-departament-and-position.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP code 201 with lower case in RegisEmployeeDepartamentDTO data characters")
    void testRegisterEmployee201SecondScenario() throws IOException, Exception {

        Set<String> roles = new HashSet<>(List.of("admin"));

        List<RegisEmployeeDepartamentDTO> departaments = new ArrayList<>(List.of(
            new RegisEmployeeDepartamentDTO(
                "contabilidad", 
                new RegisEmployeePositionDTO("auxiliar")
            )
        ));

        DataRegisterEmployee data = new DataRegisterEmployee("Federico Alvarez", "654797963946", roles, new ContactInformationDTO("65477995", "federico.alvarez@gmail.com", "lsdakjflsksdfa"), departaments);
        
        var testResponse = mockMvc.perform(post("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andExpect(status().isCreated())
            .andReturn().getResponse();

        String json = testResponse.getContentAsString();
        String password = JsonPath.read(json, "$.employee_login_data.password");
        assertNotNull(password);
        assertFalse(password.isEmpty());
        assertTrue(password.length() >= 8);
    }

    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql"
        },
         executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-employee.sql",
        "classpath:db/test/truncate-departament-and-position.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP 201 code with upper and lower case letters interspersed in the RegisEmployeeDepartamentDTO data characters")
    void testRegisterEmployee201ThirthScenario() throws IOException, Exception {

        Set<String> roles = new HashSet<>(List.of("admin"));

        List<RegisEmployeeDepartamentDTO> departaments = new ArrayList<>(List.of(
            new RegisEmployeeDepartamentDTO(
                "cONTaBilIdAd", 
                new RegisEmployeePositionDTO("aUxilIar")
            )
        ));

        DataRegisterEmployee data = new DataRegisterEmployee("Federico Alvarez", "654797963946", roles, new ContactInformationDTO("65477995", "federico.alvarez@gmail.com", "lsdakjflsksdfa"), departaments);
        
        var testResponse = mockMvc.perform(post("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andExpect(status().isCreated())
            .andReturn().getResponse();

        String json = testResponse.getContentAsString();
        String password = JsonPath.read(json, "$.employee_login_data.password");
        assertNotNull(password);
        assertFalse(password.isEmpty());
        assertTrue(password.length() >= 8);
    }
    
    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql"
    },
    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-employee.sql",
        "classpath:db/test/truncate-departament-and-position.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP code 400")
    void testRegisterEmployee400() throws IOException, Exception {

        Set<String> roles = new HashSet<>(List.of("admin"));
        
        List<RegisEmployeeDepartamentDTO> departaments = new ArrayList<>(List.of(
            new RegisEmployeeDepartamentDTO(
                "CONTABILIDAD", 
                new RegisEmployeePositionDTO("AUXILIAR")
            )
        ));

        DataRegisterEmployee data = new DataRegisterEmployee("Federico-Alvarez", "654797963946", roles, new ContactInformationDTO("65477995", "federico.alvarez@gmail.com", "lsdakjflsksdfa"), departaments);
        
        mockMvc.perform(post("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse();
    }

    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql"
        },
         executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-employee.sql",
        "classpath:db/test/truncate-departament-and-position.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP code 403")
    void testRegisterEmployee403() throws IOException, Exception {

        Set<String> roles = new HashSet<>(List.of("admin"));

        List<RegisEmployeeDepartamentDTO> departaments = new ArrayList<>(List.of(
            new RegisEmployeeDepartamentDTO(
                "CONTABILIDAD", 
                new RegisEmployeePositionDTO("AUXILIAR")
            )
        ));

        DataRegisterEmployee data = new DataRegisterEmployee("Federico Alvarez", "654797963946", roles, new ContactInformationDTO("65477995", "federico.alvarez@gmail.com", "lsdakjflsksdfa"), departaments);
        
        mockMvc.perform(post("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataRegisterJacksonTester.write(data).getJson()))
            .andExpect(status().isForbidden())
            .andReturn().getResponse();
    }
    
    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql"
    },
         executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-employee.sql",
        "classpath:db/test/truncate-departament-and-position.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP code 200")
    void testGetListOfEmployeeByParametersWithoutParameters200() throws Exception {

        mockMvc.perform(get("/employee")
            .header("Authorization", token))
            .andExpect(status().isOk());
        }
        
        @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql"
    },
    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-employee.sql",
        "classpath:db/test/truncate-departament-and-position.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP code 200")
    void testGetListOfEmployeeByParametersWithParameterId200() throws Exception {
        
        mockMvc.perform(get("/employee")
        .param("id", "1")
        .header("Authorization", token))
            .andExpect(status().isOk());
    }

    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql"
    },
         executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-departament-and-position.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP code 403")
    void testGetListOfEmployeeByParameters403() throws Exception {
        
        mockMvc.perform(get("/employee")
        .param("id", "1"))
        .andExpect(status().isForbidden());
    }
    
    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql",
        "classpath:db/test/save-employee.sql"
    },
    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-departament-and-position.sql",
        "classpath:db/test/truncate-employee.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP code 200")
    void testUpdateEmployeeUpdateDepartament200() throws IOException, Exception {

        List<UpdateEmployeeDepartamentDTO> departaments = new ArrayList<>(List.of(
            new UpdateEmployeeDepartamentDTO(
                "update", 
                "true",
                "CONTABILIDAD", 
                "Informatica",
                new UpdateEmployeePositionDTO(
                    "update", 
                    "true", 
                    "contador", 
                    "desarrollador")
            )
        ));

        List<EmployeeDeptPositionDTO> edpList = List.of(
            new EmployeeDeptPositionDTO(new ResponseDepartamentDTO("INFORMATICA"), new ResponsePositionDTO("DESARROLLADOR")),
            new EmployeeDeptPositionDTO(new ResponseDepartamentDTO("CONTABILIDAD"), new ResponsePositionDTO("AUXILIAR"))
        );

        DataUpdateEmployee data = new DataUpdateEmployee(Long.valueOf(1), null, null, departaments, null);

        DataResponseEmployee responseDto = new DataResponseEmployee(Long.valueOf(1), "Julio Barrientos", "65467866365", edpList, new ContactInformationDTO("+50499687932", "lkdsjlfasd@gmail.com", "oweuroidjsljfaoiue"));
        
        var testResponse = mockMvc.perform(put("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataUpdateJacksonTester.write(data).getJson()))
            .andExpect(status().isOk())
            .andReturn().getResponse();

        var expectedValue = dataResponseJacksonTester.write(responseDto).getJson();
        assertEquals(expectedValue, testResponse.getContentAsString());
    }

    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql",
        "classpath:db/test/save-employee.sql"
    },
    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-departament-and-position.sql",
        "classpath:db/test/truncate-employee.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP code 200")
    void testUpdateEmployeeUpdateOnlyPosition200() throws IOException, Exception {

        List<UpdateEmployeeDepartamentDTO> departaments = new ArrayList<>(List.of(
            new UpdateEmployeeDepartamentDTO(
                "update", 
                "true",
                "cOntaBiLIdAD", 
                null,
                new UpdateEmployeePositionDTO(
                    null, 
                    "true", 
                    "contador", 
                    "asistente")
            )
        ));

        List<EmployeeDeptPositionDTO> edpList = List.of(
            new EmployeeDeptPositionDTO(new ResponseDepartamentDTO("CONTABILIDAD"), new ResponsePositionDTO("ASISTENTE")),
            new EmployeeDeptPositionDTO(new ResponseDepartamentDTO("CONTABILIDAD"), new ResponsePositionDTO("AUXILIAR"))
        );

        DataUpdateEmployee data = new DataUpdateEmployee(Long.valueOf(1), null, null, departaments, null);

        DataResponseEmployee responseDto = new DataResponseEmployee(Long.valueOf(1), "Julio Barrientos", "65467866365", edpList, new ContactInformationDTO("+50499687932", "lkdsjlfasd@gmail.com", "oweuroidjsljfaoiue"));
        
        var testResponse = mockMvc.perform(put("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataUpdateJacksonTester.write(data).getJson()))
            .andExpect(status().isOk())
            .andReturn().getResponse();

        var expectedValue = dataResponseJacksonTester.write(responseDto).getJson();
        assertEquals(expectedValue, testResponse.getContentAsString());
    }

    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql",
        "classpath:db/test/save-employee.sql"
        },
         executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-departament-and-position.sql",
        "classpath:db/test/truncate-employee.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP code 200")
    void testUpdateEmployeeUpdateAddDeptPosition200() throws IOException, Exception {

        List<UpdateEmployeeDepartamentDTO> departaments = new ArrayList<>(List.of(
            new UpdateEmployeeDepartamentDTO(
                null, 
                null,
                null,
                "CONTABILIDAD", 
                new UpdateEmployeePositionDTO(
                    null, 
                    null, 
                    null, 
                    "asistente")
            )
        ));

        List<EmployeeDeptPositionDTO> edpList = List.of(
            new EmployeeDeptPositionDTO(new ResponseDepartamentDTO("CONTABILIDAD"), new ResponsePositionDTO("CONTADOR")),
            new EmployeeDeptPositionDTO(new ResponseDepartamentDTO("CONTABILIDAD"), new ResponsePositionDTO("AUXILIAR")),
            new EmployeeDeptPositionDTO(new ResponseDepartamentDTO("CONTABILIDAD"), new ResponsePositionDTO("ASISTENTE"))
        );

        DataUpdateEmployee data = new DataUpdateEmployee(Long.valueOf(1), null, null, departaments, null);

        DataResponseEmployee responseDto = new DataResponseEmployee(Long.valueOf(1), "Julio Barrientos", "65467866365", edpList, new ContactInformationDTO("+50499687932", "lkdsjlfasd@gmail.com", "oweuroidjsljfaoiue"));
        
        var testResponse = mockMvc.perform(put("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataUpdateJacksonTester.write(data).getJson()))
            .andExpect(status().isOk())
            .andReturn().getResponse();

        var expectedValue = dataResponseJacksonTester.write(responseDto).getJson();
        assertEquals(expectedValue, testResponse.getContentAsString());
    }
    
    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql",
        "classpath:db/test/save-employee.sql"
        },
         executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-departament-and-position.sql",
        "classpath:db/test/truncate-employee.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP code 200")
    void testUpdateEmployeeUpdateDeleteDeptPosition200() throws IOException, Exception {

        List<UpdateEmployeeDepartamentDTO> departaments = new ArrayList<>(List.of(
            new UpdateEmployeeDepartamentDTO(
                "delete", 
                "true",
                "CONTABILIDAD", 
                null,
                new UpdateEmployeePositionDTO(
                    "delete", 
                    "true", 
                    "contador",
                    null)
            )
        ));

        List<EmployeeDeptPositionDTO> edpList = List.of(
            new EmployeeDeptPositionDTO(new ResponseDepartamentDTO("CONTABILIDAD"), new ResponsePositionDTO("AUXILIAR"))
        );

        DataUpdateEmployee data = new DataUpdateEmployee(Long.valueOf(1), null, null, departaments, null);

        DataResponseEmployee responseDto = new DataResponseEmployee(Long.valueOf(1), "Julio Barrientos", "65467866365", edpList, new ContactInformationDTO("+50499687932", "lkdsjlfasd@gmail.com", "oweuroidjsljfaoiue"));
        
        var testResponse = mockMvc.perform(put("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataUpdateJacksonTester.write(data).getJson()))
            .andExpect(status().isOk())
            .andReturn().getResponse();

        var expectedValue = dataResponseJacksonTester.write(responseDto).getJson();
        assertEquals(expectedValue, testResponse.getContentAsString());
    }

    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql",
        "classpath:db/test/save-employee.sql"
    },
         executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-departament-and-position.sql",
        "classpath:db/test/truncate-employee.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP code 200")
    void testUpdateEmployeeUpdateGeneralData200() throws IOException, Exception {

        ContactInformationDTO contact = new ContactInformationDTO("65477995", "federico.alvarez@gmail.com", "lsdakjflsksdfa");

        List<EmployeeDeptPositionDTO> edpList = List.of(
            new EmployeeDeptPositionDTO(new ResponseDepartamentDTO("CONTABILIDAD"), new ResponsePositionDTO("CONTADOR")),
            new EmployeeDeptPositionDTO(new ResponseDepartamentDTO("CONTABILIDAD"), new ResponsePositionDTO("AUXILIAR"))
        );

        DataUpdateEmployee data = new DataUpdateEmployee(Long.valueOf(1), "Juan Carlos Tinoco", "98034682", null, contact);

        DataResponseEmployee responseDto = new DataResponseEmployee(Long.valueOf(1), "Juan Carlos Tinoco", "98034682", edpList, contact);
        
        var testResponse = mockMvc.perform(put("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataUpdateJacksonTester.write(data).getJson()))
            .andExpect(status().isOk())
            .andReturn().getResponse();

        var expectedValue = dataResponseJacksonTester.write(responseDto).getJson();
        assertEquals(expectedValue, testResponse.getContentAsString());
    }

    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql",
        "classpath:db/test/save-employee.sql"
        },
         executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-departament-and-position.sql",
        "classpath:db/test/truncate-employee.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP code 400 when the user misspells the departament name")
    void testUpdateEmployeeUpdateAddDeptPositionFirstScenario400() throws IOException, Exception {
    
        List<UpdateEmployeeDepartamentDTO> departaments = new ArrayList<>(List.of(
            new UpdateEmployeeDepartamentDTO(
                null, 
                null,
                null,
                "CONTABILIDD", 
                new UpdateEmployeePositionDTO(
                    null, 
                    null, 
                    null, 
                    "asistente")
            )
        ));
    
        DataUpdateEmployee data = new DataUpdateEmployee(Long.valueOf(1), null, null, departaments, null);
            
        mockMvc.perform(put("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataUpdateJacksonTester.write(data).getJson()))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse();
    
    }

    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql",
        "classpath:db/test/save-employee.sql"
        },
         executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-departament-and-position.sql",
        "classpath:db/test/truncate-employee.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP code 400 when the user misspells the position name")
    void testUpdateEmployeeUpdateAddDeptPosition400SecondScenario() throws IOException, Exception {
    
        List<UpdateEmployeeDepartamentDTO> departaments = new ArrayList<>(List.of(
            new UpdateEmployeeDepartamentDTO(
                null, 
                null,
                null,
                "CONTABILIDAD", 
                new UpdateEmployeePositionDTO(
                    null, 
                    null, 
                    null, 
                    "asistent")
            )
        ));
    
        DataUpdateEmployee data = new DataUpdateEmployee(Long.valueOf(1), null, null, departaments, null);
            
        mockMvc.perform(put("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataUpdateJacksonTester.write(data).getJson()))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse();
    
    }

    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql",
        "classpath:db/test/save-employee.sql"
        },
         executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-departament-and-position.sql",
        "classpath:db/test/truncate-employee.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP code 400 when the user use invalid characters in the position name")
    void testUpdateEmployeeUpdateAddDeptPosition400ThirthScenario() throws IOException, Exception {
    
        List<UpdateEmployeeDepartamentDTO> departaments = new ArrayList<>(List.of(
            new UpdateEmployeeDepartamentDTO(
                null, 
                null,
                null,
                "CONTABILIDAD", 
                new UpdateEmployeePositionDTO(
                    null, 
                    null, 
                    null, 
                    "@sistent-")
            )
        ));
    
        DataUpdateEmployee data = new DataUpdateEmployee(Long.valueOf(1), null, null, departaments, null);
            
        mockMvc.perform(put("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataUpdateJacksonTester.write(data).getJson()))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse();
    
    }
    
    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql",
        "classpath:db/test/save-employee.sql"
        },
         executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-departament-and-position.sql",
        "classpath:db/test/truncate-employee.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP code 400 when the user use invalid characters in the departament name")
    void testUpdateEmployeeUpdateAddDeptPosition400FourthScenario() throws IOException, Exception {
    
        List<UpdateEmployeeDepartamentDTO> departaments = new ArrayList<>(List.of(
            new UpdateEmployeeDepartamentDTO(
                null, 
                null,
                null,
                "-C0NT@BILIDAD", 
                new UpdateEmployeePositionDTO(
                    null, 
                    null, 
                    null, 
                    "asistent")
            )
        ));
    
        DataUpdateEmployee data = new DataUpdateEmployee(Long.valueOf(1), null, null, departaments, null);
            
        mockMvc.perform(put("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataUpdateJacksonTester.write(data).getJson()))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse();

    }
    
    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql",
        "classpath:db/test/save-employee.sql"
        },
         executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-departament-and-position.sql",
        "classpath:db/test/truncate-employee.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP 400 code when the user enters a position that already exists in the employee's instance")
    void testUpdateEmployeeUpdateAddDeptPosition400FifthScenario() throws IOException, Exception {
        
        List<UpdateEmployeeDepartamentDTO> departaments = new ArrayList<>(List.of(
            new UpdateEmployeeDepartamentDTO(
                null, 
                null,
                null,
                "CONTABILIDAD", 
                new UpdateEmployeePositionDTO(
                    null, 
                    null, 
                    null, 
                    "Contador") //This position is already exists in the employee instance
                    )
        ));
        
        DataUpdateEmployee data = new DataUpdateEmployee(Long.valueOf(1), null, null, departaments, null);
        
        mockMvc.perform(put("/employee")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", token)
        .content(dataUpdateJacksonTester.write(data).getJson()))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse();
        
    }
    
    @Test
    @Rollback
    @Sql(scripts = {
    "classpath:db/test/save-user.sql",
    "classpath:db/test/save-roles.sql",
    "classpath:db/test/save-departament-and-position.sql",
    "classpath:db/test/save-employee.sql"
},
    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
    "classpath:db/test/truncate-departament-and-position.sql",
    "classpath:db/test/truncate-employee.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP 422 code when nameDeptToUpdate is not entered")
    void testUpdateEmployeeUpdate_UpdateDeptPosition422FirstScenario() throws IOException, Exception {

    List<UpdateEmployeeDepartamentDTO> departaments = new ArrayList<>(List.of(
        new UpdateEmployeeDepartamentDTO(
            null, 
            "true",
            null,
            "CONTABILIDAD", 
            new UpdateEmployeePositionDTO(
                null, 
                "true", 
                "programador", 
                "auxiliar")
        )
        ));

        DataUpdateEmployee data = new DataUpdateEmployee(Long.valueOf(1), null, null, departaments, null);
        
        mockMvc.perform(put("/employee")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", token)
        .content(dataUpdateJacksonTester.write(data).getJson()))
        .andExpect(status().isUnprocessableEntity())
        .andReturn().getResponse();
        
    }
    
    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql",
        "classpath:db/test/save-employee.sql"
    },
    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-departament-and-position.sql",
        "classpath:db/test/truncate-employee.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP 422 code when namePositionToUpdate is not entered")
    void testUpdateEmployeeUpdate_UpdateDeptPosition422SecondScenario() throws IOException, Exception {
    
    List<UpdateEmployeeDepartamentDTO> departaments = new ArrayList<>(List.of(
        new UpdateEmployeeDepartamentDTO(
            null, 
            "true",
            "Contabilidad",
            "Informatica", 
            new UpdateEmployeePositionDTO(
                null, 
                "true", 
                null, 
                "auxiliar")
                )
    ));

    DataUpdateEmployee data = new DataUpdateEmployee(Long.valueOf(1), null, null, departaments, null);
    
    mockMvc.perform(put("/employee")
    .contentType(MediaType.APPLICATION_JSON)
    .header("Authorization", token)
    .content(dataUpdateJacksonTester.write(data).getJson()))
    .andExpect(status().isBadRequest())
    .andReturn().getResponse();
    
}
    
    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql",
        "classpath:db/test/save-employee.sql"
    },
    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-departament-and-position.sql",
        "classpath:db/test/truncate-employee.sql"
    }, 
        executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("The following test should return an HTTP 422 code when namePositionToUpdate and nameDeptToUpdate is not entered but the update variable is true")
        void testUpdateEmployeeUpdate_UpdateDeptPosition422ThirthScenario() throws IOException, Exception {

            List<UpdateEmployeeDepartamentDTO> departaments = new ArrayList<>(List.of(
        new UpdateEmployeeDepartamentDTO(
            null, 
            "true",
            null,
            "Informatica", 
            new UpdateEmployeePositionDTO(
                null, 
                "true", 
                null, 
                "auxiliar")
                )
                ));

                DataUpdateEmployee data = new DataUpdateEmployee(Long.valueOf(1), null, null, departaments, null);
                
                mockMvc.perform(put("/employee")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", token)
        .content(dataUpdateJacksonTester.write(data).getJson()))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse();
        
    }

    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql",
        "classpath:db/test/save-employee.sql"
    },
    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-departament-and-position.sql",
        "classpath:db/test/truncate-employee.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP 422 code when the user enters the type as \"delete\" and update value as \"false\" for the departmentDTO and positionDTO")
    void testUpdateEmployeeUpdate_UpdateDeptPosition422FourthScenario() throws IOException, Exception {
    
    List<UpdateEmployeeDepartamentDTO> departaments = new ArrayList<>(List.of(
        new UpdateEmployeeDepartamentDTO(
            "delete", 
            "false",
            "CONTABILIDAD",
            "INFORMATICA", 
            new UpdateEmployeePositionDTO(
                "delete", 
                "false", 
                "AUXILIAR", 
                "programador")
                )
                ));

                DataUpdateEmployee data = new DataUpdateEmployee(Long.valueOf(1), null, null, departaments, null);
                
                mockMvc.perform(put("/employee")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", token)
        .content(dataUpdateJacksonTester.write(data).getJson()))
        .andExpect(status().isUnprocessableEntity())
        .andReturn().getResponse();
        
    }

    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql",
        "classpath:db/test/save-employee.sql"
    },
        executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
        @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-departament-and-position.sql",
        "classpath:db/test/truncate-employee.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP 422 code when the user enters the type as \"delete\" and update not has value for the departmentDTO and positionDTO")
    void testUpdateEmployeeUpdate_UpdateDeptPosition422FifthScenario() throws IOException, Exception {
    
        List<UpdateEmployeeDepartamentDTO> departaments = new ArrayList<>(List.of(
            new UpdateEmployeeDepartamentDTO(
                "delete", 
                null,
                "CONTABILIDAD",
                "Informatica", 
                new UpdateEmployeePositionDTO(
                    "delete", 
                    null, 
                    "auxiliar", 
                    "programador")
            )
            ));
    
            DataUpdateEmployee data = new DataUpdateEmployee(Long.valueOf(1), null, null, departaments, null);
        
        mockMvc.perform(put("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataUpdateJacksonTester.write(data).getJson()))
            .andExpect(status().isUnprocessableEntity())
            .andReturn().getResponse();
            
        }
    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql",
        "classpath:db/test/save-employee.sql"
    },
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-departament-and-position.sql",
        "classpath:db/test/truncate-employee.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP 422 code when the frontend enters the update value as \"false\" for the departmentDTO and positionDTO")
    void testUpdateEmployeeUpdate_UpdateDeptPosition422SixthScenario() throws IOException, Exception {
    
        List<UpdateEmployeeDepartamentDTO> departaments = new ArrayList<>(List.of(
            new UpdateEmployeeDepartamentDTO(
                "update", 
                "false",
                "CONTABILIDAD",
                "informatica", 
                new UpdateEmployeePositionDTO(
                    "update", 
                    "false", 
                    "auxiliar", 
                    "PROGRAMADOR")
                    )
                    ));
    
        DataUpdateEmployee data = new DataUpdateEmployee(Long.valueOf(1), null, null, departaments, null);
        
        mockMvc.perform(put("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataUpdateJacksonTester.write(data).getJson()))
            .andExpect(status().isUnprocessableEntity())
            .andReturn().getResponse();
            
    }

    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql",
        "classpath:db/test/save-employee.sql"
        },
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-departament-and-position.sql",
        "classpath:db/test/truncate-employee.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP 422 code when the frontend wants to update a department and does not enter the update value for departmentDTO and positionDTO; and nameDeptToUpdate and namePosiitonToUpdate are not null")
    void testUpdateEmployeeUpdate_UpdateDeptPosition422SeventhScenario() throws IOException, Exception {
    
        List<UpdateEmployeeDepartamentDTO> departaments = new ArrayList<>(List.of(
            new UpdateEmployeeDepartamentDTO(
                "update", 
                null,
                "contabilidad",
                "informatica", 
                new UpdateEmployeePositionDTO(
                    "update", 
                    null, 
                    "auxiliar", 
                    "programador")
            )
        ));
    
        DataUpdateEmployee data = new DataUpdateEmployee(Long.valueOf(1), null, null, departaments, null);
            
        mockMvc.perform(put("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(dataUpdateJacksonTester.write(data).getJson()))
            .andExpect(status().isUnprocessableEntity())
            .andReturn().getResponse();
    
    }

    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql",
        "classpath:db/test/save-employee.sql"
        },
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-departament-and-position.sql",
        "classpath:db/test/truncate-employee.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP 203 when the user introduce a valid user id to logical delete")
    void testDeleteEmployee203() throws Exception {

        mockMvc.perform(delete("/employee")
            .param("id", "1")
            .header("Authorization", token))
            .andExpect(status().isNoContent());
    }

    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql",
        "classpath:db/test/save-employee.sql"
        },
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-departament-and-position.sql",
        "classpath:db/test/truncate-employee.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP 203 when the user introduce a invalid user id to logical delete")
    void testDeleteEmployee400FirstScenario() throws Exception {

        mockMvc.perform(delete("/employee")
            .param("id", "-11")
            .header("Authorization", token))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql",
        "classpath:db/test/save-employee.sql"
        },
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-departament-and-position.sql",
        "classpath:db/test/truncate-employee.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP 203 when the user introduce an user id how not exists on database ")
    void testDeleteEmployee400SecondScenario() throws Exception {

        mockMvc.perform(delete("/employee")
            .param("id", "10000")
            .header("Authorization", token))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Rollback
    @Sql(scripts = {
        "classpath:db/test/save-user.sql",
        "classpath:db/test/save-roles.sql",
        "classpath:db/test/save-departament-and-position.sql",
        "classpath:db/test/save-employee.sql"
        },
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:db/test/truncate-roles.sql",
        "classpath:db/test/truncate-departament-and-position.sql",
        "classpath:db/test/truncate-employee.sql"
    }, 
    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("The following test should return an HTTP 403 when the user no has the JWT token")
    void testDeleteEmployee403FirstScenario() throws Exception {

        mockMvc.perform(delete("/employee")
            .param("id", "1"))
            .andExpect(status().isForbidden());
    }

}
