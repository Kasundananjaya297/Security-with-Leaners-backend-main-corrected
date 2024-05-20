package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class VehicleLocations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "vehicleID_Fk", referencedColumnName = "registrationNo")
    private Vehicle registrationNo;
    private float latitude;
    private float longitude;
}
