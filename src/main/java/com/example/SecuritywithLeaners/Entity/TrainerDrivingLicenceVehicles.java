package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class TrainerDrivingLicenceVehicles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicleType_Fk", referencedColumnName = "typeID")
    private VehicleType vehicleType;

    @ManyToOne
    @JoinColumn(name = "trainerDrivingLicenceID_Fk", referencedColumnName = "trainerDrivingLicenceID")
    private TrainerDrivingLicence trainerDrivingLicence;

    @ManyToOne
    @JoinColumn(name = "trainerID_Fk", referencedColumnName = "trainerID")
    private Trainers trainer;
}
