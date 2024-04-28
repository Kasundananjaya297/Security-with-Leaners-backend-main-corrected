package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.DTO.TrainerDrivingLicenceDTO;
import com.example.SecuritywithLeaners.DTO.TrainerDrivingLicenceVehicleTypeDTO;
import com.example.SecuritywithLeaners.Entity.TrainerDrivingLicence;
import com.example.SecuritywithLeaners.Entity.TrainerDrivingLicenceVehicles;
import com.example.SecuritywithLeaners.Entity.Trainers;
import com.example.SecuritywithLeaners.Entity.VehicleType;
import com.example.SecuritywithLeaners.Repo.TrainerDrivingLicenceRepo;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class TrainerDrivingLicenceService {
    @Autowired
    private TrainerDrivingLicenceRepo trainerDrivingLicenceRepo;
    @Autowired
    ModelMapper modelMapper;

    public ResponseDTO saveTrainerDrivingLicence(TrainerDrivingLicenceDTO trainerDrivingLicenceDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if(trainerDrivingLicenceRepo.countByTrainerIdAndUpdatedOrIssuedOn(trainerDrivingLicenceDTO.getTrainerID(),trainerDrivingLicenceDTO.getUpdatedOrIssuedOn().toString())==0){
                TrainerDrivingLicence trainerDrivingLicence = new TrainerDrivingLicence();
                Trainers trainers = new Trainers();
                trainers.setTrainerID(trainerDrivingLicenceDTO.getTrainerID());

                trainerDrivingLicence.setExpiryDate(trainerDrivingLicenceDTO.getExpiryDate());
                trainerDrivingLicence.setLicenceURL(trainerDrivingLicenceDTO.getLicenceURL());
                trainerDrivingLicence.setUpdatedOrIssuedOn(trainerDrivingLicenceDTO.getUpdatedOrIssuedOn());
                trainerDrivingLicence.setTrainer(trainers);

                List<TrainerDrivingLicenceVehicles> trainerDrivingLicenceVehicles = new ArrayList<>();
                for(TrainerDrivingLicenceVehicleTypeDTO trainerDrivingLicenceVehicleTypeDTO : trainerDrivingLicenceDTO.getTrainerDrivingLicenceVehicles()){
                    TrainerDrivingLicenceVehicles trainerDrivingLicenceVehicle = new TrainerDrivingLicenceVehicles();


                    VehicleType vehicleType = new VehicleType();
                    vehicleType.setTypeID(trainerDrivingLicenceVehicleTypeDTO.getTypeID());
                    trainerDrivingLicenceVehicle.setVehicleType(vehicleType);

                    trainerDrivingLicenceVehicle.setTrainer(trainers);
                    trainerDrivingLicenceVehicle.setTrainerDrivingLicence(trainerDrivingLicence);

                    trainerDrivingLicenceVehicle.setTrainerDrivingLicence(trainerDrivingLicence);
                    trainerDrivingLicenceVehicles.add(trainerDrivingLicenceVehicle);
                }
                trainerDrivingLicence.setTrainerDrivingLicenceVehicles(trainerDrivingLicenceVehicles);
                System.out.println(trainerDrivingLicence);
                trainerDrivingLicenceRepo.save(trainerDrivingLicence);
                responseDTO.setMessage("Trainer driving licence saved successfully");
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setCode(varList.RSP_SUCCES);
            } else {
                responseDTO.setMessage("Trainer driving licence already exists");
                responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
                responseDTO.setCode(varList.RSP_DUPLICATED);
                return responseDTO;
            }

        } catch (Exception e) {
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setMessage("An error occurred: " + e.getMessage());
            responseDTO.setContent(null);
        }
        return responseDTO;
    }
}
