package com.example.SecuritywithLeaners.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class InsuranceDTO {
    private String certificateNo;
    private LocalDate startDate;
    private LocalDate endDate;
    private String issuedDate;
    private String insuranceLink;
    private String insuranceCompany;
    private String registrationNo;
    private String insuranceType;
    private double annualFee;
    private int validDays;
    private int validMonths;

}
