package com.example.SecuritywithLeaners.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainerDrivingLicenceDTO {
    private String trainerID;
    private Long id;
    private String licenceURL;
    private LocalDate updatedOrIssuedOn;
    private LocalDate expiryDate;
    private LocalDate expireDateForLightWeight;
    private LocalDate expireDateForHeavy;
    private int monthsForExpiireHevyDuty;
    private int monthsForExpireLightWeight;
    private int daysForExpireLightWeight;
    private int daysForExpireHeavyDuty;
    List<TrainerDrivingLicenceVehicleTypeDTO> trainerDrivingLicenceVehicles;
}
