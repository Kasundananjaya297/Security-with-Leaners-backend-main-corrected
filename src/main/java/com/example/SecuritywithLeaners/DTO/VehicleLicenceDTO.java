package com.example.SecuritywithLeaners.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleLicenceDTO {
    private String licenseNo;
    private double annualFee;
    private double arrearsFee;
    private double finesPaid;
    private LocalDate issuedDate;
    private LocalDate expiryDate;
    private String licenseLink;
    private String registrationNo;
    private int validMonths;
    private int validDays;
}
