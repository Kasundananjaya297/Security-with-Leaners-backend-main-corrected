package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TrainerDrivingLicence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainerDrivingLicenceID;
    @Column(unique = true)
    private LocalDate updatedOrIssuedOn;
    @ManyToOne
    @JoinColumn(name = "trainerID_Fk", referencedColumnName = "trainerID")
    private Trainers trainer;
    private LocalDate expiryDate;
    private String licenceURL;
    @OneToMany(mappedBy = "trainerDrivingLicence",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<TrainerDrivingLicenceVehicles> trainerDrivingLicenceVehicles;

}
