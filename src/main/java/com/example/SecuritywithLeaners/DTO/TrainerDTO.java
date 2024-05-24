package com.example.SecuritywithLeaners.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainerDTO {
    private String trainerID;
    private String fname;
    private String lname;
    private String email;
    private int telephone;
    private String addressNo;
    private String adl1;
    private String adl2;
    private String city;
    private LocalDate dateOfBirth;
    private String nic;
    private int age;
    private String nicURL;
    private String profilePhotoURL;
    private String licenceNo;
    private LocalDate licenceIssuedOn;
    private String bloodType;
    private LocalDate trainerLicenceIssuedON;
    private String trainerStatus;
    private int trainerPermitValidMonths;
    private int trainerPermitValidDays;
    private int trainerLicenceID;
    private List<TrainerDrivingLicenceDTO> trainerDrivingLicences;
    private List<TrainerPermitDTO> trainerPermits;
    private List<BookingScheduleDTO> bookingSchedules;
    private Boolean isActive;
    private String generatedPassword;
}
