package com.example.SecuritywithLeaners.DTO;

import com.example.SecuritywithLeaners.Entity.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermitAndVehicleTypeDTO {
    private String serialNo;
    private String  selectedType;
    private String autoOrManual;
    private String engineCapacity;
    private String description;
}