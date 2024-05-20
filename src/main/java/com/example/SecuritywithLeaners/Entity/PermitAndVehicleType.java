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
    @ManyToOne()
    @MapsId("serialNo")
    private TrialPermit serialNo;
    @ManyToOne()
    @MapsId("selectedType")
    private VehicleType selectedType;
    private Boolean isPassed;
}
