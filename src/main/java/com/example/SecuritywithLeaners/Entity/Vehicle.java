package com.example.SecuritywithLeaners.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vehicle {
    @Id
    private String registrationNo;
    private String make;
    private String color;
    private int passengerCapacity;
    @ManyToOne
    private FuelType fuelType;
    private int cylinderCapacity;
    private String urlOfBook;
    private String autoOrManual;
    private String vehiclePhoto;
    private boolean status;
    private String modal;
    private LocalDate dateOfRegistration;
    private int meterReading;
    private boolean availability;
    private String vehicleStatus;
    @ManyToOne
    private VehicleType typeID;
    @OneToMany(mappedBy = "vehicle" ,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Insurance> insurances;
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VehicleLicense> vehicleLicenses;
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmissionTest> emissionTests;
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VehicleServicesAndRepair> vehicleServicesAndRepairs;

}
