package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Insurance {
    @Id
    private String certificateNo;
    private LocalDate startDate;
    private LocalDate endDate;
    private String issuedDate;
    private String insuranceLink;
    private String insuranceCompany;
    private double annualFee;
    @ManyToOne
    private InsuranceTypes insuranceType;
    @ManyToOne
    @JoinColumn(name = "registrationNoFk", referencedColumnName = "registrationNo")
    private Vehicle vehicle;
}
