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
public class BookingSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingID;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private Boolean isAccepted;
    private Boolean isCanceled;
    private Boolean isCompleted;
    @ManyToOne
    @JoinColumn(name = "schedulerID_Fk", referencedColumnName = "schedulerID")
    private Scheduler scheduler;
    @ManyToOne
    @JoinColumn(name = "studentID_Fk", referencedColumnName = "stdID")
    private Student student;

}
