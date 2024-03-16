package com.example.SecuritywithLeaners.Entity;

import com.example.SecuritywithLeaners.DTO.TrailPermitDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PermitAndVehicleType {
    @Id
    @ManyToOne
    @JoinColumn(name = "serialNo_fk")
    private TrialPermit serialNo;
    @Id
    @ManyToOne
    @JoinColumn(name = "typeID_fk")
    private VehicleType selectedType;
    private String autoOrManual;
}
