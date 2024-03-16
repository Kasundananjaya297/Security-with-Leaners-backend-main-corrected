package com.example.SecuritywithLeaners.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data

public class ResponseDTO {
    private int recordCount;
    private HttpStatus status;
    private String code;
    private String message;
    private  Object content;
    private String token;
    private String role;
    private String username;
}
