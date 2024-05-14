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
    private String schedulerID;
    private String stdID;
}
