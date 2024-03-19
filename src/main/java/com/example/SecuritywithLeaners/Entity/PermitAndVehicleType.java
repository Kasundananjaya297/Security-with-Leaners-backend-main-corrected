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

    @EmbeddedId
    private PermitAndVehicleTypeId id;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("serialNo")
    private TrialPermit serialNo;
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("selectedType")
    private VehicleType selectedType;

    private String autoOrManual;
}
