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
public class VehicleLicense {
    @Id
    private String licenseNo;
    private double annualFee;
    private double arrearsFee;
    private double finesPaid;
    private LocalDate issuedDate;
    private LocalDate expiryDate;
    private String licenseLink;
    @ManyToOne
    @JoinColumn(name = "registrationNoFk", referencedColumnName = "registrationNo")
    private Vehicle vehicle;
}
