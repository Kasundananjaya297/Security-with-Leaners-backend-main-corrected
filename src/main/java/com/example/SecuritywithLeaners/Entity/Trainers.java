package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String aDL1;
    private String aDL2;
    private String city;
    private String nic;
    private String dateOfBirth;
    private String nicURL;
    private String profilePhotoURL;
    
}
