package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.InsuranceDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.Entity.Insurance;
import com.example.SecuritywithLeaners.Entity.InsuranceTypes;
import com.example.SecuritywithLeaners.Entity.Vehicle;
import com.example.SecuritywithLeaners.Repo.InsuranceRepo;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class InsuranceService {
    @Autowired
    private InsuranceRepo insuranceRepo;
    public ResponseDTO saveInsurance(InsuranceDTO insuranceDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if(!insuranceRepo.existsById(insuranceDTO.getCertificateNo())){

                Insurance insurance = new Insurance();
                System.out.println(insurance);
                Vehicle vehicle = new Vehicle();
                InsuranceTypes insuranceTypes = new InsuranceTypes();

                vehicle.setRegistrationNo(insuranceDTO.getRegistrationNo());
                insuranceTypes.setType(insuranceDTO.getInsuranceType());

                insurance.setCertificateNo(insuranceDTO.getCertificateNo());
                insurance.setStartDate(insuranceDTO.getStartDate());
                insurance.setEndDate(insuranceDTO.getEndDate());
                insurance.setIssuedDate(insuranceDTO.getIssuedDate());
                insurance.setInsuranceLink(insuranceDTO.getInsuranceLink());
                insurance.setInsuranceCompany(insuranceDTO.getInsuranceCompany());
                insurance.setVehicle(vehicle);
                insurance.setInsuranceType(insuranceTypes);
                insurance.setAnnualFee(insuranceDTO.getAnnualFee());


                insuranceRepo.save(insurance);
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setMessage("Success");
                responseDTO.setStatus(HttpStatus.ACCEPTED);


            }else {
                responseDTO.setCode(varList.RSP_DUPLICATED);
                responseDTO.setMessage("Insurance Already Exists");
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
