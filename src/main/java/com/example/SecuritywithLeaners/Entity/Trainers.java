package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Trainers {
    @Id
    private String trainerID;
    private String fname;
    private String lname;
    private String email;
    private int telephone;
    private String addressNo;
    private String adl1;
    private String adl2;
    private String city;
    private String nic;
    private LocalDate dateOfBirth;
    private String nicURL;
    private String profilePhotoURL;
    private String licenceNo;
    private LocalDate licenceIssuedOn;
    private String bloodType;
    private LocalDate trainerLicenceIssuedON;
    private int trainerLicenceID;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<TrainerDrivingLicence> trainerDrivingLicences;
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<TrainerPermit> trainerPermits;
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Scheduler> schedules;




}
