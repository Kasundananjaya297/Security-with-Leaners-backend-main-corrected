package com.example.SecuritywithLeaners.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleBasicDTO {
    private String registrationNo;
    private String make;
    private String color;
    private int passengerCapacity;
    private String typeID;
    private boolean status;
    private String autoOrManual;
    private String vehicleClass;
    private String modal;
    private String vehicleStatus;
    private int licenceValidMonths;
    private int insuraceValidMonths;
}
