package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.*;
import com.example.SecuritywithLeaners.Entity.TrainerDrivingLicence;
import com.example.SecuritywithLeaners.Entity.TrainerDrivingLicenceVehicles;
import com.example.SecuritywithLeaners.Entity.TrainerPermit;
import com.example.SecuritywithLeaners.Entity.Trainers;
import com.example.SecuritywithLeaners.Repo.TrainerRepo;
import com.example.SecuritywithLeaners.Util.CalculateAge;
import com.example.SecuritywithLeaners.Util.IDgenerator;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class TrainerService {
    @Autowired
    private TrainerRepo trainerRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IDgenerator idgenerator;
    @Autowired
    private CalculateAge calculateAge;

    public ResponseDTO saveTrainer(TrainerDTO trainerDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            Trainers trainer = trainerRepo.availableTrainer(trainerDTO.getEmail(), trainerDTO.getTelephone(), trainerDTO.getNic(), trainerDTO.getLicenceNo());
            if(trainer == null){
                Trainers trainers = modelMapper.map(trainerDTO, Trainers.class);
                trainers.setTrainerID(idgenerator.generateTrainerID());
                trainerRepo.save(trainers);
                responseDTO.setMessage("Trainer saved successfully");
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setContent(null);
            }else {
                Trainers trainers = modelMapper.map(trainerDTO, Trainers.class);
                trainers.setNic(trainer.getNic());
                trainers.setTelephone(trainer.getTelephone());
                trainers.setEmail(trainer.getEmail());
                responseDTO.setMessage("Trainer already exists");
                responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
                responseDTO.setCode(varList.RSP_ERROR);
                responseDTO.setContent(trainers);
            }
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setMessage("An error occurred: " + e.getMessage());
            responseDTO.setContent(null);

        }
        return responseDTO;
    }
    public ResponseDTO getAllTrainers(String field, String order,int pageSize, int offset){
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            Page<Trainers> trainers = trainerRepo.findAll(PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.fromString(order), field)));
            List<TrainerDTO> trainerDTOS = new ArrayList<>();
            for(Trainers trainer : trainers){
                TrainerDTO trainerDTO = modelMapper.map(trainer, TrainerDTO.class);
                trainerDTO.setAge(calculateAge.CalculateAgeINT(trainerDTO.getDateOfBirth().toString()));
                List<TrainerDrivingLicenceDTO> trainerDrivingLicenceDTO =new ArrayList<>();
                for(TrainerDrivingLicence trainerDrivingLicence : trainer.getTrainerDrivingLicences().stream().sorted(Comparator.comparing(TrainerDrivingLicence::getTrainerDrivingLicenceID).reversed()).toList()){
                    TrainerDrivingLicenceDTO trainerDrivingLicenceDTO1 = modelMapper.map(trainerDrivingLicence, TrainerDrivingLicenceDTO.class);
                    trainerDrivingLicenceDTO.add(trainerDrivingLicenceDTO1);
                    List<TrainerDrivingLicenceVehicleTypeDTO> trainerDrivingLicenceVehicleTypeDTO = new ArrayList<>();
                    for (TrainerDrivingLicenceVehicles trainerDrivingLicenceVehicles : trainerDrivingLicence.getTrainerDrivingLicenceVehicles()){
                        TrainerDrivingLicenceVehicleTypeDTO trainerDrivingLicenceVehicleTypeDTO1 = modelMapper.map(trainerDrivingLicenceVehicles, TrainerDrivingLicenceVehicleTypeDTO.class);
                        trainerDrivingLicenceVehicleTypeDTO.add(trainerDrivingLicenceVehicleTypeDTO1);
                        if(trainerDrivingLicenceVehicles.getVehicleType().getIsHeavy()){
                            trainerDrivingLicenceDTO1.setExpireDateForHeavy(trainerDrivingLicenceDTO1.getExpiryDate());
                            int monthsForHeavyDuty = calculateAge.CalculateMonths(trainerDrivingLicenceDTO1.getExpiryDate().toString()) -4*12;
                            int daysForHeavyDuty = calculateAge.calculateDays(trainerDrivingLicenceDTO1.getExpiryDate().toString())-4*12*365;
                            int mothsForLightWeigh = calculateAge.calcualteMonth(trainerDrivingLicenceDTO1.getExpiryDate().toString())*-1;
                            int daysForLightWeight = calculateAge.calculateDays(trainerDrivingLicenceDTO1.getExpiryDate().toString());
                            trainerDrivingLicenceDTO1.setDaysForExpireLightWeight(Math.max(daysForLightWeight, 0));
                            trainerDrivingLicenceDTO1.setDaysForExpireHeavyDuty(Math.max(daysForHeavyDuty, 0));
                            trainerDrivingLicenceDTO1.setMonthsForExpireLightWeight(Math.max(mothsForLightWeigh, 0));
                            trainerDrivingLicenceDTO1.setMonthsForExpiireHevyDuty(Math.max(monthsForHeavyDuty, 0));
                        }else{
                            trainerDrivingLicenceDTO1.setMonthsForExpiireHevyDuty(-1);
                            int months = calculateAge.calcualteMonth(trainerDrivingLicenceDTO1.getExpiryDate().toString())*-1;
                            int days = calculateAge.calculateDays(trainerDrivingLicenceDTO1.getExpiryDate().toString());
                            trainerDrivingLicenceDTO1.setDaysForExpireLightWeight(Math.max(days, 0));
                            trainerDrivingLicenceDTO1.setMonthsForExpireLightWeight(Math.max(months , 0));
                        }
                    }
                }
                List<TrainerPermitDTO> trainerPermits = new ArrayList<>();
                for(TrainerPermit trainerPermit : trainer.getTrainerPermits().stream().sorted(Comparator.comparing(TrainerPermit::getId).reversed()).toList()){
                    TrainerPermitDTO trainerPermitDTO = new TrainerPermitDTO();
                    trainerPermitDTO.setExpiryDate(trainerPermit.getExpiryDate());
                    trainerPermitDTO.setLicenceURL(trainerPermit.getLicenceURL());
                    trainerPermitDTO.setUpdatedOrIssuedOn(trainerPermit.getUpdatedOrIssuedOn());
                    int months= calculateAge.calcualteMonth(trainerPermitDTO.getExpiryDate().toString())*-1;
                    int days = calculateAge.calculateDays(trainerPermitDTO.getExpiryDate().toString())*-1;
                    trainerDTO.setTrainerPermitValidMonths(Math.max(months, 0));
                    trainerDTO.setTrainerPermitValidDays(Math.max(days, 0));
                    trainerPermits.add(trainerPermitDTO);
                }
                trainerDTO.setTrainerPermits(trainerPermits);
                trainerDTO.setTrainerDrivingLicences(trainerDrivingLicenceDTO);
                trainerDTOS.add(trainerDTO);

            }
            responseDTO.setMessage("Trainers fetched successfully");
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setContent(trainerDTOS);
        }
        catch (Exception e){
            responseDTO.setMessage("An error occurred: " + e.getMessage());
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setContent(null);
        }
        return responseDTO;
    }
}
