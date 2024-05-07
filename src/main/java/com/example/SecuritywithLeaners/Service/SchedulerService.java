package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.DTO.SchedulerDTO;
import com.example.SecuritywithLeaners.Entity.Scheduler;
import com.example.SecuritywithLeaners.Entity.Trainers;
import com.example.SecuritywithLeaners.Entity.Vehicle;
import com.example.SecuritywithLeaners.Repo.SchedulerRepo;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class SchedulerService {
    @Autowired
    private SchedulerRepo schedulerRepo;
    @Autowired
    private ModelMapper modelMapper;

    public ResponseDTO saveSchedules(List<SchedulerDTO> schedulerDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            List<Scheduler> schedulerList = new ArrayList<>();
            //filter overlaping schedules based on trainerId, date and vehicleId
            List<Scheduler> schedulers = schedulerRepo.findAll();
            List<SchedulerDTO> schedulerDTOListRejected = new ArrayList<>();
            for(SchedulerDTO scheduler : schedulerDTO){
                for(Scheduler scheduler1 : schedulers){//fetched schedulers from db
                   if(scheduler1.getTrainer().getTrainerID().equals(scheduler.getTrainerID()) || scheduler1.getVehicle().getRegistrationNo().equals(scheduler.getRegistrationNo())){
                       if(scheduler1.getStart().before(scheduler.getEnd()) && scheduler1.getEnd().after(scheduler.getStart())){
                           schedulerDTOListRejected.add(scheduler);
                }
            }}
            }
            schedulerDTO.removeAll(schedulerDTOListRejected);
            for(SchedulerDTO scheduler : schedulerDTO){
                Trainers trainers = new Trainers();
                trainers.setTrainerID(scheduler.getTrainerID());
                Vehicle vehicle = new Vehicle();
                vehicle.setRegistrationNo(scheduler.getRegistrationNo());
                Scheduler scheduler1 = modelMapper.map(scheduler, Scheduler.class);
                scheduler1.setTrainer(trainers);
                scheduler1.setVehicle(vehicle);
                schedulerList.add(scheduler1);
            }
            schedulerRepo.saveAll(schedulerList);
            if(!schedulerDTOListRejected.isEmpty() && schedulerDTO.isEmpty()){
                responseDTO.setMessage("Schedules already exists");
                responseDTO.setCode(varList.RSP_FAIL);
                responseDTO.setContent(schedulerDTOListRejected);
                responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
            } else if (!schedulerDTOListRejected.isEmpty()) {
                responseDTO.setMessage("Some schedules already exists");
                responseDTO.setCode(varList.RSP_DUPLICATED);
                responseDTO.setContent(schedulerDTOListRejected);
                responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
            } else{
                responseDTO.setMessage("Schedules saved successfully");
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setStatus(HttpStatus.ACCEPTED);
            }
        }catch (Exception e){
            responseDTO.setMessage("Error in saving schedules");
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseDTO;
    }
    public ResponseDTO getAllSchedules(){
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            List<Scheduler> schedulerList = schedulerRepo.findAll();
            List<SchedulerDTO> schedulerDTOList = new ArrayList<>();
            for(Scheduler scheduler : schedulerList){
                SchedulerDTO schedulerDTO = modelMapper.map(scheduler, SchedulerDTO.class);
                schedulerDTO.setTrainerID(scheduler.getTrainer().getTrainerID());
                schedulerDTO.setRegistrationNo(scheduler.getVehicle().getRegistrationNo());
                schedulerDTOList.add(schedulerDTO);
            }
            responseDTO.setContent(schedulerDTOList);
            responseDTO.setMessage("Schedules fetched successfully");
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setStatus(HttpStatus.OK);
    }catch (Exception e){
        responseDTO.setMessage("Error in fetching schedules");
        responseDTO.setCode(varList.RSP_ERROR);
        responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseDTO;
    }


}
