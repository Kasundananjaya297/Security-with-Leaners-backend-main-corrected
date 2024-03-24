package com.example.SecuritywithLeaners.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PackageAndVehicleTypeDTO {
    private String packageID;
    private String typeID;
    private int lessons;
}
