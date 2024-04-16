package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.*;
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
public class EmissionTest {
    @Id
    private String serialNo;
    private LocalDate issuedDate;
    private LocalDate expiryDate;
    private String urlOfDocument;
    private double emissionTestFee;
    @ManyToOne
    @JoinColumn(name = "registrationNoFk", referencedColumnName = "registrationNo")
    private Vehicle vehicle;
}
