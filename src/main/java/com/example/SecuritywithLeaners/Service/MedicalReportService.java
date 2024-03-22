package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.MedicalDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.Entity.MedicalReport;
import com.example.SecuritywithLeaners.Repo.MedicalRepo;
import com.example.SecuritywithLeaners.Repo.StudentRepo;
import com.example.SecuritywithLeaners.Util.CalculateAge;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class MedicalReportService {
    @Autowired
    private MedicalRepo medicalReportRepo;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CalculateAge cal;
    public ResponseDTO saveMedicalReport(MedicalDTO medicalReport) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if((studentRepo.existsById(medicalReport.getStdID()))){
                if(!medicalReportRepo.existsById(medicalReport.getSerialNo())){
                    String currentPermitExaminDate=medicalReportRepo.getMaxDate(medicalReport.getStdID());
                    System.out.println(currentPermitExaminDate);
                   int mcount =  cal.calcualteMonth(currentPermitExaminDate);
                    if(mcount<24 && mcount != -1){
                        responseDTO.setCode(varList.RSP_FAIL);
                        responseDTO.setMessage("Current Medical Report is not expired");
                        responseDTO.setStatus(HttpStatus.ACCEPTED);
                        responseDTO.setContent(null);
                    }else {
                        System.out.println(medicalReport);
                        MedicalReport medicalReport1 = modelMapper.map(medicalReport, MedicalReport.class);
                        System.out.println(medicalReport1);
                        medicalReportRepo.save(medicalReport1);
                        responseDTO.setCode(varList.RSP_SUCCES);
                        responseDTO.setMessage("Success");
                        responseDTO.setStatus(HttpStatus.ACCEPTED);
                        responseDTO.setContent(medicalReport);
                    }
                } else {
                    responseDTO.setCode(varList.RSP_DUPLICATED);
                    responseDTO.setMessage("Duplicated");
                    responseDTO.setContent(null);
                    responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
                }
            }  else {
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
    public ResponseDTO checkReportExpiration(String stdID){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if(studentRepo.existsById(stdID)){
                String currentPermitExaminDate=medicalReportRepo.getMaxDate(stdID);
                int mcount =  cal.calcualteMonth(currentPermitExaminDate);
                if(mcount<24 && mcount != -1){
                    responseDTO.setCode(varList.RSP_FAIL);
                    responseDTO.setMessage("Current Medical Report is not expired");
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                    responseDTO.setContent(null);
                }else {
                    responseDTO.setCode(varList.RSP_SUCCES);
                    responseDTO.setMessage("Current Medical Report is expired");
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                    responseDTO.setContent(null);
                }
            }  else {
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
}
