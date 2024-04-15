package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.InsuranceDTO;
import com.example.SecuritywithLeaners.DTO.InsuranceTypeDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.Entity.InsuranceTypes;
import com.example.SecuritywithLeaners.Repo.InsuranceTypeRepo;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class InsuranceTypeService {
    @Autowired
    InsuranceTypeRepo insuranceTypeRepo;
    @Autowired
    ModelMapper modelMapper;
    public ResponseDTO saveInsuranceType(InsuranceTypeDTO insuranceTypeDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if (!insuranceTypeRepo.existsById(insuranceTypeDTO.getType())) {
                InsuranceTypes insuranceType = modelMapper.map(insuranceTypeDTO, InsuranceTypes.class);
                insuranceTypeRepo.save(insuranceType);
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setMessage("Success");
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setContent(null);
            } else {
                responseDTO.setCode(varList.RSP_DUPLICATED);
                responseDTO.setMessage("Insurance Type Already Exists");
                responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
                responseDTO.setContent(null);
            }
        } catch (Exception e) {
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setMessage("Failed");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }

        return responseDTO;
    }
    public ResponseDTO getInsuranceTypes(){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<InsuranceTypeDTO> insuranceTypeDTOS = insuranceTypeRepo.findAll().stream().map(insuranceType -> modelMapper.map(insuranceType, InsuranceTypeDTO.class)).collect(Collectors.toList());
            responseDTO.setContent(insuranceTypeDTOS);
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setMessage("Success");
            responseDTO.setStatus(HttpStatus.ACCEPTED);
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setMessage("Failed");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }
        return responseDTO;
    }
}
