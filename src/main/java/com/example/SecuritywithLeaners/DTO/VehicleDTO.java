package com.example.SecuritywithLeaners.DTO;

import com.example.SecuritywithLeaners.Entity.FuelType;
import com.example.SecuritywithLeaners.Entity.VehicleLicense;
import com.example.SecuritywithLeaners.Entity.VehicleType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleDTO {
    private String registrationNo;
    private String make;
    private String color;
    private int passengerCapacity;
    private String fuelType;
    private int cylinderCapacity;
    private String urlOfBook;
    private String typeID;
    private boolean status;
    private String autoOrManual;
    private String vehicleClass;
    private String vehiclePhoto;
    private String modal;
    private LocalDate dateOfRegistration;
    private int meterReading;
    private String invoiceUrl;
    private String vehicleStatus;
    private List<VehicleLicenceDTO> licenses;
    private List<InsuranceDTO> insurances;
    private List<EmissionTestDTO> emissionTests;
    private List<VehicleServiceORRepairDTO> vehicleServiceORRepairs;
}
