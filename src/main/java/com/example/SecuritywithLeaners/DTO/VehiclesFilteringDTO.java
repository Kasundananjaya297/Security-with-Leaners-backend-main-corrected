package com.example.SecuritywithLeaners.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class VehiclesFilteringDTO {
    private String vehicleClass;
    private String vehicleControl;
    private int lessonCount;
    private int lessonsCompleted;
}
