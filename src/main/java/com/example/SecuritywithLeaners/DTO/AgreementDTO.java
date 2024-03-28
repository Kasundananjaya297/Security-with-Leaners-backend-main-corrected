package com.example.SecuritywithLeaners.DTO;

import com.example.SecuritywithLeaners.Entity.PackageAndVehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgreementDTO {
    private String stdID;
    private String packageID;
    private String packageName;
    private String description;
    private double packagePrice;
    private LocalDate agreementDate;
    private Boolean isFinished;
    private List<PackageAndVehicleTypeDTO> packageAndVehicleType;
}
