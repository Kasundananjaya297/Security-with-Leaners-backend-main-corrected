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
public class ExtrasNotINAgreement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int extraSessionID;
    @ManyToOne
    private Agreement agreement;
    private int extraLessons;
    private double price;
    private double priceForExtraLesson;
    private String extraLessonVehicleType;
    private String typeID;
}
