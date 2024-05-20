package com.example.SecuritywithLeaners.Entity;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import jakarta.persistence.*;
import lombok.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@Table(name = "StudentDetails")
public class Student {
    @Id
    @Column(length = 10, nullable = false, unique = true)
    private String stdID;
    @Column(length = 30)
    private String fname;
    @Column(length = 30)
    private String lname;
    @Column(length = 100, nullable = true , unique = true)
    private String email;
    @Column(length = 10, nullable = false , unique = true)
    private int telephone;
    @Column(length = 15, nullable = false , unique = false)
    private LocalDate registered;
    @Column(length = 10, nullable = false , unique = false)
    private String addressNo;
    @Column(length = 40, nullable = true , unique = false)
    private String aDL1;
    @Column(length = 40, nullable = true , unique = false)
    private String aDL2;
    @Column(name="city", length = 40, nullable = false , unique = false)
    private String city;
    @Column(name ="nic",length = 15,nullable = false, unique = true)
    private String nic;
    @Column(name = "dateOfBirth", length = 15,nullable = false, unique = false)
    private LocalDate dateOfBirth;
    @Column(name="registrationStatus")
    private Boolean registrationStatus;
    @Column(name="gender",nullable = false)
    private Boolean isMale;
    @Column(name="guardianName",length = 30,nullable = false)
    private String guardianName;
    @Column(name="guardianTelephone",length = 10,nullable = false)
    private int guardianTelephone;
    private String profilePhotoURL;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<BookingSchedule> bookingSchedules;
}
