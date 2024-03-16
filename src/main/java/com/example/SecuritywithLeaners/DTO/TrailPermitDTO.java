package com.example.SecuritywithLeaners.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrailPermitDTO {
    private String serialNo;
    private LocalDate examDate;
    private LocalDate expDate;
    private boolean b1M;
    private boolean bM;
    private boolean bA;
    private boolean a1M;
    private boolean a1A;
    private boolean aM;
    private String stdID;
}
