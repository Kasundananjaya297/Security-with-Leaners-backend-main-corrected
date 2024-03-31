package com.example.SecuritywithLeaners.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDTO {
    private String stdID ;
    private String fname;
    private String lname;
    private  String email;
    private int telephone;
    private LocalDate registered;
    private String addressNo;
    private String aDL1;
    private String aDL2;
    private String city;
    private String nic;
    private int  age;
    private Boolean registrationStatus;
    private Boolean isMale;
    private String guardianName;
    private int guardianTelephone;
    private Double fullPayment;
    private Double balance;
    private String pack;
    private LocalDate dateOfBirth;
    private String profilePhotoURL;
    private double packagePrice;
}
