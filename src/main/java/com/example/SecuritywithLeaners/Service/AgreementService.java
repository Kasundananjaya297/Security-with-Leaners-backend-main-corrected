package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.*;
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
import java.util.Optional;

@Service
@Transactional
public class AgreementService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AgreementRepo agreementRepo;
    @Autowired
    PackageRepo packageRepo;
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    UsersRepo usersRepo;
    @Autowired
    PackageAndVehicleTypeRepo packageAndVehicleTypeRepo;
    @Autowired
    private ScheduleBookingRepo scheduleBookingRepo;
    // Save Agreement
    public ResponseDTO saveAgreement(AgreementDTO agreementDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Agreement agreement = new Agreement();
            AgreementID agreementID = new AgreementID();
            Package apackage = new Package();
            Student student = new Student();
            apackage.setPackageID(agreementDTO.getPackageID());
            student.setStdID(agreementDTO.getStdID());
            PackageAndVehicleTypeID packageAndVehicleTypeID = new PackageAndVehicleTypeID();
            packageAndVehicleTypeID.setPackageID(apackage);
            agreementID.setStdID(student);
            agreementID.setPackageID(apackage);
            // Check if agreement exists
            if(!agreementRepo.existsById(agreementID)){
                // Check if current agreement is finished
                if(agreementRepo.agreementIsFinished(agreementDTO.getStdID())==null || agreementRepo.agreementIsFinished(agreementDTO.getStdID()) == true){
                    agreement.setAgreementID(agreementID);
                    agreement.setPackagePrice(agreementDTO.getPackagePrice());
                    agreement.setAgreementDate(agreementDTO.getAgreementDate());
                    agreement.setIsFinished(agreementDTO.getIsFinished());
                    agreement.setTotalAmount(agreementDTO.getPackagePrice());
                    agreement.setTotalAmountToPay(agreementDTO.getPackagePrice());
                    List<ExtraSession> extraSessionArrayList = new ArrayList<>();
                    List<PackageAndVehicleType> packageAndVehicleType = packageAndVehicleTypeRepo.findByPackageID(agreementDTO.getPackageID());

            for(PackageAndVehicleType p : packageAndVehicleType.stream().toList()){
                ExtraSession extraSession = new ExtraSession();
                extraSession.setAgreement(agreement);
                extraSession.setPackageAndVehicleType(p);
                extraSession.setTotalLessons(p.getLessons());
                System.out.println(p);
                extraSessionArrayList.add(extraSession);
            }
                    agreement.setExtraSessions(extraSessionArrayList);
                    agreementRepo.save(agreement);
                    //PackageAndVehicleType packageAndVehicleType = packageAndVehicleTypeRepo.findById(agreementDTO.getPackageID()).get();

                    responseDTO.setContent("Agreement saved successfully");
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
    // Check if current agreement is finished
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
    // Get Agreement
    public ResponseDTO getAgreement(String stdID){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<Agreement> agreement = agreementRepo.getAgreementsByStdID(stdID).stream().toList();
            AgreementDTO agreementDTO = new AgreementDTO();
            List<AgreementDTO> agreementDTOS =new ArrayList<>();
            System.out.println("get Agreement Triggered");
            for (Agreement a: agreement) {
                agreementDTO.setStdID(a.getAgreementID().getStdID().getStdID());
                Optional<Users> users = usersRepo.findById(a.getAgreementID().getStdID().getStdID());
                if(users.isPresent()){
                    agreementDTO.setGeneratedPassword(users.get().getGeneratedPassword());
                }
                agreementDTO.setPackageID(a.getAgreementID().getPackageID().getPackageID());
                agreementDTO.setPackagePrice(a.getPackagePrice());
                agreementDTO.setAgreementDate(a.getAgreementDate());
                agreementDTO.setIsFinished(a.getIsFinished());
                agreementDTO.setPackageName(a.getAgreementID().getPackageID().getPackageName());
                agreementDTO.setDescription(a.getAgreementID().getPackageID().getDescription());
                agreementDTO.setDiscount(a.getDiscount());
                agreementDTO.setTotalAmount(a.getTotalAmount());
                agreementDTO.setTotalAmountToPay(a.getTotalAmountToPay());
                agreementDTO.setTotalAmountForExtraSessions(a.getTotalAmountForExtraSessions());
                agreementDTO.setTotalAmountPaid(a.getTotalAmountPaid());
                agreementDTO.setTotalAmountForExtrasNotInAgreement(a.getTotalAmountForExtrasNotInAgreement());
                List<PackageAndVehicleTypeDTO> packageAndVehicleTypeDTOS = new ArrayList<>();
                int i =0;
                //get extra sessions
                for(PackageAndVehicleType p : a.getPackageID().getPackageAndVehicleType()){
                    PackageAndVehicleTypeDTO packageAndVehicleTypeDTO = new PackageAndVehicleTypeDTO();
                    packageAndVehicleTypeDTO.setPackageID(p.getPackageID().getPackageID());
                    packageAndVehicleTypeDTO.setTypeID(p.getTypeID().getTypeID());
                    packageAndVehicleTypeDTO.setTypeName(p.getTypeID().getTypeName());
                    packageAndVehicleTypeDTO.setEngineCapacity(p.getTypeID().getEngineCapacity());
                    packageAndVehicleTypeDTO.setLessons(p.getLessons());
                    packageAndVehicleTypeDTO.setAutoOrManual(p.getAutoOrManual());
                    packageAndVehicleTypeDTO.setExtraLessons(agreement.get(0).getExtraSessions().stream().filter(e -> e.getPackageAndVehicleType().getPackageID().getPackageID().equals(p.getPackageID().getPackageID())).toList().get(i).getExtraLessons());
                    packageAndVehicleTypeDTO.setPriceForExtraLesson(agreement.get(0).getExtraSessions().stream().filter(e -> e.getPackageAndVehicleType().getPackageID().getPackageID().equals(p.getPackageID().getPackageID())).toList().get(i).getPriceForExtraLesson());
                    packageAndVehicleTypeDTO.setTotalLessons(agreement.get(0).getExtraSessions().stream().filter(e -> e.getPackageAndVehicleType().getPackageID().getPackageID().equals(p.getPackageID().getPackageID())).toList().get(i).getTotalLessons());
                    packageAndVehicleTypeDTO.setPrice(agreement.get(0).getExtraSessions().stream().filter(e -> e.getPackageAndVehicleType().getPackageID().getPackageID().equals(p.getPackageID().getPackageID())).toList().get(i).getPrice());
                    packageAndVehicleTypeDTOS.add(packageAndVehicleTypeDTO);
                    i++;
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
                for(PackageAndVehicleTypeDTO p : packageAndVehicleTypeDTOS){
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
                for(PackageAndVehicleTypeDTO p : packageAndVehicleTypeDTOS){
                    for(VehicleClassAndParticipation v : vehicleClassAndParticipations){
                        if(p.getTypeID().equals(v.getVehicleClass())){
                            p.setParticipatedLessons(v.getParticipation());
                        }
                    }
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
    // Update Agreement
    public ResponseDTO updateAgreementDiscount(AgreementDTO agreementDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            AgreementID agreementID = new AgreementID();
            Student student = new Student();
            Package apackage = new Package();
            student.setStdID(agreementDTO.getStdID());
            apackage.setPackageID(agreementDTO.getPackageID());
            agreementID.setStdID(student);
            agreementID.setPackageID(apackage);
            System.out.println(agreementID);
            if(agreementRepo.existsById(agreementID)){
                agreementRepo.updateDiscount(agreementDTO.getStdID(),agreementDTO.getDiscount(),(agreementDTO.getPackagePrice()-agreementDTO.getDiscount()),agreementDTO.getPackageID());
                double totalAmount = agreementRepo.getTotalAmount(agreementDTO.getStdID());
                double totalForExtraLession = agreementRepo.getTotalAmountForExtraSessions(agreementDTO.getStdID());
                double totalAmountForExtrasNotINAgreement = agreementRepo.getTotalAmountForExtrasNotInAgreement(agreementDTO.getStdID());
                agreementRepo.updateTotalAmountToPay(agreementDTO.getStdID(),(totalAmount+totalForExtraLession+totalAmountForExtrasNotINAgreement),agreementDTO.getPackageID());//check
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setMessage("Discount updated successfully");
                responseDTO.setContent(null);
            }else{
                responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
                responseDTO.setStatus(HttpStatus.NOT_FOUND);
                responseDTO.setMessage("Agreement not found");
                responseDTO.setContent(null);
            }
        }catch (Exception e){
            responseDTO.setCode("01");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setMessage("An error occurred: " + e.getMessage());
        }
        responseDTO.setStatus(HttpStatus.ACCEPTED);
        return responseDTO;
    }
    // Update Agreement and Vehicle
    public ResponseDTO updateAgreementAndVehicle(PermitAndVehicleTypeDTO permitAndVehicleTypeDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            responseDTO.setMessage("Agreement and Vehicle updated successfully");
            responseDTO.setContent(null);
        }catch (Exception e){
            responseDTO.setCode("01");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setMessage("An error occurred: " + e.getMessage());
        }
        return responseDTO;
    }
    // Delete Agreement
    public ResponseDTO deleteAgreement(String stdID,String packageID){
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            AgreementID agreementID = new AgreementID();
            Student student = new Student();
            Package apackage = new Package();
            student.setStdID(stdID);
            apackage.setPackageID(packageID);
            agreementID.setStdID(student);
            agreementID.setPackageID(apackage);
            if(agreementRepo.existsById(agreementID)){
                if(agreementRepo.getTotalAmountToPaid(stdID)>0){
                    responseDTO.setCode(varList.RSP_FAIL);
                    responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
                    responseDTO.setMessage("Can't Delete");
                }else {
                    agreementRepo.deleteById(agreementID);
                    studentRepo.updateRegistrationStatus(false,stdID);
                    responseDTO.setCode(varList.RSP_SUCCES);
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                    responseDTO.setMessage("Agreement deleted successfully");
                    responseDTO.setContent(null);
                }
            }else{
                responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
                responseDTO.setStatus(HttpStatus.NOT_FOUND);
                responseDTO.setMessage("Agreement not found");
                responseDTO.setContent(null);
            }
        }catch (Exception e){
            responseDTO.setCode("01");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setMessage("An error occurred: " + e.getMessage());
        }
        return responseDTO;
    }
    // Update Total Amount to Pay
    public ResponseDTO upDateTotalAmountToPay(AgreementDTO agreementDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            AgreementID agreementID = new AgreementID();
            Student student = new Student();
            Package apackage = new Package();
            student.setStdID(agreementDTO.getStdID());
            apackage.setPackageID(agreementDTO.getPackageID());
            agreementID.setStdID(student);
            agreementID.setPackageID(apackage);
            if(agreementRepo.existsById(agreementID)){
                double totalAmount = agreementRepo.getTotalAmount(agreementDTO.getStdID());
                double totalAmountForExtrasNotINAgreement = agreementRepo.getTotalAmountForExtrasNotInAgreement(agreementDTO.getStdID());
                agreementRepo.updateTotalAmountForExtraSessions(agreementDTO.getStdID(),agreementDTO.getTotalAmountToPay(),agreementDTO.getPackageID());
                agreementRepo.updateTotalAmountToPay(agreementDTO.getStdID(),(totalAmount+agreementDTO.getTotalAmountToPay()+totalAmountForExtrasNotINAgreement),agreementDTO.getPackageID());
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setMessage("Total Amount to pay updated successfully");
                responseDTO.setContent(null);
            }else{
                responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
                responseDTO.setStatus(HttpStatus.NOT_FOUND);
                responseDTO.setMessage("Agreement not found");
                responseDTO.setContent(null);
            }
        }catch (Exception e){
            responseDTO.setCode("01");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setMessage("An error occurred: " + e.getMessage());
        }
        return responseDTO;
    }

}
