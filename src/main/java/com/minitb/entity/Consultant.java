package com.minitb.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "consultants")
@NoArgsConstructor
@AllArgsConstructor
public class Consultant extends PanacheEntity {

    @Column(name = "first_name", length = 100, nullable = false)
    public String firstName;

    @Column(name = "last_name", length = 100, nullable = false)
    public String lastName;

    @Column(name = "job_title", length = 100)
    public String jobTitle;

    @Column(name = "status", length = 50)
    public String status;

    @Column(name = "hire_date", nullable = false)
    public LocalDate hireDate;

    @Column(name = "email", unique = true)
    public String email;

    @Column(name = "phone_number")
    public String phoneNumber;
}
