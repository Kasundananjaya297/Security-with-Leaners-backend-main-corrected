package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.BookingScheduleDTO;
import com.example.SecuritywithLeaners.DTO.NotificationDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.DTO.VehiclesFilteringDTO;
import com.example.SecuritywithLeaners.Entity.*;
import com.example.SecuritywithLeaners.Repo.AgreementRepo;
import com.example.SecuritywithLeaners.Repo.ScheduleBookingRepo;
import com.example.SecuritywithLeaners.Repo.SchedulerRepo;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class BookingService {
    @Autowired
    private ScheduleBookingRepo scheduleBookingRepo;
    @Autowired
    private SchedulerRepo schedulerRepo;
    @Autowired
    private AgreementRepo agreementRepo;
    @Autowired
    private NotificationService notificationService;



    public ResponseDTO makeBooking(BookingScheduleDTO bookingScheduleDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            int count = scheduleBookingRepo.findBySchedulerID(bookingScheduleDTO.getSchedulerID()).size();
            int maxstdCount = schedulerRepo.getStudentCountBySchedulerID(bookingScheduleDTO.getSchedulerID());
            if(count >= maxstdCount){
                responseDTO.setCode(varList.RSP_DUPLICATED);
                responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
                responseDTO.setMessage("Booking Full");
                responseDTO.setContent(null);
                return responseDTO;
            }else {
                BookingSchedule bookingSchedule = new BookingSchedule();
                Student student = new Student();
                student.setStdID(bookingScheduleDTO.getStdID());
                Scheduler scheduler = new Scheduler();
                scheduler.setSchedulerID(bookingScheduleDTO.getSchedulerID());
                //check duplication of student Schedule start and edn time
                List<BookingSchedule> bookingSchedules = scheduleBookingRepo.findByStdID(bookingScheduleDTO.getStdID());
                Scheduler scheduler1 = schedulerRepo.findById(bookingScheduleDTO.getSchedulerID()).get();
                for(BookingSchedule bookingSchedule1 : bookingSchedules) {
                    if(bookingSchedule1.getScheduler().getStart().before(scheduler1.getEnd()) && bookingSchedule1.getScheduler().getEnd().after(scheduler1.getStart())){
                        responseDTO.setCode(varList.RSP_FAIL);
                        responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
                        responseDTO.setMessage("Booking Time Overlap for this student");
                        responseDTO.setContent(null);
                        return responseDTO;
                    }
                }
                //check students' maximum lession count per day
                Agreement agreementList = agreementRepo.getLatestAgreement(bookingScheduleDTO.getStdID());
                List<VehiclesFilteringDTO> vehicleTypes = new ArrayList<>();
                for(PackageAndVehicleType packageAndVehicleType : agreementList.getPackageID().getPackageAndVehicleType()){
                    VehiclesFilteringDTO vehiclesFilteringDTO = new VehiclesFilteringDTO();
                    vehiclesFilteringDTO.setVehicleClass(packageAndVehicleType.getTypeID().getTypeID());
                    vehiclesFilteringDTO.setVehicleControl(packageAndVehicleType.getAutoOrManual());
                    vehiclesFilteringDTO.setLessonCount(packageAndVehicleType.getLessons());
                    vehicleTypes.add(vehiclesFilteringDTO);
                }
                for(ExtrasNotINAgreement extrasNotINAgreement : agreementList.getExtrasNotINAgreement()){
                    VehiclesFilteringDTO vehiclesFilteringDTO = new VehiclesFilteringDTO();
                    vehiclesFilteringDTO.setVehicleClass(extrasNotINAgreement.getTypeID());
                    vehiclesFilteringDTO.setVehicleControl(extrasNotINAgreement.getExtraLessonVehicleType());
                    vehiclesFilteringDTO.setLessonCount(extrasNotINAgreement.getExtraLessons());
                    vehicleTypes.add(vehiclesFilteringDTO);
                }
                for(ExtraSession extraSession : agreementList.getExtraSessions()){
                    VehiclesFilteringDTO vehiclesFilteringDTO = new VehiclesFilteringDTO();
                    vehiclesFilteringDTO.setVehicleClass(extraSession.getPackageAndVehicleType().getTypeID().getTypeID());
                    vehiclesFilteringDTO.setVehicleControl(extraSession.getPackageAndVehicleType().getAutoOrManual());
                    vehiclesFilteringDTO.setLessonCount(extraSession.getExtraLessons());
                    vehicleTypes.add(vehiclesFilteringDTO);
                }
                //merge all vehicle types based on vehicle class and get total lesson count per vehicle type
                List<VehiclesFilteringDTO> vehicleTypesMerged = new ArrayList<>();
                for(VehiclesFilteringDTO vehiclesFilteringDTO : vehicleTypes){
                    boolean isExist = false;
                    for(VehiclesFilteringDTO vehiclesFilteringDTO1 : vehicleTypesMerged){
                        if(vehiclesFilteringDTO.getVehicleClass().equals(vehiclesFilteringDTO1.getVehicleClass()) && vehiclesFilteringDTO.getVehicleControl().equals(vehiclesFilteringDTO1.getVehicleControl())){
                            vehiclesFilteringDTO1.setLessonCount(vehiclesFilteringDTO1.getLessonCount() + vehiclesFilteringDTO.getLessonCount());
                            isExist = true;
                        }
                    }
                    if(!isExist){
                        vehicleTypesMerged.add(vehiclesFilteringDTO);
                    }
                }

                //get count student booked
                List<VehiclesFilteringDTO> vehicleTypesBookedByStudent = new ArrayList<>();
                List<BookingSchedule> bookingScheduleListByStdID = scheduleBookingRepo.findByStdID(bookingScheduleDTO.getStdID());
                for(BookingSchedule bookingSchedule1 : bookingScheduleListByStdID){
                    VehiclesFilteringDTO vehiclesFilteringDTO = new VehiclesFilteringDTO();
                    vehiclesFilteringDTO.setVehicleClass(bookingSchedule1.getScheduler().getVehicle().getTypeID().getTypeID());
                    vehiclesFilteringDTO.setVehicleControl(bookingSchedule1.getScheduler().getVehicle().getAutoOrManual());
                    vehicleTypesBookedByStudent.add(vehiclesFilteringDTO);
                }
                //set booking count per vehicle type
                List<VehiclesFilteringDTO> vehicleTypesBookedByStudentMerged = new ArrayList<>();
                for(VehiclesFilteringDTO vehiclesFilteringDTO : vehicleTypesBookedByStudent){
                    boolean isExist = false;
                    for(VehiclesFilteringDTO vehiclesFilteringDTO1 : vehicleTypesBookedByStudentMerged){
                        if(vehiclesFilteringDTO.getVehicleClass().equals(vehiclesFilteringDTO1.getVehicleClass()) && vehiclesFilteringDTO.getVehicleControl().equals(vehiclesFilteringDTO1.getVehicleControl())){
                            vehiclesFilteringDTO1.setLessonCount(vehiclesFilteringDTO1.getLessonCount() + 1);
                            isExist = true;
                        }
                    }
                    if(!isExist){
                        vehiclesFilteringDTO.setLessonCount(1);
                        vehicleTypesBookedByStudentMerged.add(vehiclesFilteringDTO);
                    }
                }
                System.out.println(vehicleTypesMerged);
                //check student booking count per vehicle Type and based on booking
                for(VehiclesFilteringDTO vehiclesFilteringDTO : vehicleTypesMerged){
                    for(VehiclesFilteringDTO vehiclesFilteringDTO1 : vehicleTypesBookedByStudentMerged){
                        if(vehiclesFilteringDTO.getVehicleClass().equals(vehiclesFilteringDTO1.getVehicleClass()) && vehiclesFilteringDTO.getVehicleControl().equals(vehiclesFilteringDTO1.getVehicleControl())){
                            if(vehiclesFilteringDTO.getLessonCount() <= vehiclesFilteringDTO1.getLessonCount()){
                                responseDTO.setCode(varList.RSP_DUPLICATED);
                                responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
                                responseDTO.setMessage("Student already booked maximum lesson count for this vehicle type");
                                responseDTO.setContent(null);
                                return responseDTO;
                            }
                        }
                    }
                }
                bookingSchedule.setBookingDate(bookingScheduleDTO.getBookingDate());
                bookingSchedule.setBookingTime(bookingScheduleDTO.getBookingTime());
                bookingSchedule.setIsAccepted(bookingScheduleDTO.getIsAccepted());
                bookingSchedule.setIsCanceled(bookingScheduleDTO.getIsCanceled());
                bookingSchedule.setIsCompleted(bookingScheduleDTO.getIsCompleted());
                bookingSchedule.setScheduler(scheduler);
                bookingSchedule.setStudent(student);
                scheduleBookingRepo.save(bookingSchedule);
                //get max booking id
                BookingSchedule bookingSchedule1 = scheduleBookingRepo.findByStdID(bookingScheduleDTO.getStdID()).get(0);
                NotificationDTO notificationDTO = new NotificationDTO();
                notificationDTO.setItemID(bookingSchedule1.getStudent().getStdID());
                notificationDTO.setItemFname(bookingSchedule1.getStudent().getFname());
                notificationDTO.setItemLname(bookingSchedule1.getStudent().getLname());
                notificationDTO.setItemOrEventDate(bookingSchedule1.getScheduler().getStart());
                notificationDTO.setMessage("Booking Requested");
                notificationDTO.setStatus("Success");
                notificationService.saveNotification(notificationDTO);
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setMessage("Success");
            }
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setMessage("Failed");
            responseDTO.setContent(null);
        }
        return responseDTO;
    }
    public ResponseDTO acceptOrRegectBookingRequest(BookingScheduleDTO bookingScheduleDTO ){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            scheduleBookingRepo.updateBookingSchedule(bookingScheduleDTO.getBookingID(),bookingScheduleDTO.getIsAccepted());
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            responseDTO.setMessage("Success");
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setMessage("Failed");
            responseDTO.setContent(null);
        }
        return responseDTO;
    }
    public ResponseDTO cancelBooking(Long bookingID){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            BookingSchedule bookingSchedule = scheduleBookingRepo.findById(bookingID).get();

            scheduleBookingRepo.deleteBookingSchedule(bookingID);
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setItemID(bookingSchedule.getStudent().getStdID());
            notificationDTO.setItemFname(bookingSchedule.getStudent().getFname());
            notificationDTO.setItemLname(bookingSchedule.getStudent().getLname());
            notificationDTO.setItemOrEventDate(bookingSchedule.getScheduler().getStart());
            notificationDTO.setMessage("Booking Canceled");
            notificationDTO.setStatus("Success");
            notificationService.saveNotification(notificationDTO);
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            responseDTO.setMessage("Success");
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setMessage("Failed");
            responseDTO.setContent(null);
        }
        return responseDTO;
    }

}
