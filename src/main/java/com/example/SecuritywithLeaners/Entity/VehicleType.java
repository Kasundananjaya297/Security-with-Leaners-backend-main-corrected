package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehicleType {
    @Id
    @Column(length = 3)
    private String typeID;
    @Column(length = 100, nullable = false)
    private String typeName;
    @Column(length = 10, nullable = true)
    private String engineCapacity;
    private Boolean typeAuto;
    private Boolean typeManual;
    private Boolean isHeavy;
    @OneToMany(mappedBy = "selectedType")
    private List<PermitAndVehicleType> permitAndVehicleType;
    @OneToMany(mappedBy = "registrationNo")
    private List<Vehicle> vehicles;
    @OneToMany(mappedBy = "vehicleType")
    private List<TrainerDrivingLicenceVehicles> trainerDrivingLicenceVehicles;



}
