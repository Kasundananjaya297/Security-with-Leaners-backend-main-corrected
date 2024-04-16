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
public class EmissionTestDTO {
    private String serialNo;
    private String registrationNo;
    private LocalDate issuedDate;
    private LocalDate expiryDate;
    private String urlOfDocument;
    private double emissionTestFee;
    private int validDays;
    private int validMonths;
}
