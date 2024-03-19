package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VehicleType {
    @Id
    private String typeID;
    @Column(length = 100, nullable = false)
    private String typeName;
    @Column(length = 10, nullable = true)
    private String engineCapacity;
    private Boolean typeAuto;
    private Boolean typeManual;
    @OneToMany(mappedBy = "selectedType" , cascade = CascadeType.ALL)
    private List<PermitAndVehicleType> permitAndVehicleType;
}
// "typeID": "B",
//         "typeName": "Motor Tricycle",
//         "engineCapacity": "",
//         "typeAuto": false,
//         "typeManual": true