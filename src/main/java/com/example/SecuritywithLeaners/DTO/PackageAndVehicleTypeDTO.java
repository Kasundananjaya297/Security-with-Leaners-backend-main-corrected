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
    private String typeName;
    private String engineCapacity;
    private int lessons;
    private String autoOrManual;
}
