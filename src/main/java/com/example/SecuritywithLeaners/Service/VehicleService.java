package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.DTO.VehicleDTO;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class VehicleService {
    public ResponseDTO saveVehicle(VehicleDTO vehicleDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try{

            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setMessage("Success");
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            //System.out.println("Vehicle saved");
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setMessage("Failed");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }

        return responseDTO;
    }

}
