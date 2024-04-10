package com.annamacharya.emptesting.service;


import com.annamacharya.emptesting.enums.EmploymentStatus;
import com.annamacharya.emptesting.enums.Gender;
import com.annamacharya.emptesting.enums.HireSource;
import com.annamacharya.emptesting.enums.MaritalStatus;
import com.annamacharya.emptesting.exception.ResourceNotFoundException;
import com.annamacharya.emptesting.model.Employee;
import com.annamacharya.emptesting.repository.EmployeeRepository;
import com.annamacharya.emptesting.service.Impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup(){
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        //employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
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
    }

    // JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){
        // given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());

        given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        // when -  action or the behaviour that we are going test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        System.out.println(savedEmployee);
        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method which throws exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException(){
        // given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        // when -  action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        // then
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    // JUnit test for getAllEmployees method
    @DisplayName("JUnit test for getAllEmployees method")
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList(){
        // given - precondition or setup

        Employee employee1 = Employee.builder()
                .id(2L)
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
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee,employee1));

        // when -  action or the behaviour that we are going test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    // JUnit test for getAllEmployees method
    @DisplayName("JUnit test for getAllEmployees method (negative scenario)")
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList(){
        // given - precondition or setup

        Employee employee1 = Employee.builder()
                .id(2L)
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
                .build();

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when -  action or the behaviour that we are going test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }

    // JUnit test for getEmployeeById method
    @DisplayName("JUnit test for getEmployeeById method")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject(){
        // given
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // when
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

        // then
        assertThat(savedEmployee).isNotNull();

    }

    // JUnit test for updateEmployee method
    @DisplayName("JUnit test for updateEmployee method")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        // given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("ram@gmail.com");
        employee.setFirstName("Ram");
        // when -  action or the behaviour that we are going test
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        // then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("ram@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Ram");
    }

    // JUnit test for deleteEmployee method
    @DisplayName("JUnit test for deleteEmployee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing(){
        // given - precondition or setup
        long employeeId = 1L;

        willDoNothing().given(employeeRepository).deleteById(employeeId);

        // when -  action or the behaviour that we are going test
        employeeService.deleteEmployee(employeeId);

        // then - verify the output
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}
