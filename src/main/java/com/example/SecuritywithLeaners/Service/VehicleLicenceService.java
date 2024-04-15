package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.DTO.VehicleLicenceDTO;
import com.example.SecuritywithLeaners.Entity.Vehicle;
import com.example.SecuritywithLeaners.Entity.VehicleLicense;
import com.example.SecuritywithLeaners.Repo.VehicleLicenceRepo;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
public class VehicleLicenceService {
    @Autowired
    VehicleLicenceRepo vehicleLicenceRepo;
    public ResponseDTO saveVehicleLicense(VehicleLicenceDTO vehicleLicenceDTO) {
       ResponseDTO responseDTO = new ResponseDTO();
       try {
           if(!vehicleLicenceRepo.existsById(vehicleLicenceDTO.getLicenseNo())){
               VehicleLicense vehicleLicense = new VehicleLicense();
               Vehicle vehicle = new Vehicle();
               vehicleLicense.setLicenseNo(vehicleLicenceDTO.getLicenseNo());
               vehicleLicense.setAnnualFee(vehicleLicenceDTO.getAnnualFee());
               vehicleLicense.setArrearsFee(vehicleLicenceDTO.getArrearsFee());
               vehicleLicense.setFinesPaid(vehicleLicenceDTO.getFinesPaid());
               vehicleLicense.setIssuedDate(vehicleLicenceDTO.getIssuedDate());
               vehicleLicense.setExpiryDate(vehicleLicenceDTO.getExpiryDate());
               vehicleLicense.setLicenseLink(vehicleLicenceDTO.getLicenseLink());
               vehicle.setRegistrationNo(vehicleLicenceDTO.getRegistrationNo());
               vehicleLicense.setVehicle(vehicle);
                vehicleLicenceRepo.save(vehicleLicense);
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setMessage("Success");
                responseDTO.setContent(null);
           }else {
               responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
               responseDTO.setCode(varList.RSP_DUPLICATED);
               responseDTO.setMessage("Vehicle License Already Exists");
               responseDTO.setContent(null);
           }
       }catch (Exception e){
           responseDTO.setStatus(HttpStatus.BAD_REQUEST);
           responseDTO.setCode(varList.RSP_FAIL);
           responseDTO.setMessage("Failed");
           responseDTO.setContent(null);
       }
       return responseDTO;
    }
}
