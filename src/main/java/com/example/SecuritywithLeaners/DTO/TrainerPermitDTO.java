package com.example.SecuritywithLeaners.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainerPermitDTO {
    private LocalDate updatedOrIssuedOn;
    private LocalDate expiryDate;
    private String licenceURL;
    private String trainerID;
}
