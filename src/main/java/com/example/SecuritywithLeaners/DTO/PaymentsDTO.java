package com.example.SecuritywithLeaners.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentsDTO {
    private Long paymentID;
    private double amount;
    private LocalDate paymentDate;
    private LocalTime paymentTime;
    private String stdID;
    private String packageID;
}
