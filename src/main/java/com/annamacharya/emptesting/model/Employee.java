package com.annamacharya.emptesting.model;

import com.annamacharya.emptesting.enums.Gender;
import com.annamacharya.emptesting.enums.HireSource;
import com.annamacharya.emptesting.enums.MaritalStatus;
import com.annamacharya.emptesting.enums.EmploymentStatus;

import lombok.*;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name="email",nullable = false)
    private String email;

    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender",nullable=false)
    private Gender gender;

    @Column(name = "date_of_birth",nullable=false)
    private LocalDate dateOfBirth;

    @Column(name = "hire_date",nullable=false)
    private LocalDate hireDate;


    @Column(name = "job_title",nullable=false)
    private String jobTitle;

    @Column(name = "department",nullable=false)
    private String department;

    @Column(name = "salary",nullable=false)
    private BigDecimal salary;

    @Column(name = "address",nullable=false)
    private String address;


    @Column(name = "city",nullable=false)
    private String city;


    @Column(name = "state",nullable=false)
    private String state;


    @Column(name = "postal_code",nullable=false)
    private String postalCode;

    @Column(name = "country",nullable=false)
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status",nullable=false)
    private MaritalStatus maritalStatus;

    @Column(name = "emergency_contact_name",nullable=false)
    private String emergencyContactName;

    @Column(name = "emergency_contact_phone",nullable=false)
    private String emergencyContactPhone;

    @Enumerated(EnumType.STRING)
    @Column(name = "hire_source",nullable=false)
    private HireSource hireSource;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_status",nullable=false)
    private EmploymentStatus employmentStatus;
    // Getters and setters for enums
}
