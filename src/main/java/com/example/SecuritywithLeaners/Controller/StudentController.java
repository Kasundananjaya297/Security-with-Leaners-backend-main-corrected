package com.example.SecuritywithLeaners.Controller;

import com.example.SecuritywithLeaners.DTO.BookingScheduleDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.Service.BookingService;
import com.example.SecuritywithLeaners.Service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "api/student")
@CrossOrigin
public class StudentController {
    @Autowired
    private SchedulerService schedulerService;
    @Autowired
    private BookingService bookingService;

    @GetMapping("/getScheduelsByStudentID/{stdID}")
    public ResponseEntity getStudent(@PathVariable String stdID){
        ResponseDTO responseDTO = schedulerService.getStudentSchedules(stdID);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/makeBooking")
    public ResponseEntity makeBooking(@RequestBody BookingScheduleDTO bookingScheduleDTO){
        ResponseDTO responseDTO = bookingService.makeBooking(bookingScheduleDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }

}
