package com.example.SecuritywithLeaners.DTO;

import com.example.SecuritywithLeaners.Entity.PackageAndVehicleType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PackageDTO {
    private String packageID;
    private String packageName;
    private String description;
    private Double packagePrice;
    private List<PackageAndVehicleTypeDTO> packageAndVehicleType;
}
