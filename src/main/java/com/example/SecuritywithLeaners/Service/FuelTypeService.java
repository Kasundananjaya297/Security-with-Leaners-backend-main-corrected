package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.FuelTypeDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.Entity.FuelType;
import com.example.SecuritywithLeaners.Repo.FuelTypeRepo;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FuelTypeService {
    @Autowired
    FuelTypeRepo fuelTypeRepo;
    @Autowired
    ModelMapper modelMapper;

    public ResponseDTO saveFuelType(FuelTypeDTO fuelTypeDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        FuelType fuelType = modelMapper.map(fuelTypeDTO, FuelType.class);
        try {
            if(fuelTypeRepo.existsById(fuelType.getFuelType())){
                responseDTO.setMessage("Fuel Type Already Exists");
                responseDTO.setCode(varList.RSP_DUPLICATED);
                responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
                responseDTO.setContent(null);
            }
            else{
                fuelTypeRepo.save(fuelType);
                responseDTO.setMessage("Fuel Type Saved Successfully");
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setStatus(HttpStatus.OK);
                responseDTO.setContent(fuelTypeDTO);
            }
        }catch (Exception e) {
            responseDTO.setMessage("Fuel Type Save Failed");
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setContent(null);
        }
        return responseDTO;
    }
    public ResponseDTO getFuelTypes(){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO.setMessage("Fuel Types Fetched Successfully");
            List<FuelType> fuelTypes = fuelTypeRepo.findAll();
            List <FuelTypeDTO> fuelTypeDTOS = fuelTypes.stream().map(fuelType -> modelMapper.map(fuelType, FuelTypeDTO.class)).toList();
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setStatus(HttpStatus.OK);
            responseDTO.setContent(fuelTypeDTOS);
        }catch (Exception e){
            responseDTO.setMessage("Fuel Types Fetch Failed");
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setContent(null);
        }
        return responseDTO;
    }
}
