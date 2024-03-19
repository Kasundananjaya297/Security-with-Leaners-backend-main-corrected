package com.example.SecuritywithLeaners.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrialPermit1DTO {
    private String serialNo;
    private LocalDate examDate;
    private LocalDate expDate;
    private String stdID;
    private String downURL;
    private List<PermitAndVehicleTypeDTO> permitAndVehicleType;
}
