package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TrainerPermit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate updatedOrIssuedOn;
    private LocalDate expiryDate;
    private String licenceURL;
    @ManyToOne
    @JoinColumn(name = "trainerID_Fk", referencedColumnName = "trainerID")
    private Trainers trainer;
}