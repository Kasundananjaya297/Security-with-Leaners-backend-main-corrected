package com.example.SecuritywithLeaners.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentBasicDTO {
    private String stdID;
    private String fname;
    private String lname;
    private String nic;
    private int telephone;

}
