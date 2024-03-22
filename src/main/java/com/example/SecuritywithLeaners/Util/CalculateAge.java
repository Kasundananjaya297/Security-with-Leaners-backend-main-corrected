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
        int day = Integer.parseInt(date[2]);
        int age = LocalDate.now().getYear() - year;
        if((month == LocalDate.now().getMonthValue() && day<LocalDate.now().getDayOfMonth())||(month < LocalDate.now().getMonthValue()))
        {
            //age--;
//            age++;
        }
        else{
            age--;
        }
        return age;
    }
    public int calcualteMonth(String d){
        if(d == null){
            return -1;
        }
        String [] date = d.split("-");
        int monthCount = 0;
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);
        LocalDate today = LocalDate.now();
        monthCount = monthCount + (today.getYear() -year)*12;
        monthCount = monthCount+ today.getMonthValue() - month;
        if((today.getDayOfMonth() < day)&&(today.getMonthValue() >= month)){
            monthCount--;
        }else{
            //monthCount++;
        }
        return monthCount;
    }
}
