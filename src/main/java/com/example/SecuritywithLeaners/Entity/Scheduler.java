package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

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
    @Column(columnDefinition = "boolean default false")
    private Boolean trainerRequestToCancel;
    @Column(columnDefinition = "boolean default false")
    private Boolean isStarted;
    @Column(columnDefinition = "boolean default false")
    private Boolean isCompleted;
    private LocalTime completeOn;
    private LocalTime startedOn;
    @ManyToOne
    @JoinColumn(name = "trainerID_Fk", referencedColumnName = "trainerID")
    private Trainers trainer;
    @ManyToOne
    @JoinColumn(name = "vehicleID_Fk", referencedColumnName = "registrationNo")
    private Vehicle vehicle;
    @OneToMany(mappedBy = "scheduler", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookingSchedule> bookingSchedule;
}
