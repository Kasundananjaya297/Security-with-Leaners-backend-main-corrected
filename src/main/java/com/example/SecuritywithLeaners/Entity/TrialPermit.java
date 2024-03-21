package com.example.SecuritywithLeaners.Entity;

import com.example.SecuritywithLeaners.Entity.Views.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "trialPermit")
public class TrialPermit {
    @Id
    @Column(length = 15)
    private String serialNo;
    private LocalDate examDate;
    private LocalDate expDate;
    @ManyToOne
    @JoinColumn(name = "stdID_fk", referencedColumnName = "stdID")
    private Student stdID;
    private String downURL;
    @OneToMany(mappedBy = "serialNo")
    private List<PermitAndVehicleType> permitAndVehicleType;
}
