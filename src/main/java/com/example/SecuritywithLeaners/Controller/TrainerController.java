package com.example.SecuritywithLeaners.Controller;

import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.Service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "api/trainer")
@CrossOrigin
public class TrainerController {
    @Autowired
    private SchedulerService schedulerService;

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


}
