package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PackageAndVehicleType {

    @EmbeddedId
    private PackageAndVehicleTypeID packageAndVehicleTypeID;
    @ManyToOne
    @MapsId("packageID")
    private Package packageID;
    @ManyToOne
    @MapsId("typeID")
    private VehicleType typeID;
    private String autoOrManual;
    private int lessons;
}
