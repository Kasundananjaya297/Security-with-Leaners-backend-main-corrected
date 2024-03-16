package com.example.SecuritywithLeaners.Util;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Transactional

@AllArgsConstructor
@Setter
@Service
public class CalculateAge {
    public int CalculateAgeINT(String DOB){
        String [] date = DOB.split("-");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int age = LocalDate.now().getYear() - year;
        if(month > 6){age++;}
        return age;
    }
}
