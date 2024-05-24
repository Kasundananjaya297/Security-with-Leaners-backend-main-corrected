package com.example.SecuritywithLeaners.Controller;

import com.example.SecuritywithLeaners.DTO.BookingScheduleDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.DTO.VehicleLocationDTO;
import com.example.SecuritywithLeaners.Entity.VehicleLocations;
import com.example.SecuritywithLeaners.Service.BookingService;
import com.example.SecuritywithLeaners.Service.SchedulerService;
import com.example.SecuritywithLeaners.Service.VehicleLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "api/trainer")
@CrossOrigin
public class TrainerController {
    @Autowired
    private SchedulerService schedulerService;
    @Autowired
    private VehicleLocationService vehicleLocationService;
    @Autowired
    private BookingService bookingService;

    @GetMapping("/getScheduelsByTrainerID/{TrainerID}")
    public ResponseEntity getStudent(@PathVariable String TrainerID){
        System.out.println("getScheduelsByTrainerID+++++");
        ResponseDTO responseDTO = schedulerService.getTrainerSchedules(TrainerID);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PutMapping("/trainerCancleSchedule/{scheduleID}")
    public ResponseEntity cancelSchedule(@PathVariable Long scheduleID){
        ResponseDTO responseDTO = schedulerService.cancelSchedule(scheduleID);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/saveStartedLocation")
    public ResponseEntity saveStartedLocation(@RequestBody VehicleLocationDTO vehicleLocations){
        ResponseDTO responseDTO = vehicleLocationService.saveStartedLocation(vehicleLocations);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PutMapping("/updateVehicleLocation")
    public ResponseEntity updateVehicleLocation(@RequestBody VehicleLocationDTO vehicleLocations){
        ResponseDTO responseDTO = vehicleLocationService.updateVehicleLocation(vehicleLocations);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PutMapping("/updateStudentAttendance")
    public ResponseEntity updateStudentAttendance(@RequestBody BookingScheduleDTO bookingScheduleDTO){
        ResponseDTO responseDTO = bookingService.updateStudentAttendance(bookingScheduleDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PutMapping("/completeSchedule/{scheduleID}")
    public ResponseEntity completeSchedule(@PathVariable Long scheduleID){
        ResponseDTO responseDTO = schedulerService.completeSchedule(scheduleID);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }



}
