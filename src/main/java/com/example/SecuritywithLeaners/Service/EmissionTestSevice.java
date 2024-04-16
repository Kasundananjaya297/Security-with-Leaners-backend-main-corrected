package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.EmissionTestDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.Entity.EmissionTest;
import com.example.SecuritywithLeaners.Entity.Vehicle;
import com.example.SecuritywithLeaners.Repo.EmissionTestRepo;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class EmissionTestSevice {
    @Autowired
    EmissionTestRepo emissionTestRepo;
    @Autowired
    ModelMapper modelMapper;
    public ResponseDTO saveEmissionTest(EmissionTestDTO emissionTestDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        Vehicle vehicle = new Vehicle();
        try {
            if(!emissionTestRepo.existsById(emissionTestDTO.getSerialNo())){
                EmissionTest emissionTest = modelMapper.map(emissionTestDTO, EmissionTest.class);
                vehicle.setRegistrationNo(emissionTestDTO.getRegistrationNo());
                emissionTest.setVehicle(vehicle);
                emissionTestRepo.save(emissionTest);
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setMessage("Success");
                responseDTO.setContent(null);
                responseDTO.setStatus(HttpStatus.ACCEPTED);

            }else {
                responseDTO.setCode(varList.RSP_DUPLICATED);
                responseDTO.setMessage("Duplicated");
                responseDTO.setContent(null);
                responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
            }

        }catch (Exception e){
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setMessage("Failed");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);

        }
        return responseDTO;
    }
}
