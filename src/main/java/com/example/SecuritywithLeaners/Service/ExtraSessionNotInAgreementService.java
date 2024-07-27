package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.*;
import com.example.SecuritywithLeaners.Entity.*;
import com.example.SecuritywithLeaners.Entity.Package;
import com.example.SecuritywithLeaners.Repo.AgreementRepo;
import com.example.SecuritywithLeaners.Repo.ExtrasNotInAgreementRepo;
import com.example.SecuritywithLeaners.Repo.ScheduleBookingRepo;
import com.example.SecuritywithLeaners.Repo.VehicleTypeRepo;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class ExtraSessionNotInAgreementService {
    @Autowired
    private ExtrasNotInAgreementRepo extraSessionNotInAgreementRepo;
    @Autowired
    private VehicleTypeRepo vehicleTypeRepo;
    @Autowired
    private AgreementRepo agreementRepo;
    @Autowired
    private ScheduleBookingRepo scheduleBookingRepo;


    public ResponseDTO saveExtraSession(List<ExtraSessionDTO> extraSessionDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        int count =0;
        double totalPrice = 0.0;
        System.out.println("+++++++++++++++++++++++++++++++++++");
        //System.out.println(extraSessionNotInAgreementRepo.countExtraSession(extraSessionDTO.get(0).getStdID(),extraSessionDTO.get(0).getPackageID()));
        try {
            for(int i = 0; i<extraSessionDTO.size(); i++){
                if(extraSessionNotInAgreementRepo.countExtraSession(extraSessionDTO.get(0).getStdID(),extraSessionDTO.get(0).getPackageID(),extraSessionDTO.get(i).getTypeID())>0){
                    count++;
                }
                totalPrice = totalPrice + extraSessionDTO.get(i).getPriceForExtraLesson();
            }
            if(count == 0){
            List<ExtrasNotINAgreement> extrasNotINAgreementList = new ArrayList<>();
            for (ExtraSessionDTO e : extraSessionDTO) {
                ExtrasNotINAgreement extrasNotINAgreement = new ExtrasNotINAgreement();
                extrasNotINAgreement.setExtraLessons(e.getExtraLessons());
                extrasNotINAgreement.setPrice(e.getPrice());
                extrasNotINAgreement.setPriceForExtraLesson(e.getPriceForExtraLesson());
                extrasNotINAgreement.setExtraLessonVehicleType(e.getExtraLessonVehicleType());
                extrasNotINAgreement.setTypeID(e.getTypeID());
                Agreement agreement = new Agreement();
                AgreementID agreementID = new AgreementID();
                Student student = new Student();
                student.setStdID(e.getStdID());
                Package packageID = new Package();
                packageID.setPackageID(e.getPackageID());
                agreementID.setStdID(student);
                agreementID.setPackageID(packageID);
                agreement.setAgreementID(agreementID);
                extrasNotINAgreement.setAgreement(agreement);
                extrasNotINAgreementList.add(extrasNotINAgreement);
            }
                System.out.println("Total Price: "+totalPrice);
            extraSessionNotInAgreementRepo.saveAll(extrasNotINAgreementList);
            double totalPriceForExtrasNotINAgeement = extraSessionNotInAgreementRepo.getTotalPrice(extraSessionDTO.get(0).getStdID(),extraSessionDTO.get(0).getPackageID());
            double totalAmountForExtraSesssion = agreementRepo.getTotalAmountForExtraSessions(extraSessionDTO.get(0).getStdID());
            double totalAmount = agreementRepo.getTotalAmount(extraSessionDTO.get(0).getStdID());
            agreementRepo.updateTotalAmountToPay(extraSessionDTO.get(0).getStdID(),totalPriceForExtrasNotINAgeement+totalAmountForExtraSesssion+totalAmount,extraSessionDTO.get(0).getPackageID());
            agreementRepo.updateTotalAmountForExtrasNotInAgreement(extraSessionDTO.get(0).getStdID(),totalPriceForExtrasNotINAgeement,extraSessionDTO.get(0).getPackageID());
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            responseDTO.setMessage("Success");
            responseDTO.setContent(null);
            responseDTO.setCode(varList.RSP_SUCCES);
        }else{
            responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
            responseDTO.setMessage("Extra Session Already Exists");
            responseDTO.setContent(null);
            responseDTO.setCode(varList.RSP_DUPLICATED);
            }
        }catch (Exception e) {
            log.error(e.getMessage());
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }
        return responseDTO;
    }
    public ResponseDTO updateExtraSession(List<ExtraSessionDTO> extraSessionDTOS){
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            for (ExtraSessionDTO e : extraSessionDTOS) {
                if(extraSessionNotInAgreementRepo.countExtraSession(e.getStdID(),e.getPackageID(),e.getTypeID())>0){
                    extraSessionNotInAgreementRepo.updateExtraSession(e.getStdID(),e.getPackageID(),e.getTypeID(),e.getExtraLessons(),e.getPrice(),e.getPriceForExtraLesson());
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                    responseDTO.setMessage("Success");
                    responseDTO.setContent(null);
                    responseDTO.setCode(varList.RSP_SUCCES);
                }else{
                    responseDTO.setStatus(HttpStatus.NOT_FOUND);
                    responseDTO.setMessage("No Data Found");
                    responseDTO.setContent(null);
                    responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
                    break;
                }
            }
            double totalPriceForExtrasNotINAgeement = extraSessionNotInAgreementRepo.getTotalPrice(extraSessionDTOS.get(0).getStdID(),extraSessionDTOS.get(0).getPackageID());
            agreementRepo.updateTotalAmountForExtrasNotInAgreement(extraSessionDTOS.get(0).getStdID(),totalPriceForExtrasNotINAgeement,extraSessionDTOS.get(0).getPackageID());
            double totalAmountForExtraSesssion = agreementRepo.getTotalAmountForExtraSessions(extraSessionDTOS.get(0).getStdID());
            double totalAmount = agreementRepo.getTotalAmount(extraSessionDTOS.get(0).getStdID());
            agreementRepo.updateTotalAmountToPay(extraSessionDTOS.get(0).getStdID(),totalPriceForExtrasNotINAgeement+totalAmountForExtraSesssion+totalAmount,extraSessionDTOS.get(0).getPackageID());
                }
        catch (Exception e){
            log.error(e.getMessage());
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setMessage("Failed");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }




        return responseDTO;
    }
    public ResponseDTO getExtraSessionNotInAgreement(String stdID,String packageID){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<ExtrasNotINAgreement> extrasNotINAgreementList = extraSessionNotInAgreementRepo.getExtraSession(stdID,packageID);
            if(extrasNotINAgreementList.size()>0){
                List<ExtraSessionDTO> extraSessionDTOList = new ArrayList<>();
                for (ExtrasNotINAgreement e : extrasNotINAgreementList) {
                    ExtraSessionDTO extraSessionDTO = new ExtraSessionDTO();
                    extraSessionDTO.setStdID(e.getAgreement().getAgreementID().getStdID().getStdID());
                    extraSessionDTO.setPackageID(e.getAgreement().getAgreementID().getPackageID().getPackageID());
                    extraSessionDTO.setTypeID(e.getTypeID());
                    extraSessionDTO.setExtraLessons(e.getExtraLessons());
                    extraSessionDTO.setPriceForExtraLesson(e.getPriceForExtraLesson());
                    extraSessionDTO.setPrice(e.getPrice());
                    extraSessionDTO.setTypeName(vehicleTypeRepo.getOne(e.getTypeID()).getTypeName());
                    extraSessionDTO.setExtraLessonVehicleType(e.getExtraLessonVehicleType());
                    extraSessionDTOList.add(extraSessionDTO);
                }
                List<BookingSchedule> bookingSchedules = scheduleBookingRepo.getParticipatedLessons(stdID);
                List<BookingScheduleDTO> bookingScheduleDTOS = new ArrayList<>();
                for(BookingSchedule b : bookingSchedules){
                    BookingScheduleDTO bookingScheduleDTO = new BookingScheduleDTO();
                    bookingScheduleDTO.setBookingID(b.getBookingID());
                    bookingScheduleDTO.setBookingDate(b.getBookingDate());
                    bookingScheduleDTO.setBookingTime(b.getBookingTime());
                    bookingScheduleDTO.setIsAccepted(b.getIsAccepted());
                    bookingScheduleDTO.setIsCanceled(b.getIsCanceled());
                    bookingScheduleDTO.setIsCompleted(b.getIsCompleted());
                    bookingScheduleDTO.setSchedulerID(b.getScheduler().getSchedulerID());
                    bookingScheduleDTO.setVehicleClass(b.getScheduler().getVehicle().getTypeID().getTypeID());
                    bookingScheduleDTOS.add(bookingScheduleDTO);
                }
                //filter vehicle classes and set participated lessons count and mereged list
                List<VehicleClassAndParticipation> vehicleClassAndParticipations = new ArrayList<>();
                for(ExtrasNotINAgreement p : extrasNotINAgreementList){
                    VehicleClassAndParticipation vehicleClassAndParticipation = new VehicleClassAndParticipation();
                    vehicleClassAndParticipation.setVehicleClass(p.getTypeID());
                    vehicleClassAndParticipations.add(vehicleClassAndParticipation);
                }
                //merge vehicle classes and participated lessons
                for(VehicleClassAndParticipation v : vehicleClassAndParticipations){
                    for(BookingScheduleDTO b : bookingScheduleDTOS){
                        if(v.getVehicleClass().equals(b.getVehicleClass())){
                            v.setParticipation(v.getParticipation()+1);
                        }
                    }
                }
                //set Lessons count for package and vehicle type
                for(ExtraSessionDTO p : extraSessionDTOList){
                    for(VehicleClassAndParticipation v : vehicleClassAndParticipations){
                        if(p.getTypeID().equals(v.getVehicleClass())){
                            p.setParticipatedLessons(v.getParticipation());
                        }
                    }
                }

                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setMessage("Success");
                responseDTO.setContent(extraSessionDTOList);
                responseDTO.setCode(varList.RSP_SUCCES);
            }else{
                responseDTO.setStatus(HttpStatus.NOT_FOUND);
                responseDTO.setMessage("No Data Found");
                responseDTO.setContent(null);
                responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
            }
        }catch (Exception e) {
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }
        return responseDTO;
    }


}
//private int extraLessons;
//private double price;
//private double priceForExtraLesson;
//private int totalLessons;
//private String extraLessonVehicleType;