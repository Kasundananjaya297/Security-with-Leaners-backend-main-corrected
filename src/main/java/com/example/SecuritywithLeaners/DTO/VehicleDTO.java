package com.example.SecuritywithLeaners.DTO;

import com.example.SecuritywithLeaners.Entity.FuelType;
import com.example.SecuritywithLeaners.Entity.VehicleType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleDTO {
    private String registrationNo;
    private String make;
    private String color;
    private int passengerCapacity;
    @OneToOne
    private String fuelType;
    private int cylinderCapacity;
    private String urlOfBook;
    private String vehicleTypes;
    private String autoOrManual;
}
