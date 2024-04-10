package com.annamacharya.emptesting.controller;
import com.annamacharya.emptesting.enums.EmploymentStatus;
import com.annamacharya.emptesting.enums.Gender;
import com.annamacharya.emptesting.enums.HireSource;
import com.annamacharya.emptesting.enums.MaritalStatus;
import com.annamacharya.emptesting.model.Employee;
import com.annamacharya.emptesting.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception{

        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Durga Mahesh")
                .lastName("Kasala")
                .email("ramesh@gmail.com")
                .phoneNumber("+917842630339")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.of(2002, 2, 7))
                .hireDate(LocalDate.now()) // Example for hire date
                .jobTitle("Software Engineer")
                .department("Engineering")
                .salary(BigDecimal.valueOf(50000)) // Example for salary
                .address("123 Main St")
                .city("Bangalore")
                .state("Karnataka")
                .postalCode("560001")
                .country("India")
                .maritalStatus(MaritalStatus.SINGLE)
                .emergencyContactName("Venkata")
                .emergencyContactPhone("+919876543210")
                .hireSource(HireSource.REFERRAL)
                .employmentStatus(EmploymentStatus.FULL_TIME)
                .build();
        given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(employee)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",
                        is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(employee.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(employee.getEmail())));

    }

    // JUnit test for Get All employees REST API
    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception{
        // given - precondition or setup
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder()
                .firstName("Durga Mahesh")
                .lastName("Kasala")
                .email("ramesh@gmail.com")
                .phoneNumber("+917842630339")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.of(2002, 2, 7))
                .hireDate(LocalDate.now()) // Example for hire date
                .jobTitle("Software Engineer")
                .department("Engineering")
                .salary(BigDecimal.valueOf(50000)) // Example for salary
                .address("123 Main St")
                .city("Bangalore")
                .state("Karnataka")
                .postalCode("560001")
                .country("India")
                .maritalStatus(MaritalStatus.SINGLE)
                .emergencyContactName("Venkata")
                .emergencyContactPhone("+919876543210")
                .hireSource(HireSource.REFERRAL)
                .employmentStatus(EmploymentStatus.FULL_TIME)
                .build());
        listOfEmployees.add(Employee.builder()
                .firstName("Arjun")
                .lastName("Narayan")
                .email("arjun.narayan@example.com")
                .phoneNumber("+919876543210")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.of(1990, 5, 25))
                .hireDate(LocalDate.now())
                .jobTitle("Senior Software Engineer")
                .department("Engineering")
                .salary(BigDecimal.valueOf(80000))
                .address("789 Temple Street")
                .city("Chennai")
                .state("Tamil Nadu")
                .postalCode("600001")
                .country("India")
                .maritalStatus(MaritalStatus.MARRIED)
                .emergencyContactName("Lakshmi Narayan")
                .emergencyContactPhone("+919876543211")
                .hireSource(HireSource.INTERNAL_PROMOTION)
                .employmentStatus(EmploymentStatus.FULL_TIME)
                .build());
        given(employeeService.getAllEmployees()).willReturn(listOfEmployees);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfEmployees.size())));

    }

    // positive scenario - valid employee id
    // JUnit test for GET employee by id REST API
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Durga Mahesh")
                .lastName("Kasala")
                .email("ramesh@gmail.com")
                .phoneNumber("+917842630339")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.of(2002, 2, 7))
                .hireDate(LocalDate.now()) // Example for hire date
                .jobTitle("Software Engineer")
                .department("Engineering")
                .salary(BigDecimal.valueOf(50000)) // Example for salary
                .address("123 Main St")
                .city("Bangalore")
                .state("Karnataka")
                .postalCode("560001")
                .country("India")
                .maritalStatus(MaritalStatus.SINGLE)
                .emergencyContactName("Venkata")
                .emergencyContactPhone("+919876543210")
                .hireSource(HireSource.REFERRAL)
                .employmentStatus(EmploymentStatus.FULL_TIME)
                .build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }

    // negative scenario - valid employee id
    // JUnit test for GET employee by id REST API
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }
    // JUnit test for update employee REST API - positive scenario
        @Test
        public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception{
            // given - precondition or setup
            long employeeId = 1L;
            Employee savedEmployee = Employee.builder().id(1L)
                    .firstName("Durga Mahesh")
                    .lastName("Kasala")
                    .email("ramesh@gmail.com")
                    .phoneNumber("+917842630339")
                    .gender(Gender.MALE)
                    .dateOfBirth(LocalDate.of(2002, 2, 7))
                    .hireDate(LocalDate.now()) // Example for hire date
                    .jobTitle("Software Engineer")
                    .department("Engineering")
                    .salary(BigDecimal.valueOf(50000)) // Example for salary
                    .address("123 Main St")
                    .city("Bangalore")
                    .state("Karnataka")
                    .postalCode("560001")
                    .country("India")
                    .maritalStatus(MaritalStatus.SINGLE)
                    .emergencyContactName("Venkata")
                    .emergencyContactPhone("+919876543210")
                    .hireSource(HireSource.REFERRAL)
                    .employmentStatus(EmploymentStatus.FULL_TIME)
                    .build();

            Employee updatedEmployee = Employee.builder().id(1L)
                    .firstName("Mahesh")
                    .lastName("Kasala")
                    .email("mahesh145@gmail.com")
                    .phoneNumber("+917842630339")
                    .gender(Gender.MALE)
                    .dateOfBirth(LocalDate.of(2002, 2, 7))
                    .hireDate(LocalDate.now()) // Example for hire date
                    .jobTitle("Software Engineer")
                    .department("Engineering")
                    .salary(BigDecimal.valueOf(50000)) // Example for salary
                    .address("123 Main St")
                    .city("Bangalore")
                    .state("Karnataka")
                    .postalCode("560001")
                    .country("India")
                    .maritalStatus(MaritalStatus.SINGLE)
                    .emergencyContactName("Venkata")
                    .emergencyContactPhone("+919876543210")
                    .hireSource(HireSource.REFERRAL)
                    .employmentStatus(EmploymentStatus.FULL_TIME)
                    .build();

            given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedEmployee));
            given(employeeService.updateEmployee(any(Employee.class)))
                    .willAnswer((invocation)-> invocation.getArgument(0));

            // when -  action or the behaviour that we are going test
            ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(updatedEmployee)));


            // then - verify the output
            response.andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                    .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                    .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));
        }

    // JUnit test for update employee REST API - negative scenario
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;

        Employee updatedEmployee = Employee.builder()
                .id(1L)
                .firstName("Mahesh")
                .lastName("Kasala")
                .email("mahesh145@gmail.com")
                .phoneNumber("+917842630339")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.of(2002, 2, 7))
                .hireDate(LocalDate.now()) // Example for hire date
                .jobTitle("Software Engineer")
                .department("Engineering")
                .salary(BigDecimal.valueOf(50000)) // Example for salary
                .address("123 Main St")
                .city("Bangalore")
                .state("Karnataka")
                .postalCode("560001")
                .country("India")
                .maritalStatus(MaritalStatus.SINGLE)
                .emergencyContactName("Venkata")
                .emergencyContactPhone("+919876543210")
                .hireSource(HireSource.REFERRAL)
                .employmentStatus(EmploymentStatus.FULL_TIME)
                .build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
        given(employeeService.updateEmployee(any(Employee.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

// JUnit test for delete employee REST API
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        willDoNothing().given(employeeService).deleteEmployee(employeeId);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
