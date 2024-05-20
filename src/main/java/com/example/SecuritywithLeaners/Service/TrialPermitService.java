package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.*;
import com.example.SecuritywithLeaners.Entity.*;
import com.example.SecuritywithLeaners.Repo.PermitAndVehicleTypeRepo;
import com.example.SecuritywithLeaners.Repo.StudentRepo;
import com.example.SecuritywithLeaners.Repo.TrialPermitRepo;
import com.example.SecuritywithLeaners.Repo.VehicleTypeRepo;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
            if (studentRepo.existsById(stdID) && trialPermitRepo.existsByStdID(stdID) > 0) {
                System.out.println("Exists");
                responseDTO.setMessage("Success");
                List<TrialPermit> trialPermitList = trialPermitRepo.findAllByStdID(stdID);
                List<TrialPermit1DTO> trailPermitDTOList = new ArrayList<>();
                for (TrialPermit tp : trialPermitList) {
                    TrialPermit1DTO trialPermit1DTO = new TrialPermit1DTO();
                    trialPermit1DTO.setSerialNo(tp.getSerialNo());
                    trialPermit1DTO.setExamDate(tp.getExamDate());
                    trialPermit1DTO.setExpDate(tp.getExpDate());
                    trialPermit1DTO.setDownURL(tp.getDownURL());
                    trialPermit1DTO.setStdID(tp.getStdID().getStdID());
                    List<PermitAndVehicleTypeDTO> permitAndVehicleTypeDTOList = new ArrayList<>();
                    for (PermitAndVehicleType permitAndVehicleType : tp.getPermitAndVehicleType()) {
                        PermitAndVehicleTypeDTO permitAndVehicleTypeDTO = new PermitAndVehicleTypeDTO();
                        permitAndVehicleTypeDTO.setSerialNo(permitAndVehicleType.getSerialNo().getSerialNo());
                        permitAndVehicleTypeDTO.setSelectedType(permitAndVehicleType.getId().getSelectedType().getTypeID());
                        permitAndVehicleTypeDTO.setDescription(permitAndVehicleType.getId().getSelectedType().getTypeName());
                        permitAndVehicleTypeDTO.setEngineCapacity(permitAndVehicleType.getId().getSelectedType().getEngineCapacity());
                        //permitAndVehicleTypeDTO.setAutoOrManual(permitAndVehicleType.getAutoOrManual());
                        permitAndVehicleTypeDTOList.add(permitAndVehicleTypeDTO);
                    }
                    trialPermit1DTO.setPermitAndVehicleType(permitAndVehicleTypeDTOList);

                    trailPermitDTOList.add(trialPermit1DTO);
                }
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
                        //permitAndVehicleType.setAutoOrManual(permitDTO.getAutoOrManual());
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
                    if (maxDate != null && maxDate.isAfter(currentDate)) {
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
                       // permitAndVehicleType.setAutoOrManual(permitAndVehicleTypeDTO.getAutoOrManual());
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
    public ResponseDTO updateTrialPermit(TrialPermit1DTO trialPermitDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if (studentRepo.existsById(trialPermitDTO.getStdID())) {
                if (trialPermitRepo.existsById(trialPermitDTO.getSerialNo())) {
                    TrialPermit trialPermit = trialPermitRepo.findById(trialPermitDTO.getSerialNo()).get();
                    trialPermit.setExamDate(trialPermitDTO.getExamDate());
                    trialPermit.setExpDate(trialPermitDTO.getExpDate());
                    trialPermit.setDownURL(trialPermitDTO.getDownURL());
                    trialPermit.setStdID(studentRepo.findById(trialPermitDTO.getStdID()).get());
                    permitAndVehicleTypeRepo.deleteAllBySerialNo(trialPermit.getSerialNo());
                    for (PermitAndVehicleTypeDTO permitAndVehicleTypeDTO : trialPermitDTO.getPermitAndVehicleType()) {
                        PermitAndVehicleTypeId permitAndVehicleTypeId = new PermitAndVehicleTypeId();
                        permitAndVehicleTypeId.setSerialNo(trialPermit);
                        permitAndVehicleTypeId.setSelectedType(vehicleTypeRepo.findById(permitAndVehicleTypeDTO.getSelectedType()).get());
                        PermitAndVehicleType permitAndVehicleType = new PermitAndVehicleType();
                        permitAndVehicleType.setId(permitAndVehicleTypeId);
                       //permitAndVehicleType.setAutoOrManual(permitAndVehicleTypeDTO.getAutoOrManual());
                        permitAndVehicleTypeRepo.saveAndFlush(permitAndVehicleType);
                    }
                    //trialPermit.setPermitAndVehicleType(modelMapper.map(trialPermitDTO.getPermitAndVehicleType(), PermitAndVehicleType.class));
                    trialPermitRepo.saveAndFlush(trialPermit);
                    responseDTO.setCode(varList.RSP_SUCCES);
                    responseDTO.setMessage("Success");
                    responseDTO.setContent("Updated");
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                } else {
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                    responseDTO.setMessage("Trial Permit not Exists");
                    responseDTO.setContent(null);
                    responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
                }
            }
        }catch (Exception e){
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setMessage("Error");
            responseDTO.setContent("Error");
            responseDTO.setCode(varList.RSP_FAIL);
        }
        return responseDTO;
    }
    public ResponseDTO checkTrialPermitExpired(String stdID) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if (studentRepo.existsById(stdID)) {
                LocalDate maxDate = trialPermitRepo.getMaximumExpDateByStdID(stdID);
                if (maxDate != null && maxDate.isAfter(LocalDate.now())) {// another way to check validity
                    responseDTO.setCode(varList.RSP_FAIL);
                    responseDTO.setMessage("Current Trial Permit is not expired");
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                    responseDTO.setContent(null);
                } else {
                    responseDTO.setCode(varList.RSP_SUCCES);
                    responseDTO.setMessage("Current Trial Permit is expired or not found");
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                    responseDTO.setContent(null);
                }
            } else {
                responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Student not found");
                responseDTO.setContent(null);
                responseDTO.setStatus(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setMessage("An error occurred: " + e.getMessage());
            responseDTO.setContent(null);
        }
        return responseDTO;
    }
//
//    public ResponseDTO getTrialPermitByStdID(String stdID) {
//        ResponseDTO responseDTO = new ResponseDTO();
//        PermitAndVehicleTypeId permitAndVehicleTypeId = new PermitAndVehicleTypeId();
//        try {
//            if (trialPermitRepo.existsByStdID(stdID)>0) {
//                List<TrialPermit> trialPermit = trialPermitRepo.findAllByStdID(stdID);
//                responseDTO.setCode(varList.RSP_SUCCES);
//                responseDTO.setMessage("Success");
//                responseDTO.setContent(trialPermit);
//                responseDTO.setStatus(HttpStatus.ACCEPTED);
//            } else {
//                responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
//                responseDTO.setMessage("No Trial Permit found for the given student ID");
//                responseDTO.setContent(null);
//                responseDTO.setStatus(HttpStatus.NO_CONTENT);
//            }
//        } catch (Exception e) {
//            responseDTO.setCode(varList.RSP_FAIL);
//            responseDTO.setMessage("Failed");
//            responseDTO.setContent(null);
//            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
//        }
//        return responseDTO;
//    }
    public ResponseDTO updatePassOrFailStudentInTrialPermit(PermitAndVehicleTypeDTO permitAndVehicleTypeDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if (trialPermitRepo.existsById(permitAndVehicleTypeDTO.getSerialNo())) {
                PermitAndVehicleTypeId permitAndVehicleTypeId = new PermitAndVehicleTypeId();
                permitAndVehicleTypeId.setSerialNo(trialPermitRepo.findById(permitAndVehicleTypeDTO.getSerialNo()).get());
                permitAndVehicleTypeId.setSelectedType(vehicleTypeRepo.findById(permitAndVehicleTypeDTO.getSelectedType()).get());
                if (permitAndVehicleTypeRepo.existsById(permitAndVehicleTypeId)) {
                    permitAndVehicleTypeRepo.updateIsPassed(permitAndVehicleTypeDTO.getIsPassed(), permitAndVehicleTypeDTO.getSerialNo(), permitAndVehicleTypeDTO.getSelectedType());
                    responseDTO.setCode(varList.RSP_SUCCES);
                    responseDTO.setMessage("Success");
                    responseDTO.setContent("Updated");
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                } else {
                    responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
                    responseDTO.setMessage("No data found");
                    responseDTO.setContent(null);
                    responseDTO.setStatus(HttpStatus.NO_CONTENT);
                }
            } else {
                responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No data found");
                responseDTO.setContent(null);
                responseDTO.setStatus(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setMessage("Failed");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }
        return responseDTO;
    }
}





