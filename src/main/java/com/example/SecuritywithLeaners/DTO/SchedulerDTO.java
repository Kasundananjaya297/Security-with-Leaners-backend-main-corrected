package com.example.SecuritywithLeaners.DTO;

import com.example.SecuritywithLeaners.Entity.Trainers;
import com.example.SecuritywithLeaners.Entity.Vehicle;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SchedulerDTO {
    private Long schedulerID;
    private String title;
    private LocalDate date;
    private Date start;
    private Date end;
    private int studentCount;
    private String trainerID;
    private String registrationNo;
}






