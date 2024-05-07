package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Scheduler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schedulerID;
    private Date start;
    private Date end;
    private int studentCount;
    private String title;
    @ManyToOne
    @JoinColumn(name = "trainerID_Fk", referencedColumnName = "trainerID")
    private Trainers trainer;
    @ManyToOne
    @JoinColumn(name = "vehicleID_Fk", referencedColumnName = "registrationNo")
    private Vehicle vehicle;
}
