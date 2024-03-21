package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MedicalReport {
    @Id
    private String serialNo;
    private LocalDate examination;
    private String bloodType;
    private Boolean visionISCorrected;
    private Boolean isSatisfactory;
    private Boolean isSquint;
    private String medicalURL;
    @ManyToOne
    @JoinColumn(name = "stdID_FK", referencedColumnName = "stdID")
    private Student stdID;

}
