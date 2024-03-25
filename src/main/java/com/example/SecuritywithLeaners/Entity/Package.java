package com.example.SecuritywithLeaners.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Package {
    @Id
    private String packageID;
    private String packageName;
    private String description;
    private Double packagePrice;
    @OneToMany(mappedBy = "packageID")
    private List<PackageAndVehicleType> packageAndVehicleType;
}
