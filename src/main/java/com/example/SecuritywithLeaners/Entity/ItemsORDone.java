package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemsORDone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private CommonItemsOrServiesOfferedByService itemID;
    private double totalAmount;
    @ManyToOne
    @JoinColumn(name = "invoiceFk", referencedColumnName = "id")
    private VehicleServicesAndRepair vehicleServicesAndRepair;
}
