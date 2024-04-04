package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExtraSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int extraSessionID;
    @ManyToOne
    private Agreement agreement;
    @ManyToOne
    private PackageAndVehicleType packageAndVehicleType;
    private int extraLessons;
    private double priceForExtraLesson;
    private int totalLessons;
}
