package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
// "typeID": "B",
//         "typeName": "Motor Tricycle",
//         "engineCapacity": "",
//         "typeAuto": false,
//         "typeManual": true