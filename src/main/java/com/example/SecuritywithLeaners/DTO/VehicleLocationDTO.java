package com.example.SecuritywithLeaners.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleLocationDTO {
    private Long id;
    private Long schedulerID;
    private String registrationNo;
    private float startLatitude;
    private float startLongitude;
    private float endLatitude;
    private float endLongitude;
}
