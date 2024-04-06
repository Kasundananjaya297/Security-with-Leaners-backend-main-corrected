package com.example.SecuritywithLeaners.DTO;

import com.example.SecuritywithLeaners.Entity.Package;
import com.example.SecuritywithLeaners.Entity.Student;
import com.example.SecuritywithLeaners.Entity.VehicleType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExtraSessionDTO {
    private String stdID;
    private String packageID;
    private String typeID;
    private int extraLessons;
    private double priceForExtraLesson;
    private int totalLessons;
    private double price;
}
