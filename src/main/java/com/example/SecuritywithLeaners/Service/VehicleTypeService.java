package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.DTO.VehicleTypeDTO;
import com.example.SecuritywithLeaners.Entity.VehicleType;
import com.example.SecuritywithLeaners.Repo.VehicleTypeRepo;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class VehicleTypeService {
    @Autowired
    VehicleTypeRepo vehicleTypeRepo;
    @Autowired
    ModelMapper modelMapper;
    public ResponseDTO saveVehicleType(VehicleType vehicleType){
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            if(!vehicleTypeRepo.existsById(vehicleType.getTypeID())){
                vehicleTypeRepo.save(vehicleType);
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setMessage("Success");
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                //System.out.println("Vehicle Type saved");
            }
            else{
                responseDTO.setCode(varList.RSP_DUPLICATED);
                responseDTO.setMessage("Already registered That Vehicle Type");
                responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
                //System.out.println("Vehicle Type already registered");
            }
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setMessage("Failed");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }

        return responseDTO;
    }
    public ResponseDTO getVehicleType(){
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setMessage("Success");
            List<VehicleTypeDTO> vehicleTypes = vehicleTypeRepo.findAll().stream()
                    .map(vehicleType -> modelMapper.map(vehicleType, VehicleTypeDTO.class))
                    .collect(Collectors.toList());
            responseDTO.setContent(vehicleTypes);
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
