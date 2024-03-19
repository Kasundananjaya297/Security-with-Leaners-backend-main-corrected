package com.example.SecuritywithLeaners.Entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@Embeddable
public class PermitAndVehicleTypeId implements Serializable {
    @ManyToOne
    private TrialPermit serialNo;
    @ManyToOne
    private VehicleType selectedType;
}
