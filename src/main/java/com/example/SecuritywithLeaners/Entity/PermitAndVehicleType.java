package com.example.SecuritywithLeaners.Entity;

import com.example.SecuritywithLeaners.DTO.TrailPermitDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
