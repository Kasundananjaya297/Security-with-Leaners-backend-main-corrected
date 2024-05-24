package com.example.SecuritywithLeaners.DTO;

import com.example.SecuritywithLeaners.Entity.Scheduler;
import com.example.SecuritywithLeaners.Entity.Student;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class BookingScheduleDTO {
    private Long bookingID;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private Boolean isAccepted;
    private Boolean isCanceled;
    private Boolean isCompleted;
    private Long schedulerID;
    private String vehicleClass;
    private String stdID;
    private String stdFname;
    private String stdLname;
    private int telephone;
    private Date startTime;
    private Date endTime;
    private String vehicleRegistrationNo;
    private String vehicleMade;
    private String vehicleModal;
}
