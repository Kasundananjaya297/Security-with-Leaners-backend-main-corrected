package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Scheduler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schedulerID;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private int studentCount;
    private String title;
    @ManyToOne
    @JoinColumn(name = "trainerID_Fk", referencedColumnName = "trainerID")
    private Trainers trainer;
    @ManyToOne
    @JoinColumn(name = "vehicleID_Fk", referencedColumnName = "registrationNo")
    private Vehicle vehicle;
}
