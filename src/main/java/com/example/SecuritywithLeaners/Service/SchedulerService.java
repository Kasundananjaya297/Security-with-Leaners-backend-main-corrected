package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.BookingScheduleDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.DTO.SchedulerDTO;
import com.example.SecuritywithLeaners.DTO.VehiclesFilteringDTO;
import com.example.SecuritywithLeaners.Entity.*;
import com.example.SecuritywithLeaners.Entity.Package;
import com.example.SecuritywithLeaners.Repo.AgreementRepo;
import com.example.SecuritywithLeaners.Repo.PackageRepo;
import com.example.SecuritywithLeaners.Repo.SchedulerRepo;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class SchedulerService {
    @Autowired
    private SchedulerRepo schedulerRepo;
    @Autowired
    private AgreementRepo agreementRepo;
    @Autowired
    private ModelMapper modelMapper;


    public ResponseDTO saveSchedules(List<SchedulerDTO> schedulerDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            List<Scheduler> schedulerList = new ArrayList<>();
            //filter overlaping schedules based on trainerId, date and vehicleId
            List<Scheduler> schedulers = schedulerRepo.findAll();
            List<SchedulerDTO> schedulerDTOListRejected = new ArrayList<>();
            for(SchedulerDTO scheduler : schedulerDTO){
                for(Scheduler scheduler1 : schedulers){//fetched schedulers from db
                   if(scheduler1.getTrainer().getTrainerID().equals(scheduler.getTrainerID()) || scheduler1.getVehicle().getRegistrationNo().equals(scheduler.getRegistrationNo())){
                       if(scheduler1.getStart().before(scheduler.getEnd()) && scheduler1.getEnd().after(scheduler.getStart())){
                           schedulerDTOListRejected.add(scheduler);
                }
            }}
            }
            schedulerDTO.removeAll(schedulerDTOListRejected);
            for(SchedulerDTO scheduler : schedulerDTO){
                Trainers trainers = new Trainers();
                trainers.setTrainerID(scheduler.getTrainerID());
                Vehicle vehicle = new Vehicle();
                vehicle.setRegistrationNo(scheduler.getRegistrationNo());
                Scheduler scheduler1 = modelMapper.map(scheduler, Scheduler.class);
                scheduler1.setTrainer(trainers);
                scheduler1.setVehicle(vehicle);
                schedulerList.add(scheduler1);
            }
            schedulerRepo.saveAll(schedulerList);
            if(!schedulerDTOListRejected.isEmpty() && schedulerDTO.isEmpty()){
                responseDTO.setMessage("Schedules already exists");
                responseDTO.setCode(varList.RSP_FAIL);
                responseDTO.setContent(schedulerDTOListRejected);
                responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
            } else if (!schedulerDTOListRejected.isEmpty()) {
                responseDTO.setMessage("Some schedules already exists");
                responseDTO.setCode(varList.RSP_DUPLICATED);
                responseDTO.setContent(schedulerDTOListRejected);
                responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
            } else{
                responseDTO.setMessage("Schedules saved successfully");
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setStatus(HttpStatus.ACCEPTED);
            }
        }catch (Exception e){
            responseDTO.setMessage("Error in saving schedules");
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseDTO;
    }
    public ResponseDTO getAllSchedules(){
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            List<Scheduler> schedulerList = schedulerRepo.findAll();
            List<SchedulerDTO> schedulerDTOList = new ArrayList<>();
            for(Scheduler scheduler : schedulerList){
                SchedulerDTO schedulerDTO = modelMapper.map(scheduler, SchedulerDTO.class);
                schedulerDTO.setTrainerID(scheduler.getTrainer().getTrainerID());
                schedulerDTO.setTrainerFname(scheduler.getTrainer().getFname());
                schedulerDTO.setTrainerLname(scheduler.getTrainer().getLname());
                schedulerDTO.setContactNo(scheduler.getTrainer().getTelephone());
                schedulerDTO.setRegistrationNo(scheduler.getVehicle().getRegistrationNo());
                schedulerDTO.setMake(scheduler.getVehicle().getMake());
                schedulerDTO.setModal(scheduler.getVehicle().getModal());
                schedulerDTO.setVehicleClass(scheduler.getVehicle().getTypeID().getTypeID());
                schedulerDTO.setVehicleClassName(scheduler.getVehicle().getTypeID().getTypeName());
                schedulerDTO.setTrainerPhoto(scheduler.getTrainer().getProfilePhotoURL());
                schedulerDTO.setVehiclePhoto(scheduler.getVehicle().getVehiclePhoto());
                List<BookingScheduleDTO> bookingScheduleDTOList = new ArrayList<>();
                for(BookingSchedule bookingSchedule : scheduler.getBookingSchedule()){
                    BookingScheduleDTO bookingScheduleDTO = modelMapper.map(bookingSchedule, BookingScheduleDTO.class);
                    bookingScheduleDTO.setStdID(bookingSchedule.getStudent().getStdID());
                    bookingScheduleDTO.setStdFname(bookingSchedule.getStudent().getFname());
                    bookingScheduleDTO.setStdLname(bookingSchedule.getStudent().getLname());
                    bookingScheduleDTO.setTelephone(bookingSchedule.getStudent().getTelephone());
                    bookingScheduleDTOList.add(bookingScheduleDTO);
                }
                schedulerDTO.setBookingScheduleDTO(bookingScheduleDTOList);
                schedulerDTOList.add(schedulerDTO);
            }
            responseDTO.setContent(schedulerDTOList);
            responseDTO.setMessage("Schedules fetched successfully");
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setStatus(HttpStatus.OK);
    }catch (Exception e){
        responseDTO.setMessage("Error in fetching schedules");
        responseDTO.setCode(varList.RSP_ERROR);
        responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseDTO;
    }
    public ResponseDTO updateSchedules(SchedulerDTO schedulerDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            List<Scheduler> schedulers = schedulerRepo.findAll();
            Scheduler scheduler = modelMapper.map(schedulerDTO, Scheduler.class);
            Vehicle vehicle = new Vehicle();
            vehicle.setRegistrationNo(schedulerDTO.getRegistrationNo());
            Trainers trainers = new Trainers();
            trainers.setTrainerID(schedulerDTO.getTrainerID());
            scheduler.setVehicle(vehicle);
            scheduler.setTrainer(trainers);
            //remove the schedule from the list
            System.out.println(schedulers.size());
            schedulers.removeIf(scheduler1 -> scheduler1.getSchedulerID().equals(schedulerDTO.getSchedulerID()));
            System.out.println(schedulers.size());
            //filter overlaping schedules based on trainerId, date and vehicleId
            for (Scheduler scheduler1 : schedulers){
                if(scheduler1.getTrainer().getTrainerID().equals(scheduler.getTrainer().getTrainerID()) || scheduler1.getVehicle().getRegistrationNo().equals(scheduler.getVehicle().getRegistrationNo())){
                    if(scheduler1.getStart().before(scheduler.getEnd()) && scheduler1.getEnd().after(scheduler.getStart())){
                        responseDTO.setMessage("Schedule already exists");
                        responseDTO.setCode(varList.RSP_FAIL);
                        responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
                        return responseDTO;
                    }
                }
            }
            schedulerRepo.saveAndFlush(scheduler);
            responseDTO.setMessage("Schedule updated successfully");
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setStatus(HttpStatus.ACCEPTED);

        }catch (Exception e){
            responseDTO.setMessage("Error in updating schedule");
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseDTO;
    }
//filter schedules based on student trial permit vehicles
    public ResponseDTO getStudentSchedules(String stdID){
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            List<Scheduler> schedulers = schedulerRepo.findAll();
            List<SchedulerDTO> schedulerDTOList = new ArrayList<>();
            for(Scheduler scheduler : schedulers){
                SchedulerDTO schedulerDTO = modelMapper.map(scheduler, SchedulerDTO.class);
                schedulerDTO.setTrainerID(scheduler.getTrainer().getTrainerID());
                schedulerDTO.setTrainerFname(scheduler.getTrainer().getFname());
                schedulerDTO.setTrainerLname(scheduler.getTrainer().getLname());
                schedulerDTO.setContactNo(scheduler.getTrainer().getTelephone());
                schedulerDTO.setRegistrationNo(scheduler.getVehicle().getRegistrationNo());
                schedulerDTO.setMake(scheduler.getVehicle().getMake());
                schedulerDTO.setModal(scheduler.getVehicle().getModal());
                schedulerDTO.setVehicleClass(scheduler.getVehicle().getTypeID().getTypeID());
                schedulerDTO.setVehicleClassName(scheduler.getVehicle().getTypeID().getTypeName());
                schedulerDTO.setTrainerPhoto(scheduler.getTrainer().getProfilePhotoURL());
                schedulerDTO.setVehicleControl(scheduler.getVehicle().getAutoOrManual());
                schedulerDTO.setVehiclePhoto(scheduler.getVehicle().getVehiclePhoto());
                List<BookingScheduleDTO> bookingScheduleDTOList = new ArrayList<>();
                for(BookingSchedule bookingSchedule : scheduler.getBookingSchedule()){
                    BookingScheduleDTO bookingScheduleDTO = modelMapper.map(bookingSchedule, BookingScheduleDTO.class);
                    bookingScheduleDTO.setStdID(bookingSchedule.getStudent().getStdID());
                    bookingScheduleDTO.setStdFname(bookingSchedule.getStudent().getFname());
                    bookingScheduleDTO.setStdLname(bookingSchedule.getStudent().getLname());
                    bookingScheduleDTO.setTelephone(bookingSchedule.getStudent().getTelephone());
                    bookingScheduleDTOList.add(bookingScheduleDTO);
                }
                schedulerDTO.setBookingScheduleDTO(bookingScheduleDTOList);
                schedulerDTOList.add(schedulerDTO);
            }
            Agreement agreementList = agreementRepo.getLatestAgreement(stdID);
            List<VehiclesFilteringDTO> vehicleTypes = new ArrayList<>();

            for(PackageAndVehicleType packageAndVehicleType : agreementList.getPackageID().getPackageAndVehicleType()){
                VehiclesFilteringDTO vehiclesFilteringDTO = new VehiclesFilteringDTO();
                vehiclesFilteringDTO.setVehicleClass(packageAndVehicleType.getTypeID().getTypeID());
                vehiclesFilteringDTO.setVehicleControl(packageAndVehicleType.getAutoOrManual());
                vehicleTypes.add(vehiclesFilteringDTO);
            }
            for(ExtrasNotINAgreement extrasNotINAgreement : agreementList.getExtrasNotINAgreement()){
                VehiclesFilteringDTO vehiclesFilteringDTO = new VehiclesFilteringDTO();
                vehiclesFilteringDTO.setVehicleClass(extrasNotINAgreement.getTypeID());
                vehiclesFilteringDTO.setVehicleControl(extrasNotINAgreement.getExtraLessonVehicleType());
                vehicleTypes.add(vehiclesFilteringDTO);
            }
            //filter schedules based on student trial permit vehicles
            schedulerDTOList.removeIf(schedulerDTO -> {
                for(VehiclesFilteringDTO vehiclesFilteringDTO : vehicleTypes){
                    if(schedulerDTO.getVehicleClass().equals(vehiclesFilteringDTO.getVehicleClass()) && schedulerDTO.getVehicleControl().equals(vehiclesFilteringDTO.getVehicleControl())){
                        return false;
                    }
                }
                return true;
            });
            //filter schdules basd on current date
            schedulerDTOList.removeIf(schedulerDTO -> schedulerDTO.getStart().before(new java.util.Date()));
            responseDTO.setContent(schedulerDTOList);
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setMessage("Schedules fetched successfully");
        }catch (Exception e){
            responseDTO.setMessage("Error in fetching schedules");
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return responseDTO;
    }
    public ResponseDTO getTrainerSchedules(String trainerID){
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            List<Scheduler> schedulers = schedulerRepo.findAll();
            List<SchedulerDTO> schedulerDTOList = new ArrayList<>();
            for(Scheduler scheduler : schedulers){
                SchedulerDTO schedulerDTO = modelMapper.map(scheduler, SchedulerDTO.class);
                schedulerDTO.setTrainerID(scheduler.getTrainer().getTrainerID());
                schedulerDTO.setTrainerFname(scheduler.getTrainer().getFname());
                schedulerDTO.setTrainerLname(scheduler.getTrainer().getLname());
                schedulerDTO.setContactNo(scheduler.getTrainer().getTelephone());
                schedulerDTO.setRegistrationNo(scheduler.getVehicle().getRegistrationNo());
                schedulerDTO.setMake(scheduler.getVehicle().getMake());
                schedulerDTO.setModal(scheduler.getVehicle().getModal());
                schedulerDTO.setVehicleClass(scheduler.getVehicle().getTypeID().getTypeID());
                schedulerDTO.setVehicleClassName(scheduler.getVehicle().getTypeID().getTypeName());
                schedulerDTO.setTrainerPhoto(scheduler.getTrainer().getProfilePhotoURL());
                schedulerDTO.setVehicleControl(scheduler.getVehicle().getAutoOrManual());
                schedulerDTO.setVehiclePhoto(scheduler.getVehicle().getVehiclePhoto());
                List<BookingScheduleDTO> bookingScheduleDTOList = new ArrayList<>();
                for(BookingSchedule bookingSchedule : scheduler.getBookingSchedule()){
                    BookingScheduleDTO bookingScheduleDTO = modelMapper.map(bookingSchedule, BookingScheduleDTO.class);
                    bookingScheduleDTO.setStdID(bookingSchedule.getStudent().getStdID());
                    bookingScheduleDTO.setStdFname(bookingSchedule.getStudent().getFname());
                    bookingScheduleDTO.setStdLname(bookingSchedule.getStudent().getLname());
                    bookingScheduleDTO.setTelephone(bookingSchedule.getStudent().getTelephone());
                    bookingScheduleDTOList.add(bookingScheduleDTO);
                }
                schedulerDTO.setBookingScheduleDTO(bookingScheduleDTOList);
                schedulerDTOList.add(schedulerDTO);
            }
            //filter schedules based on trainerID
//       schedulerDTOList.removeIf(schedulerDTO -> !schedulerDTO.getTrainerID().equals(trainerID));
            responseDTO.setContent(schedulerDTOList);
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setMessage("Schedules fetched successfully");
        }catch (Exception e){
            responseDTO.setMessage("Error in fetching schedules");
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }



        return responseDTO;
    }


}
