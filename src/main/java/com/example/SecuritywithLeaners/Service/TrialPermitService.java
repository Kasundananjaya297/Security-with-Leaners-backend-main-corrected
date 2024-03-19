package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.PermitAndVehicleTypeDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.DTO.TrailPermitDTO;
import com.example.SecuritywithLeaners.DTO.TrialPermit1DTO;
import com.example.SecuritywithLeaners.Entity.*;
import com.example.SecuritywithLeaners.Repo.PermitAndVehicleTypeRepo;
import com.example.SecuritywithLeaners.Repo.StudentRepo;
import com.example.SecuritywithLeaners.Repo.TrialPermitRepo;
import com.example.SecuritywithLeaners.Repo.VehicleTypeRepo;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.stream.Stream;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class TrialPermitService {

    @Autowired
    TrialPermitRepo trialPermitRepo;
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private VehicleTypeRepo vehicleTypeRepo;
    @Autowired
    private PermitAndVehicleTypeRepo permitAndVehicleTypeRepo;

    public ResponseDTO SaveTrailPermit(TrailPermitDTO trialPermitDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        LocalDate currentDate = LocalDate.now();
        try {
            if (studentRepo.existsById(trialPermitDTO.getStdID())) {
                if (!(trialPermitRepo.existsById(trialPermitDTO.getSerialNo()))) {
                    LocalDate maxDate = trialPermitRepo.getMaximumExpDateByStdID(trialPermitDTO.getStdID());
                    //System.out.println("max date is " + maxDate);
                    if (!(maxDate != null && maxDate.isAfter(currentDate))) {
                        try {
                            TrialPermit data = modelMapper.map(trialPermitDTO, TrialPermit.class);
                            trialPermitRepo.save(data);
                            System.out.println(data);
                            responseDTO.setMessage("Success");
                            responseDTO.setContent("Saved");
                            responseDTO.setStatus(HttpStatus.ACCEPTED);
                            responseDTO.setCode(varList.RSP_SUCCES);
                        } catch (Exception e) {
                            log.error("Error while saving trial permit", e);
                            responseDTO.setMessage("Failed");
                            responseDTO.setContent("Failed");
                            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
                            responseDTO.setCode(varList.RSP_FAIL);
                        }
                    } else {
                        responseDTO.setCode(varList.RSP_FAIL);
                        responseDTO.setMessage("Can't Enter Current Trial Permit Not expired");
                        responseDTO.setContent(null);
                        responseDTO.setStatus(HttpStatus.MULTI_STATUS);
                    }
                } else {
                    responseDTO.setCode(varList.RSP_DUPLICATED);
                    responseDTO.setMessage("Duplicated");
                    responseDTO.setContent("Duplicated");
                    responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
                }

            } else {
                responseDTO.setMessage("Not no Exists");
                responseDTO.setContent("Not no Exists");
                responseDTO.setStatus(HttpStatus.NO_CONTENT);
                responseDTO.setCode(varList.RSP_FAIL);
            }

        } catch (Exception e) {
            log.error("Error while saving trial permit", e);
            responseDTO.setMessage("Failed");
            responseDTO.setContent("Failed");
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setCode(varList.RSP_FAIL);
        }
        System.out.println(responseDTO);
        return responseDTO;
    }

    public ResponseDTO getTrialPermit(String stdID) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if (studentRepo.existsById(stdID) && (trialPermitRepo.existsByStdID(stdID) > 0)) {
                System.out.println("Exists");
                responseDTO.setMessage("Success");
                List<TrialPermit> trialPermitLit = trialPermitRepo.findAllByStdID(stdID);
                System.out.println(trialPermitLit);
                List<TrailPermitDTO> trailPermitDTOList = trialPermitLit.stream().map(tp -> modelMapper.map(tp, TrailPermitDTO.class)).toList();
                responseDTO.setContent(trailPermitDTOList);
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setCode(varList.RSP_SUCCES);
            } else {
                responseDTO.setMessage("No trial permits found for student ID: " + stdID);
                responseDTO.setContent("No trial permits found");
                responseDTO.setStatus(HttpStatus.NOT_FOUND);
                responseDTO.setCode(varList.RSP_FAIL);
            }
        } catch (Exception e) {
            System.err.println("An error occurred while fetching trial permits: " + e.getMessage());
            responseDTO.setMessage("An error occurred while fetching trial permits");
            responseDTO.setContent("Error");
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setCode(varList.RSP_ERROR);
        }
        return responseDTO;
    }

    public ResponseDTO saveTrailPermit(List<PermitAndVehicleTypeDTO> permitAndVehicleTypeDTOS) {//not a best practice but tried manual way future suggest to use JPA
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if (trialPermitRepo.existsById(permitAndVehicleTypeDTOS.get(0).getSerialNo())) {
                for (PermitAndVehicleTypeDTO permitDTO : permitAndVehicleTypeDTOS) {
                    PermitAndVehicleTypeId permitAndVehicleTypeId = new PermitAndVehicleTypeId();
                    TrialPermit trialPermit = trialPermitRepo.findById(permitDTO.getSerialNo()).get();
                    trialPermit.setSerialNo(permitDTO.getSerialNo());
                    VehicleType vehicleType = new VehicleType();
                    vehicleType.setTypeID(permitDTO.getSelectedType());
                    permitAndVehicleTypeId.setSerialNo(trialPermit);
                    permitAndVehicleTypeId.setSelectedType(vehicleType);
                    if (vehicleTypeRepo.existsById(permitDTO.getSelectedType()) && (!permitAndVehicleTypeRepo.existsById(permitAndVehicleTypeId))) {
                        System.out.println("ready to save");
                        System.out.println(permitDTO);
                        PermitAndVehicleType permitAndVehicleType = new PermitAndVehicleType();
                        //permitAndVehicleType.setId(permitAndVehicleTypeId);
                        permitAndVehicleType.setSelectedType(vehicleType);
                        permitAndVehicleType.setSerialNo(trialPermit);
                        permitAndVehicleType.setAutoOrManual(permitDTO.getAutoOrManual());
                        //System.out.println(permitAndVehicleType);
                        permitAndVehicleTypeRepo.save(permitAndVehicleType);
                        responseDTO.setStatus(HttpStatus.ACCEPTED);
                        responseDTO.setMessage("Success");
                        responseDTO.setContent("Saved");
                        responseDTO.setCode(varList.RSP_SUCCES);

                    } else {
                        System.out.println(permitAndVehicleTypeId);
                        permitAndVehicleTypeRepo.deleteAllInBatch(List.of(permitAndVehicleTypeRepo.findById(permitAndVehicleTypeId).get()));
                        System.out.println(permitAndVehicleTypeRepo.countByAllBySerialNo(permitDTO.getSerialNo()));
                        if (permitAndVehicleTypeRepo.countByAllBySerialNo(permitDTO.getSerialNo()) == 0) {
                            trialPermitRepo.deleteAllInBatch(List.of(trialPermitRepo.findById(permitDTO.getSerialNo()).get()));
                            System.out.println("Deleted++++++++++++++++++" + permitDTO.getSerialNo());
                        }
                        //trialPermitRepo.deleteById(permitDTO.getSerialNo());
                        responseDTO.setStatus(HttpStatus.ACCEPTED);
                        responseDTO.setMessage("Duplicated");
                        responseDTO.setContent("Deleted");
                        responseDTO.setCode(varList.RSP_DUPLICATED);
                    }
                }

            } else {
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setMessage("Trial Permit not Exists");
                responseDTO.setContent(null);
                responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
            }
        } catch (Exception e) {
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setMessage("Error");
            responseDTO.setContent("Error");
            responseDTO.setCode(varList.RSP_FAIL);
        }
        return responseDTO;
    }

    public ResponseDTO saveTrailPermitEfficinetWay(TrialPermit1DTO trialPermit1DTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        LocalDate currentDate = LocalDate.now();
        try {
            if (studentRepo.existsById(trialPermit1DTO.getStdID())) {
                if (!trialPermitRepo.existsById(trialPermit1DTO.getSerialNo())) {
                    TrialPermit trialPermit = new TrialPermit();
                    PermitAndVehicleType permitAndVehicleType = new PermitAndVehicleType();
                    PermitAndVehicleTypeId permitAndVehicleTypeId = new PermitAndVehicleTypeId();
                    LocalDate maxDate = trialPermitRepo.getMaximumExpDateByStdID(trialPermit1DTO.getStdID());
                    if(maxDate != null && maxDate.isAfter(currentDate)){
                        responseDTO.setCode(varList.RSP_FAIL);
                        responseDTO.setMessage("Can't Enter Current Trial Permit Not expired");
                        responseDTO.setContent(null);
                        responseDTO.setStatus(HttpStatus.MULTI_STATUS);
                        return responseDTO;
                    }
                    trialPermit.setSerialNo(trialPermit1DTO.getSerialNo());
                    trialPermit.setExamDate(trialPermit1DTO.getExamDate());
                    trialPermit.setExpDate(trialPermit1DTO.getExpDate());
                    trialPermit.setDownURL(trialPermit1DTO.getDownURL());
                    trialPermit.setStdID(studentRepo.findById(trialPermit1DTO.getStdID()).get());
                    trialPermitRepo.save(trialPermit);
                    for (PermitAndVehicleTypeDTO permitAndVehicleTypeDTO : trialPermit1DTO.getPermitAndVehicleType()) {
                        permitAndVehicleTypeId.setSerialNo(trialPermit);
                        permitAndVehicleTypeId.setSelectedType(vehicleTypeRepo.findById(permitAndVehicleTypeDTO.getSelectedType()).get());
                        permitAndVehicleType.setId(permitAndVehicleTypeId);
                        permitAndVehicleType.setAutoOrManual(permitAndVehicleTypeDTO.getAutoOrManual());
                        permitAndVehicleTypeRepo.save(permitAndVehicleType);
                    }
                    responseDTO.setCode(varList.RSP_SUCCES);
                    responseDTO.setMessage("Success");
                    responseDTO.setContent("Saved");
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                } else {
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                    responseDTO.setMessage("Trial Permit Exists");
                    responseDTO.setContent(null);
                    responseDTO.setCode(varList.RSP_DUPLICATED);
                }
            } else {
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setMessage("Student not Exists");
                responseDTO.setContent(null);
                responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
            }
        } catch (Exception e) {
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setMessage("Error");
            responseDTO.setContent("Error");
            responseDTO.setCode(varList.RSP_FAIL);
        }
        return responseDTO;
    }
}




