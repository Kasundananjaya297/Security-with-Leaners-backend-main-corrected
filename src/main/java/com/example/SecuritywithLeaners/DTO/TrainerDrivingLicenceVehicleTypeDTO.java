package com.example.SecuritywithLeaners.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainerDrivingLicenceVehicleTypeDTO {
    private String typeID;
    private LocalDate updatedOrIssuedOn;
    private String trainerID;
    private String typeName;

}
