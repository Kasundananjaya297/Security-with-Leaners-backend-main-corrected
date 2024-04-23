package com.example.SecuritywithLeaners.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleServiceORRepairDTO {
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
    private String registrationNo;
    private double totalAmountForService;
    private List<ItemORDoneDTO> itemsORDones;

}
