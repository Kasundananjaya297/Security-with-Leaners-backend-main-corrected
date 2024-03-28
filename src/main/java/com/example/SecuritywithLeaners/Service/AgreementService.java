package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.AgreementDTO;
import com.example.SecuritywithLeaners.DTO.PackageAndVehicleTypeDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.Entity.*;
import com.example.SecuritywithLeaners.Entity.Package;
import com.example.SecuritywithLeaners.Repo.AgreementRepo;
import com.example.SecuritywithLeaners.Repo.PackageRepo;
import com.example.SecuritywithLeaners.Repo.StudentRepo;
import com.example.SecuritywithLeaners.Util.varList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AgreementService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AgreementRepo agreementRepo;
    @Autowired
    PackageRepo packageRepo;
    @Autowired
    StudentRepo studentRepo;
    public ResponseDTO saveAgreement(AgreementDTO agreementDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Agreement agreement = new Agreement();
            AgreementID agreementID = new AgreementID();
            Package apackage = new Package();
            Student student = new Student();

            apackage.setPackageID(agreementDTO.getPackageID());
            student.setStdID(agreementDTO.getStdID());

            agreementID.setStdID(student);
            agreementID.setPackageID(apackage);
            if(!agreementRepo.existsById(agreementID)){
                if(agreementRepo.agreementIsFinished(agreementDTO.getStdID())==null || agreementRepo.agreementIsFinished(agreementDTO.getStdID()) == true){
                    agreement.setAgreementID(agreementID);
                    agreement.setPackagePrice(agreementDTO.getPackagePrice());
                    agreement.setAgreementDate(agreementDTO.getAgreementDate());
                    agreement.setIsFinished(agreementDTO.getIsFinished());
                    agreementRepo.save(agreement);
                    responseDTO.setContent(agreement);
                    responseDTO.setCode(varList.RSP_SUCCES);
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                    responseDTO.setMessage("Agreement saved successfully");
                }else{
                    responseDTO.setCode(varList.RSP_FAIL);
                    responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
                    responseDTO.setMessage("Current Agreement Not Expired");
                    responseDTO.setContent(null);
                }
            }else{
                responseDTO.setCode(varList.RSP_DUPLICATED);
                responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
                responseDTO.setMessage("Agreement already exists");
                responseDTO.setContent(null);
            }
        } catch (Exception e) {
            // Set response details for failure
            responseDTO.setCode("01");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setMessage("An error occurred: " + e.getMessage());
        }
        return responseDTO;
    }
    public ResponseDTO checkCurrentAgreement(String stdID){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if(agreementRepo.agreementIsFinished(stdID)==null || agreementRepo.agreementIsFinished(stdID) == true){
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setMessage("Current Agreement Expired");
                responseDTO.setContent(null);
            }else{
                responseDTO.setCode(varList.RSP_FAIL);
                responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
                responseDTO.setMessage("Current Agreement Not Expired");
                responseDTO.setContent(null);
            }
        } catch (Exception e) {
            // Set response details for failure
            responseDTO.setCode("01");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setMessage("An error occurred: " + e.getMessage());
        }
        return responseDTO;
    }
    public ResponseDTO getAgreement(String stdID){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<Agreement> agreement = agreementRepo.getAgreementsByStdID(stdID).stream().toList();
            AgreementDTO agreementDTO = new AgreementDTO();
            List<AgreementDTO> agreementDTOS =new ArrayList<>();
            for (Agreement a: agreement) {
                agreementDTO.setStdID(a.getAgreementID().getStdID().getStdID());
                agreementDTO.setPackageID(a.getAgreementID().getPackageID().getPackageID());
                agreementDTO.setPackagePrice(a.getPackagePrice());
                agreementDTO.setAgreementDate(a.getAgreementDate());
                agreementDTO.setIsFinished(a.getIsFinished());
                agreementDTO.setPackageName(a.getAgreementID().getPackageID().getPackageName());
                agreementDTO.setDescription(a.getAgreementID().getPackageID().getDescription());
                List<PackageAndVehicleTypeDTO> packageAndVehicleTypeDTOS = new ArrayList<>();
                for(PackageAndVehicleType p : a.getPackageID().getPackageAndVehicleType()){
                    PackageAndVehicleTypeDTO packageAndVehicleTypeDTO = new PackageAndVehicleTypeDTO();
                    packageAndVehicleTypeDTO.setPackageID(p.getPackageID().getPackageID());
                    packageAndVehicleTypeDTO.setTypeID(p.getTypeID().getTypeID());
                    packageAndVehicleTypeDTO.setTypeName(p.getTypeID().getTypeName());
                    packageAndVehicleTypeDTO.setEngineCapacity(p.getTypeID().getEngineCapacity());
                    packageAndVehicleTypeDTO.setLessons(p.getLessons());
                    packageAndVehicleTypeDTO.setAutoOrManual(p.getAutoOrManual());
                    packageAndVehicleTypeDTOS.add(packageAndVehicleTypeDTO);
                }
                agreementDTO.setPackageAndVehicleType(packageAndVehicleTypeDTOS);
                agreementDTOS.add(agreementDTO);
            }
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            responseDTO.setMessage("Agreement found");
            responseDTO.setContent(agreementDTOS);
        }catch (Exception e){
            responseDTO.setCode("01");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setMessage("An error occurred: " + e.getMessage());
        }
        return responseDTO;
    }
}
