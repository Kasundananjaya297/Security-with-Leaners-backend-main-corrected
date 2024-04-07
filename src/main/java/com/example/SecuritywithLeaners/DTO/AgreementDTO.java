package com.example.SecuritywithLeaners.DTO;

import com.example.SecuritywithLeaners.Entity.PackageAndVehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AgreementDTO {
    private String stdID;
    private String packageID;
    private String packageName;
    private String description;
    private double packagePrice;
    private LocalDate agreementDate;
    private Boolean isFinished;
    private double discount;
    private double totalAmount;
    private double totalAmountToPay;
    private double totalAmountForExtraSessions;
    private  double totalAmountPaid;
    private List<PackageAndVehicleTypeDTO> packageAndVehicleType;
    private List<ExtraSessionDTO> extraSession;
}
