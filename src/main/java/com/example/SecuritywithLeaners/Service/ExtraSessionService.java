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
import java.util.stream.Collectors;

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
    @Autowired
    private VehicleRepo vehicleRepo;

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

                List<Vehicle> vehicles = vehicleRepo.findAll();

                List<Vehicle> vehicleTypes = vehicleRepo.findAll();
                List<VehicleTypeDTO> vehicleTypesDTO = new ArrayList<>();
                for(Vehicle vehicle:vehicleTypes){
                    VehicleTypeDTO vehicleTypeDTO = new VehicleTypeDTO();
                    vehicleTypeDTO.setTypeID(vehicle.getTypeID().getTypeID());
                    vehicleTypeDTO.setTypeName(vehicle.getTypeID().getTypeName());
                    vehicleTypeDTO.setEngineCapacity(vehicle.getTypeID().getEngineCapacity());
                    vehicleTypeDTO.setTypeAuto(vehicle.getAutoOrManual().equals("Auto"));
                    vehicleTypeDTO.setTypeManual(vehicle.getAutoOrManual().equals("Manual"));
                    vehicleTypeDTO.setIsHeavy(vehicle.getTypeID().getIsHeavy());
                    vehicleTypesDTO.add(vehicleTypeDTO);
                }
                // Merge based on typeID
                List<VehicleTypeDTO> distinctVehicleTypes = new ArrayList<>(vehicleTypesDTO.stream()
                        .collect(Collectors.toMap(VehicleTypeDTO::getTypeID, // key mapper
                                vehicleTypeDTO -> vehicleTypeDTO, // value mapper
                                (existing, replacement) -> {
                                    // Merge Auto and Manual types
                                    existing.setTypeAuto(existing.getTypeAuto() || replacement.getTypeAuto());
                                    existing.setTypeManual(existing.getTypeManual() || replacement.getTypeManual());
                                    return existing;
                                })) // merge function
                        .values());


                List<VehicleTypeDTO> vehicleTypeDTO = distinctVehicleTypes.stream().filter(vehicleType -> types.contains(vehicleType.getTypeID())).collect(Collectors.toList());

                responseDTO.setContent(vehicleTypeDTO);
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