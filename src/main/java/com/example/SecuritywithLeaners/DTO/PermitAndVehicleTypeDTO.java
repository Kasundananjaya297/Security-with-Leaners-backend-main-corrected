package com.example.SecuritywithLeaners.DTO;

import com.example.SecuritywithLeaners.Entity.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermitAndVehicleTypeDTO {
    private String serialNo;
    private String selectedType;
    private String autoOrManual;
    private String engineCapacity;
    private String description;
    private int extraLessons;
    private int lessons;
    private double priceForExtraLesson;
    private int totalLessons;
    private int participatedLessons;
    private int remainingLessons;
}