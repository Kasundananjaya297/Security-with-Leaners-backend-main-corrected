package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.DTO.VehicleLocationDTO;
import com.example.SecuritywithLeaners.Entity.VehicleLocations;
import com.example.SecuritywithLeaners.Repo.SchedulerRepo;
import com.example.SecuritywithLeaners.Repo.VehicleLocationRepo;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Transactional
@Service
public class VehicleLocationService {
    @Autowired
    private VehicleLocationRepo vehicleLocationRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SchedulerRepo schedulerRepo;

     public ResponseDTO updateVehicleLocation(VehicleLocationDTO vehicleLocations) {
         System.out.println("updateVehicleLocation++++++++++++++++++++++++++" + vehicleLocations);
         ResponseDTO responseDTO = new ResponseDTO();
         try {
             VehicleLocations vehicleLoc = vehicleLocationRepo.getVehicleLocationByRegNo(vehicleLocations.getRegistrationNo());
                vehicleLoc.setEndLatitude(vehicleLocations.getEndLatitude());
                vehicleLoc.setEndLongitude(vehicleLocations.getEndLongitude());
             vehicleLocationRepo.saveAndFlush(vehicleLoc);
             responseDTO.setStatus(HttpStatus.ACCEPTED);
             responseDTO.setCode(varList.RSP_SUCCES);
             responseDTO.setMessage("Vehicle Location Updated Successfully");
         } catch (Exception e) {
             responseDTO.setCode(varList.RSP_FAIL);
             responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
             responseDTO.setMessage("Vehicle Location Update Failed");
         }
         return responseDTO;
     }
        public ResponseDTO saveStartedLocation(VehicleLocationDTO vehicleLocations) {
            ResponseDTO responseDTO = new ResponseDTO();
            try {
                VehicleLocations vehicleLoc = vehicleLocationRepo.getVehicleLocationByRegNo(vehicleLocations.getRegistrationNo());
                if (vehicleLoc == null) {
                    vehicleLoc = modelMapper.map(vehicleLocations, VehicleLocations.class);
                    schedulerRepo.updateIsStarted(vehicleLocations.getSchedulerID(), LocalTime.now());
                    vehicleLocationRepo.save(vehicleLoc);
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                    responseDTO.setCode(varList.RSP_SUCCES);
                    return responseDTO;
                }else {
                    vehicleLoc.setStartLatitude(vehicleLocations.getStartLatitude());
                    vehicleLoc.setStartLongitude(vehicleLocations.getStartLongitude());
                    vehicleLoc.setEndLatitude(vehicleLocations.getStartLatitude());
                    vehicleLoc.setEndLongitude(vehicleLocations.getStartLongitude());
                    vehicleLocationRepo.saveAndFlush(vehicleLoc);
                    schedulerRepo.updateIsStarted(vehicleLocations.getSchedulerID(),LocalTime.now());
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                    responseDTO.setCode(varList.RSP_SUCCES);
                }
            } catch (Exception e) {
                responseDTO.setCode(varList.RSP_FAIL);
                responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                responseDTO.setMessage("Vehicle Location Save Failed");
            }
            return responseDTO;
        }
        public ResponseDTO getVehicleLocationByVehicleID(String vehicleID) {
            ResponseDTO responseDTO = new ResponseDTO();
            try {
                VehicleLocations vehicleLoc = vehicleLocationRepo.getVehicleLocationByRegNo(vehicleID);
                if (vehicleLoc != null) {
                    VehicleLocationDTO vehicleLocationDTO = modelMapper.map(vehicleLoc, VehicleLocationDTO.class);
                    responseDTO.setContent(vehicleLocationDTO);
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                    responseDTO.setCode(varList.RSP_SUCCES);
                    return responseDTO;
                }
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setMessage("No Vehicle Location Found");
            } catch (Exception e) {
                responseDTO.setCode(varList.RSP_FAIL);
                responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                responseDTO.setMessage("Vehicle Location Fetch Failed");
            }
            return responseDTO;
        }

}
