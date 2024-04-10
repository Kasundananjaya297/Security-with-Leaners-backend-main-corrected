package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.ExtraSessionDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.DTO.VehicleTypeDTO;
import com.example.SecuritywithLeaners.Entity.*;
import com.example.SecuritywithLeaners.Entity.Package;
import com.example.SecuritywithLeaners.Repo.*;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ExtraSessionService {
    @Autowired
    private ExtrasNotInAgreementRepo extrasNotInAgreementRepo;
    @Autowired
    private ExtraSessionRepo extraSessionRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TrialPermitRepo trialPermitRepo;
    @Autowired
    private PermitAndVehicleTypeRepo permitAndVehicleTypeRepo;
    @Autowired
    private VehicleTypeRepo vehicleTypeRepo;
    @Autowired
    private AgreementRepo agreementRepo;
    @Autowired
    private PackageAndVehicleTypeRepo packageAndVehicleTypeRepo;
    public ResponseDTO UpdateExtraSession(List<ExtraSessionDTO> extraSessionDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        ExtraSession extraSessions = new ExtraSession();
        try{
            for (ExtraSessionDTO e : extraSessionDTO) {
                if(extraSessionRepo.checkExtraSession(e.getStdID(), e.getPackageID(), e.getTypeID()) > 0){
                    extraSessionRepo.updateExtraSession(e.getTypeID(), e.getExtraLessons(), e.getPriceForExtraLesson(), e.getTotalLessons(), e.getStdID(), e.getPackageID(), e.getPrice());
                    System.out.println("Extra Session Found");
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                    responseDTO.setCode(varList.RSP_SUCCES);
                    responseDTO.setMessage("Success");
                    responseDTO.setContent(null);
                }
                else {
                    responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
                    responseDTO.setMessage("No Data Found");
                    responseDTO.setContent(null);
                    responseDTO.setStatus(HttpStatus.NOT_FOUND);
                    break;
                }
            }
        }catch (Exception e){
            System.out.println(e);
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setMessage("Failed");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }
        return responseDTO;
    }

    public ResponseDTO getVehicleTypesForExtraSession(String stdID) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if(trialPermitRepo.existsByStdID(stdID) == 0){
                responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No Data Found");
                responseDTO.setContent(null);
                responseDTO.setStatus(HttpStatus.NOT_FOUND);

            }else {
                String serial_no = trialPermitRepo.findByStdID(stdID);
                List<String> types = permitAndVehicleTypeRepo.findSelectedTypeBySerialNo(serial_no);
                String packageID = agreementRepo.PackageID(stdID);
                List<String> typesInPackage = packageAndVehicleTypeRepo.findTypeByPackageID(packageID);
                List<String> typesNotINAgreement = extrasNotInAgreementRepo.getTypeID(stdID, packageID);
                types.removeAll(typesNotINAgreement);
                types.removeAll(typesInPackage);
                List<VehicleType> vehicleTypes = vehicleTypeRepo.findAllById(types);
                List<VehicleTypeDTO> vehicleTypeDTOList = vehicleTypes.stream().map(vehicleType -> modelMapper.map(vehicleType, VehicleTypeDTO.class)).toList();
                responseDTO.setContent(vehicleTypeDTOList);
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setMessage("Success");
            }
            responseDTO.setStatus(HttpStatus.ACCEPTED);
        }catch (Exception e) {
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setMessage("Failed");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }
        return responseDTO;
    }

}
//findVehicleTypeNotExistOnPackageExistsOnTrialPermit