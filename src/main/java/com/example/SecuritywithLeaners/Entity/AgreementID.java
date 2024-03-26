package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class AgreementID implements Serializable {
    @ManyToOne
    private Student stdID;
    @ManyToOne
    private Package packageID;
}
