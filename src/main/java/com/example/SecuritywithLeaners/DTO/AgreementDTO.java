package com.example.SecuritywithLeaners.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgreementDTO {
    private String stdID;
    private String packageID;
    private double packagePrice;
    private LocalDate agreementDate;
    private Boolean isFinished;
}
