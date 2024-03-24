package com.example.SecuritywithLeaners.Entity;


import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class PackageAndVehicleTypeID implements Serializable {
    @ManyToOne
    private Package packageID;
    @ManyToOne
    private VehicleType typeID;
}
