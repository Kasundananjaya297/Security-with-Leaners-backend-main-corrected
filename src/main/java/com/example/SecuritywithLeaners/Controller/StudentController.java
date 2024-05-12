package com.example.SecuritywithLeaners.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "api/student")
@CrossOrigin
public class StudentController {
    @GetMapping("/getStudent")
    public String getStudent(){
        
        return "Student";
    }
}
