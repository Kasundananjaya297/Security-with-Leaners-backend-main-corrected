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
    private String stdID;
    private String downURL;
}
