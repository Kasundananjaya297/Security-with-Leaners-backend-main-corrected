package com.example.SecuritywithLeaners.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleTypeDTO {
    private String typeID;
    private String typeName;
    private String engineCapacity;
    private Boolean typeAuto;
    private Boolean typeManual;
}
