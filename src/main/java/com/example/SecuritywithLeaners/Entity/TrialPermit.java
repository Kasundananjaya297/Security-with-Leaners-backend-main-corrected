package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "trialPermit")
public class TrialPermit {
    @Id
    private String serialNo;
    private LocalDate examDate;
    private LocalDate expDate;
    @ManyToOne
    @JoinColumn(name = "stdID_fk")
    private Student stdID;
}
