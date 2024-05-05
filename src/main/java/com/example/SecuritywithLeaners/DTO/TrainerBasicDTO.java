package com.example.SecuritywithLeaners.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainerBasicDTO {
    private String trainerID;
    private String fname;
    private String lname;
    private String email;
    private int telephone;
    private String nic;
    private int age;
    private int trainerPermitValidMonths;
    private int trainerPermitValidDays;
    private int trainerLicenceID;
    private Boolean isActive;
}
