package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class ExtraSessionID implements Serializable {
    @ManyToOne
    private Agreement agreementID;
}
