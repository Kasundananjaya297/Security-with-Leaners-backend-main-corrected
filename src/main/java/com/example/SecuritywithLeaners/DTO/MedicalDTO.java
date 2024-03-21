package com.example.SecuritywithLeaners.DTO;

import com.example.SecuritywithLeaners.Entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicalDTO {
    private String serialNo;
    private LocalDate examination;
    private String bloodType;
    private Boolean visionISCorrected;
    private Boolean isSatisfactory;
    private Boolean isSquint;
    private String medicalURL;
    private String stdID;
}
