package com.example.SecuritywithLeaners.DTO;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleClassAndParticipation {
    private String vehicleClass;
    private int participation;
}
