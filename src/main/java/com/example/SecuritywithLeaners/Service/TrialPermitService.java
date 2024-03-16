package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.DTO.TrailPermitDTO;
import com.example.SecuritywithLeaners.Entity.Student;
import com.example.SecuritywithLeaners.Entity.TrialPermit;
import com.example.SecuritywithLeaners.Repo.StudentRepo;
import com.example.SecuritywithLeaners.Repo.TrialPermitRepo;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class TrialPermitService {

    @Autowired
    TrialPermitRepo trialPermitRepo;
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    private ModelMapper modelMapper;
    public ResponseDTO SaveTrailPermit(TrailPermitDTO trialPermitDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        LocalDate currentDate = LocalDate.now();
        try{if (studentRepo.existsById(trialPermitDTO.getStdID())) {
            if(!(trialPermitRepo.existsById(trialPermitDTO.getSerialNo()))){
                LocalDate maxDate= trialPermitRepo.getMaximumExpDateByStdID(trialPermitDTO.getStdID());
                //System.out.println("max date is " + maxDate);
                if(!(maxDate !=null && maxDate.isAfter(currentDate))){
                    try {
                        TrialPermit data = modelMapper.map(trialPermitDTO, TrialPermit.class);
                        trialPermitRepo.save(data);
                        responseDTO.setMessage("Success");
                        responseDTO.setContent("Saved");
                        responseDTO.setStatus(HttpStatus.ACCEPTED);
                        responseDTO.setCode(varList.RSP_SUCCES);
                    } catch (Exception e) {
                        log.error("Error while saving trial permit", e);
                        responseDTO.setMessage("Failed");
                        responseDTO.setContent("Failed");
                        responseDTO.setStatus(HttpStatus.BAD_REQUEST);
                        responseDTO.setCode(varList.RSP_FAIL);
                    }}
                else {responseDTO.setCode(varList.RSP_FAIL);
                    responseDTO.setMessage("Can't Enter Current Trial Permit Not expired");
                    responseDTO.setContent(null);
                    responseDTO.setStatus(HttpStatus.MULTI_STATUS);}
            }

            else {responseDTO.setCode(varList.RSP_DUPLICATED);
                responseDTO.setMessage("Duplicated");
                responseDTO.setContent("Duplicated");
                responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);}

        }else{responseDTO.setMessage("Not no Exists");
            responseDTO.setContent("Not no Exists");
            responseDTO.setStatus(HttpStatus.NO_CONTENT);
            responseDTO.setCode(varList.RSP_FAIL);
        }

        }catch (Exception e){
            log.error("Error while saving trial permit", e);
            responseDTO.setMessage("Failed");
            responseDTO.setContent("Failed");
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setCode(varList.RSP_FAIL);
        }
        System.out.println(responseDTO);
        return responseDTO;
    }
    public ResponseDTO getTrialPermit(String stdID) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if (studentRepo.existsById(stdID) && (trialPermitRepo.existsByStdID(stdID) > 0)) {
                System.out.println("Exists");
                responseDTO.setMessage("Success");
                List<TrialPermit> trialPermitLit = trialPermitRepo.findAllByStdID(stdID);
                System.out.println(trialPermitLit);
                List<TrailPermitDTO> trailPermitDTOList = trialPermitLit.stream().map(tp -> modelMapper.map(tp, TrailPermitDTO.class)).toList();
                responseDTO.setContent(trailPermitDTOList);
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setCode(varList.RSP_SUCCES);
            } else {
                responseDTO.setMessage("No trial permits found for student ID: " + stdID);
                responseDTO.setContent("No trial permits found");
                responseDTO.setStatus(HttpStatus.NOT_FOUND);
                responseDTO.setCode(varList.RSP_FAIL);
            }
        } catch (Exception e) {
            System.err.println("An error occurred while fetching trial permits: " + e.getMessage());
            responseDTO.setMessage("An error occurred while fetching trial permits");
            responseDTO.setContent("Error");
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setCode(varList.RSP_ERROR);
        }
        return responseDTO;
    }

}
