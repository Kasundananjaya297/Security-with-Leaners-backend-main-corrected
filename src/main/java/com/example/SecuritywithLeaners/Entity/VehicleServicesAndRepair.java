package com.example.SecuritywithLeaners.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VehicleServicesAndRepair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String invoiceNo;
    private int milage;
    private String contactNumber;
    private String repairCenter;
    private LocalDate servicedDate;
    private LocalTime servicedTime;
    private LocalDate returnDate;
    private LocalTime returnTime;
    private String invoiceUrl;
    private double totalAmountForService;
    @ManyToOne
    @JoinColumn(name = "registrationNoFk", referencedColumnName = "registrationNo")
    private Vehicle vehicle;
    @OneToMany(mappedBy = "vehicleServicesAndRepair", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemsORDone> itemsORDones;
}
