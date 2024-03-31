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
public class Agreement {
    @EmbeddedId
    private AgreementID agreementID;
    @ManyToOne
    @MapsId("stdID")
    private Student stdID;
    @ManyToOne
    @MapsId("packageID")
    private Package packageID;
    private double packagePrice;
    private LocalDate agreementDate;
    private Boolean isFinished;
    private double discount;
    private double totalAmount;

}
