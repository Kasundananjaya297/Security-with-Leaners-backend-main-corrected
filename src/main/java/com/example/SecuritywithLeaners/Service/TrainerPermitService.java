package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.DTO.TrainerPermitDTO;
import com.example.SecuritywithLeaners.Entity.TrainerPermit;
import com.example.SecuritywithLeaners.Entity.Trainers;
import com.example.SecuritywithLeaners.Repo.TrainerPermitRepo;
import com.example.SecuritywithLeaners.Util.CalculateAge;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class TrainerPermitService {
    @Autowired
    private TrainerPermitRepo trainerPermitRepo;
    @Autowired
    ModelMapper modelMapper;


    public ResponseDTO saveTrainerPermit(TrainerPermitDTO trainerPermitDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            if(trainerPermitRepo.countByTrainerIdAndExpiryDate(trainerPermitDTO.getTrainerID(),trainerPermitDTO.getExpiryDate().toString())==0) {
                TrainerPermit trainerPermit = new TrainerPermit();
                Trainers trainer = new Trainers();
                trainer.setTrainerID(trainerPermitDTO.getTrainerID());
                trainerPermit.setExpiryDate(trainerPermitDTO.getExpiryDate());
                trainerPermit.setUpdatedOrIssuedOn(trainerPermitDTO.getUpdatedOrIssuedOn());
                trainerPermit.setLicenceURL(trainerPermitDTO.getLicenceURL());
                trainerPermit.setTrainer(trainer);
                trainerPermitRepo.save(trainerPermit);
                responseDTO.setMessage("Trainer Permit saved successfully");
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setCode(varList.RSP_SUCCES);
            }else{
                responseDTO.setMessage("Trainer Permit already exists");
                responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
                responseDTO.setCode(varList.RSP_DUPLICATED);
            }
        }catch (Exception e){
            responseDTO.setMessage("Trainer Permit not saved");
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setCode(varList.RSP_ERROR);
        }





       return responseDTO;
    }
}
