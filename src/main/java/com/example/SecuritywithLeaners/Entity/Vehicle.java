package com.example.SecuritywithLeaners.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vehicle {
    @Id
    private String registrationNo;
    private String make;
    private String color;
    private int passengerCapacity;
    @ManyToOne
    private FuelType fuelType;
    private int cylinderCapacity;
    private String urlOfBook;
    private String autoOrManual;
    @ManyToOne
    private VehicleType vehicleTypes;

}
