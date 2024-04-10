package com.annamacharya.emptesting.repository;

import com.annamacharya.emptesting.enums.EmploymentStatus;
import com.annamacharya.emptesting.enums.Gender;
import com.annamacharya.emptesting.enums.HireSource;
import com.annamacharya.emptesting.enums.MaritalStatus;
import com.annamacharya.emptesting.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee=null;
    @BeforeEach
    public void setup(){
         //given - precondition or setup
        employee =Employee.builder()
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
    // JUnit test for save employee operation
    @DisplayName("JUnit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.save(employee);
        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }


    // JUnit test for get all employees operation
    @DisplayName("JUnit test for get all employees operation")
    @Test
    public void givenEmployeesList_whenFindAll_thenEmployeesList(){
        Employee employee1 = Employee.builder()
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

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        // when -  action or the behaviour that we are going test
        List<Employee> employeeList = employeeRepository.findAll();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);

    }

    // JUnit test for get employee by id operation
    @DisplayName("JUnit test for get employee by id operation")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject(){
        employeeRepository.save(employee);

        // when -  action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    // JUnit test for get employee by email operation
    @DisplayName("JUnit test for get employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject(){
        employeeRepository.save(employee);

        // when -  action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    // JUnit test for update employee operation
    @DisplayName("JUnit test for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){

        employeeRepository.save(employee);

        // when -  action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("ram@gmail.com");
        savedEmployee.setFirstName("Ram");
        Employee updatedEmployee =  employeeRepository.save(savedEmployee);

        // then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("ram@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Ram");
    }

    // JUnit test for delete employee operation
    @DisplayName("JUnit test for delete employee operation")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee(){

        employeeRepository.save(employee);

        // when -  action or the behaviour that we are going test
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        // then - verify the output
        assertThat(employeeOptional).isEmpty();
    }

    // JUnit test for custom query using JPQL with index
    @DisplayName("JUnit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject(){
        // given - precondition or setup
        employeeRepository.save(employee);
        // when -  action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByJPQL(employee.getFirstName(), employee.getLastName());
        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for custom query using JPQL with Named params
    @DisplayName("JUnit test for custom query using JPQL with Named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject(){
        // given - precondition or setup
        employeeRepository.save(employee);
        // when -  action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(employee.getFirstName(), employee.getLastName());
        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for custom query using native SQL with index
    @DisplayName("JUnit test for custom query using native SQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject(){
        // given - precondition or setup
        employeeRepository.save(employee);
        // when -  action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());
        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for custom query using native SQL with named params
    @DisplayName("JUnit test for custom query using native SQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject(){
        // given - precondition or setup
        employeeRepository.save(employee);
        // when -  action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQLNamed(employee.getFirstName(), employee.getLastName());
        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }
}
