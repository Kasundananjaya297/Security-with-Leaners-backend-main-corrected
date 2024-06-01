package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.*;
import com.example.SecuritywithLeaners.Entity.*;
import com.example.SecuritywithLeaners.Repo.TrainerRepo;
import com.example.SecuritywithLeaners.Repo.UsersRepo;
import com.example.SecuritywithLeaners.Util.CalculateAge;
import com.example.SecuritywithLeaners.Util.IDgenerator;
import com.example.SecuritywithLeaners.Util.SaveUer;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class TrainerService {
    @Autowired
    private TrainerRepo trainerRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IDgenerator idgenerator;
    @Autowired
    private CalculateAge calculateAge;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UsersRepo usersRepo;


    public ResponseDTO saveTrainer(TrainerDTO trainerDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            Trainers trainer = trainerRepo.availableTrainer(trainerDTO.getEmail(), trainerDTO.getTelephone(), trainerDTO.getNic(), trainerDTO.getLicenceNo());
            if(trainer == null){
                Trainers trainers = modelMapper.map(trainerDTO, Trainers.class);
                trainers.setTrainerID(idgenerator.generateTrainerID());
                trainers.setTrainerStatus("Not-ready");
                trainerRepo.save(trainers);
                UsersDTO usersDTO = new UsersDTO();
                usersDTO.setUsername(trainers.getTrainerID());
                usersDTO.setRole("TRAINER");
                usersDTO.setGeneratedPassword(SaveUer.generateRandomPassword(trainers.getTrainerID()));
                authenticationService.SaveUserInternally(usersDTO);
                responseDTO.setMessage("Trainer saved successfully");
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setContent(null);
            }else {
                Trainers trainers = modelMapper.map(trainerDTO, Trainers.class);
                trainers.setNic(trainer.getNic());
                trainers.setTelephone(trainer.getTelephone());
                trainers.setEmail(trainer.getEmail());
                responseDTO.setMessage("Trainer already exists");
                responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
                responseDTO.setCode(varList.RSP_ERROR);
                responseDTO.setContent(trainers);
            }
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setMessage("An error occurred: " + e.getMessage());
            responseDTO.setContent(null);
        }
        return responseDTO;
    }
    public ResponseDTO getAllTrainers(String field, String order,int pageSize, int offset){
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            int size = trainerRepo.findAll().size();
            Page<Trainers> trainers = trainerRepo.findAll(PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.fromString(order), field)));
            List<TrainerDTO> trainerDTOS = new ArrayList<>();
            for(Trainers trainer : trainers){
                TrainerDTO trainerDTO = modelMapper.map(trainer, TrainerDTO.class);
                trainerDTO.setAge(calculateAge.CalculateAgeINT(trainerDTO.getDateOfBirth().toString()));
                trainerDTO.setGeneratedPassword(usersRepo.findUsersByUsername(trainer.getTrainerID()).get().getGeneratedPassword());
                List<TrainerDrivingLicenceDTO> trainerDrivingLicenceDTO =new ArrayList<>();
                for(TrainerDrivingLicence trainerDrivingLicence : trainer.getTrainerDrivingLicences().stream().sorted(Comparator.comparing(TrainerDrivingLicence::getTrainerDrivingLicenceID).reversed()).toList()){
                    TrainerDrivingLicenceDTO trainerDrivingLicenceDTO1 = modelMapper.map(trainerDrivingLicence, TrainerDrivingLicenceDTO.class);
                    trainerDrivingLicenceDTO.add(trainerDrivingLicenceDTO1);
                    List<TrainerDrivingLicenceVehicleTypeDTO> trainerDrivingLicenceVehicleTypeDTO = new ArrayList<>();
                    for (TrainerDrivingLicenceVehicles trainerDrivingLicenceVehicles : trainerDrivingLicence.getTrainerDrivingLicenceVehicles()){
                        TrainerDrivingLicenceVehicleTypeDTO trainerDrivingLicenceVehicleTypeDTO1 = modelMapper.map(trainerDrivingLicenceVehicles, TrainerDrivingLicenceVehicleTypeDTO.class);
                        trainerDrivingLicenceVehicleTypeDTO.add(trainerDrivingLicenceVehicleTypeDTO1);
                        if(trainerDrivingLicenceVehicles.getVehicleType().getIsHeavy()){
                            trainerDrivingLicenceDTO1.setExpireDateForHeavy(trainerDrivingLicenceDTO1.getExpiryDate());
                            int monthsForHeavyDuty = calculateAge.CalculateMonths(trainerDrivingLicenceDTO1.getExpiryDate().toString()) -4*12;
                            int daysForHeavyDuty = calculateAge.calculateDays(trainerDrivingLicenceDTO1.getExpiryDate().toString())-4*12*365;
                            int mothsForLightWeigh = calculateAge.calcualteMonth(trainerDrivingLicenceDTO1.getExpiryDate().toString())*-1;
                            int daysForLightWeight = calculateAge.calculateDays(trainerDrivingLicenceDTO1.getExpiryDate().toString());
                            trainerDrivingLicenceDTO1.setDaysForExpireLightWeight(Math.max(daysForLightWeight, 0));
                            trainerDrivingLicenceDTO1.setDaysForExpireHeavyDuty(Math.max(daysForHeavyDuty, 0));
                            trainerDrivingLicenceDTO1.setMonthsForExpireLightWeight(Math.max(mothsForLightWeigh, 0));
                            trainerDrivingLicenceDTO1.setMonthsForExpiireHevyDuty(Math.max(monthsForHeavyDuty, 0));
                        }else{
                            trainerDrivingLicenceDTO1.setMonthsForExpiireHevyDuty(-1);
                            int months = calculateAge.calcualteMonth(trainerDrivingLicenceDTO1.getExpiryDate().toString())*-1;
                            int days = calculateAge.calculateDays(trainerDrivingLicenceDTO1.getExpiryDate().toString());
                            trainerDrivingLicenceDTO1.setDaysForExpireLightWeight(Math.max(days, 0));
                            trainerDrivingLicenceDTO1.setMonthsForExpireLightWeight(Math.max(months , 0));
                        }
                    }
                }
                List<TrainerPermitDTO> trainerPermits = new ArrayList<>();
                for(TrainerPermit trainerPermit : trainer.getTrainerPermits().stream().sorted(Comparator.comparing(TrainerPermit::getId).reversed()).toList()){
                    TrainerPermitDTO trainerPermitDTO = new TrainerPermitDTO();
                    trainerPermitDTO.setExpiryDate(trainerPermit.getExpiryDate());
                    trainerPermitDTO.setLicenceURL(trainerPermit.getLicenceURL());
                    trainerPermitDTO.setTrainerID(trainerPermit.getTrainer().getTrainerID());
                    trainerPermitDTO.setUpdatedOrIssuedOn(trainerPermit.getUpdatedOrIssuedOn());
                    int months= calculateAge.calcualteMonth(trainerPermitDTO.getExpiryDate().toString())*-1;
                    int days = calculateAge.calculateDays(trainerPermitDTO.getExpiryDate().toString())*-1;
                    trainerDTO.setTrainerPermitValidMonths(Math.max(months, 0));
                    trainerDTO.setTrainerPermitValidDays(Math.max(days, 0));
                    trainerPermitDTO.setTrainerPermitValidMonths(Math.max(months, 0));
                    trainerPermitDTO.setTrainerPermitValidDays(Math.max(days, 0));
                    trainerPermits.add(trainerPermitDTO);
                }
                List<Scheduler> trainerSchedulesList = trainer.getSchedules().stream().toList();
                List<SchedulerDTO> schedulerDTOS = new ArrayList<>();
                for(Scheduler scheduler : trainerSchedulesList){
                    SchedulerDTO schedulerDTO = new SchedulerDTO();
                    schedulerDTO.setTrainerRequestToCancel(scheduler.getTrainerRequestToCancel());
                    schedulerDTO.setTrainerFname(scheduler.getTrainer().getFname());
                    schedulerDTO.setTrainerLname(scheduler.getTrainer().getLname());
                    schedulerDTO.setTrainerID(scheduler.getTrainer().getTrainerID());
                    schedulerDTO.setSchedulerID(scheduler.getSchedulerID());
                    schedulerDTO.setStart(scheduler.getStart());
                    schedulerDTO.setEnd(scheduler.getEnd());
                    schedulerDTO.setStudentCount(scheduler.getStudentCount());
                    schedulerDTO.setTitle(scheduler.getTitle());
                    schedulerDTO.setIsStarted(scheduler.getIsStarted());
                    schedulerDTO.setIsCompleted(scheduler.getIsCompleted());
                    schedulerDTO.setVehicleRegistrationNo(scheduler.getVehicle().getRegistrationNo());
                    schedulerDTO.setMake(scheduler.getVehicle().getMake());
                    schedulerDTO.setModal(scheduler.getVehicle().getModal());
                    schedulerDTO.setVehicleClass(scheduler.getVehicle().getTypeID().getTypeID());
                    schedulerDTO.setVehicleClassName(scheduler.getVehicle().getTypeID().getTypeName());
                    schedulerDTO.setVehiclePhoto(scheduler.getVehicle().getVehiclePhoto());
                    schedulerDTO.setCompleteOn(scheduler.getCompleteOn());
                    schedulerDTO.setStartedOn(scheduler.getStartedOn());
                    schedulerDTO.setVehicleClass(scheduler.getVehicle().getTypeID().getTypeID());
                    schedulerDTO.setNumberofBooking(scheduler.getBookingSchedule().size());
                    schedulerDTO.setNumberofAttendance((int) scheduler.getBookingSchedule().stream().filter(bookingSchedule -> bookingSchedule.getIsCompleted()).count());
                    schedulerDTO.setVehicleClassName(scheduler.getVehicle().getTypeID().getTypeName());
                    List<BookingSchedule> bookingSchedules = scheduler.getBookingSchedule().stream().toList();
                    List<BookingScheduleDTO> bookingScheduleDTOS = new ArrayList<>();
                    for (BookingSchedule bookingSchedule : bookingSchedules){
                        BookingScheduleDTO bookingScheduleDTO = new BookingScheduleDTO();
                        bookingScheduleDTO.setBookingID(bookingSchedule.getBookingID());
                        bookingScheduleDTO.setBookingDate(bookingSchedule.getBookingDate());
                        bookingScheduleDTO.setBookingTime(bookingSchedule.getBookingTime());
                        bookingScheduleDTO.setStdFname(bookingSchedule.getStudent().getFname());
                        bookingScheduleDTO.setStdLname(bookingSchedule.getStudent().getLname());
                        bookingScheduleDTO.setStdID(bookingSchedule.getStudent().getStdID());
                        bookingScheduleDTO.setIsAccepted(bookingSchedule.getIsAccepted());
                        bookingScheduleDTO.setIsCanceled(bookingSchedule.getIsCanceled());
                        bookingScheduleDTO.setIsCompleted(bookingSchedule.getIsCompleted());
                        bookingScheduleDTOS.add(bookingScheduleDTO);
                    }
                    schedulerDTO.setBookingScheduleDTO(bookingScheduleDTOS);
                    schedulerDTOS.add(schedulerDTO);
                }
                trainerDTO.setSchedulerDTO(schedulerDTOS);
                //Trainer Status
                if(!trainerDrivingLicenceDTO.isEmpty() && !trainerPermits.isEmpty()) {
                    if (trainerDrivingLicenceDTO.get(0).getExpiryDate().isBefore(LocalDate.now()) && trainerPermits.get(0).getExpiryDate().isBefore(LocalDate.now()))
                    {
                        if(trainerDrivingLicenceDTO.get(0).getMonthsForExpiireHevyDuty()>0 ){
                            trainerDTO.setTrainerStatus("Expired DRL(H/L) & TRP");
                            trainerRepo.updateTrainerStatus("Expired DRL(H/L) & TRP", trainer.getTrainerID());
                        }else{
                            trainerDTO.setTrainerStatus("Expired DRL(L) & TRP");
                            trainerRepo.updateTrainerStatus("Expired DRL(L) & TRP", trainer.getTrainerID());
                        }
                    } else if (trainerDrivingLicenceDTO.get(0).getExpiryDate().isBefore(LocalDate.now())) {
                        trainerDTO.setTrainerStatus("Expired DRL");
                        trainerRepo.updateTrainerStatus("Expired DRL", trainer.getTrainerID());
                    } else if (trainerPermits.get(0).getExpiryDate().isBefore(LocalDate.now())) {
                        trainerDTO.setTrainerStatus("Expired TRP");
                        trainerRepo.updateTrainerStatus("Expired TRP", trainer.getTrainerID());
                    } else {
                        trainerDTO.setTrainerStatus("Active");
                        trainerRepo.updateTrainerStatus("Active", trainer.getTrainerID());
                    }
                }

                trainerDTO.setTrainerPermits(trainerPermits);
                trainerDTO.setTrainerDrivingLicences(trainerDrivingLicenceDTO);
                trainerDTOS.add(trainerDTO);
            }
            responseDTO.setRecordCount(size);
            responseDTO.setMessage("Trainers fetched successfully");
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setContent(trainerDTOS);
        }
        catch (Exception e){
            responseDTO.setMessage("An error occurred: " + e.getMessage());
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setContent(null);
        }
        return responseDTO;
    }
    public ResponseDTO getTrainerByLetter(String letter){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<Trainers> trainers= trainerRepo.findByDetail(letter);
            List<TrainerBasicDTO> trainerDTOS = new ArrayList<>();
            for(Trainers trainer : trainers){
                TrainerBasicDTO trainerBasicDTO = modelMapper.map(trainer, TrainerBasicDTO.class);
                trainerBasicDTO.setAge(calculateAge.CalculateAgeINT(trainer.getDateOfBirth().toString()));
                trainerDTOS.add(trainerBasicDTO);
            }

            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            responseDTO.setMessage("Trainers fetched successfully");
            responseDTO.setContent(trainerDTOS);
        }catch (Exception e){
            responseDTO.setMessage("An error occurred: " + e.getMessage());
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setContent(null);
        }
        return responseDTO;
    }
    public ResponseDTO getTrainerByVehicleClass(String vehicleClass){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<Trainers> trainers= trainerRepo.findAll();
            List<TrainerBasicDTO> trainerDTOS = new ArrayList<>();
            trainers = trainers.stream().filter(trainer ->
                            trainer.getTrainerDrivingLicences().stream().anyMatch(trainerDrivingLicence ->
                                    trainerDrivingLicence.getTrainerDrivingLicenceVehicles().stream().anyMatch(trainerDrivingLicenceVehicles ->
                                            trainerDrivingLicenceVehicles.getVehicleType().getTypeID().equals(vehicleClass)))).
                    collect(Collectors.toList());
            trainers.forEach(trainer -> {
                TrainerBasicDTO trainerBasicDTO = modelMapper.map(trainer, TrainerBasicDTO.class);
                trainerBasicDTO.setAge(calculateAge.CalculateAgeINT(trainer.getDateOfBirth().toString()));
                trainerDTOS.add(trainerBasicDTO);
            });
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            responseDTO.setMessage("Trainers fetched successfully");
            responseDTO.setContent(trainerDTOS);
        }catch (Exception e){
            responseDTO.setMessage("An error occurred: " + e.getMessage());
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setContent(null);
        }
        return responseDTO;
    }
    public ResponseDTO getTrainerByID(String trainerID){
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            Trainers trainer = trainerRepo.findById(trainerID).get();
            TrainerDTO trainerDTO = modelMapper.map(trainer, TrainerDTO.class);
            trainerDTO.setAge(calculateAge.CalculateAgeINT(trainerDTO.getDateOfBirth().toString()));
            trainerDTO.setGeneratedPassword(usersRepo.findUsersByUsername(trainer.getTrainerID()).get().getGeneratedPassword());
            List<TrainerDrivingLicenceDTO> trainerDrivingLicenceDTO =new ArrayList<>();
            for(TrainerDrivingLicence trainerDrivingLicence : trainer.getTrainerDrivingLicences().stream().sorted(Comparator.comparing(TrainerDrivingLicence::getTrainerDrivingLicenceID).reversed()).toList()){
                TrainerDrivingLicenceDTO trainerDrivingLicenceDTO1 = modelMapper.map(trainerDrivingLicence, TrainerDrivingLicenceDTO.class);
                trainerDrivingLicenceDTO.add(trainerDrivingLicenceDTO1);
                List<TrainerDrivingLicenceVehicleTypeDTO> trainerDrivingLicenceVehicleTypeDTO = new ArrayList<>();
                for (TrainerDrivingLicenceVehicles trainerDrivingLicenceVehicles : trainerDrivingLicence.getTrainerDrivingLicenceVehicles()){
                    TrainerDrivingLicenceVehicleTypeDTO trainerDrivingLicenceVehicleTypeDTO1 = modelMapper.map(trainerDrivingLicenceVehicles, TrainerDrivingLicenceVehicleTypeDTO.class);
                    trainerDrivingLicenceVehicleTypeDTO.add(trainerDrivingLicenceVehicleTypeDTO1);
                    if(trainerDrivingLicenceVehicles.getVehicleType().getIsHeavy()){
                        trainerDrivingLicenceDTO1.setExpireDateForHeavy(trainerDrivingLicenceDTO1.getExpiryDate());
                        int monthsForHeavyDuty = calculateAge.CalculateMonths(trainerDrivingLicenceDTO1.getExpiryDate().toString()) -4*12;
                        int daysForHeavyDuty = calculateAge.calculateDays(trainerDrivingLicenceDTO1.getExpiryDate().toString())-4*12*365;
                        int mothsForLightWeigh = calculateAge.calcualteMonth(trainerDrivingLicenceDTO1.getExpiryDate().toString())*-1;
                        int daysForLightWeight = calculateAge.calculateDays(trainerDrivingLicenceDTO1.getExpiryDate().toString());
                        trainerDrivingLicenceDTO1.setDaysForExpireLightWeight(Math.max(daysForLightWeight, 0));
                        trainerDrivingLicenceDTO1.setDaysForExpireHeavyDuty(Math.max(daysForHeavyDuty, 0));
                        trainerDrivingLicenceDTO1.setMonthsForExpireLightWeight(Math.max(mothsForLightWeigh, 0));
                        trainerDrivingLicenceDTO1.setMonthsForExpiireHevyDuty(Math.max(monthsForHeavyDuty, 0));
                    }else{
                        trainerDrivingLicenceDTO1.setMonthsForExpiireHevyDuty(-1);
                        int months = calculateAge.calcualteMonth(trainerDrivingLicenceDTO1.getExpiryDate().toString())*-1;
                        int days = calculateAge.calculateDays(trainerDrivingLicenceDTO1.getExpiryDate().toString());
                        trainerDrivingLicenceDTO1.setDaysForExpireLightWeight(Math.max(days, 0));
                        trainerDrivingLicenceDTO1.setMonthsForExpireLightWeight(Math.max(months , 0));
                    }
                }
            }
            List<TrainerPermitDTO> trainerPermits = new ArrayList<>();
            for(TrainerPermit trainerPermit : trainer.getTrainerPermits().stream().sorted(Comparator.comparing(TrainerPermit::getId).reversed()).toList()){
                TrainerPermitDTO trainerPermitDTO = new TrainerPermitDTO();
                trainerPermitDTO.setExpiryDate(trainerPermit.getExpiryDate());
                trainerPermitDTO.setLicenceURL(trainerPermit.getLicenceURL());
                trainerPermitDTO.setTrainerID(trainerPermit.getTrainer().getTrainerID());
                trainerPermitDTO.setUpdatedOrIssuedOn(trainerPermit.getUpdatedOrIssuedOn());
                int months= calculateAge.calcualteMonth(trainerPermitDTO.getExpiryDate().toString())*-1;
                int days = calculateAge.calculateDays(trainerPermitDTO.getExpiryDate().toString())*-1;
                trainerDTO.setTrainerPermitValidMonths(Math.max(months, 0));
                trainerDTO.setTrainerPermitValidDays(Math.max(days, 0));
                trainerPermitDTO.setTrainerPermitValidMonths(Math.max(months, 0));
                trainerPermitDTO.setTrainerPermitValidDays(Math.max(days, 0));
                trainerPermits.add(trainerPermitDTO);
            }
            //Trainer Status
            if(!trainerDrivingLicenceDTO.isEmpty() && !trainerPermits.isEmpty()) {
                if (trainerDrivingLicenceDTO.get(0).getExpiryDate().isBefore(LocalDate.now()) && trainerPermits.get(0).getExpiryDate().isBefore(LocalDate.now()))
                {
                    if(trainerDrivingLicenceDTO.get(0).getMonthsForExpiireHevyDuty()>0 ){
                        trainerDTO.setTrainerStatus("Expired DRL(H/L) & TRP");
                        trainerRepo.updateTrainerStatus("Expired DRL(H/L) & TRP", trainer.getTrainerID());
                    }else{
                        trainerDTO.setTrainerStatus("Expired DRL(L) & TRP");
                        trainerRepo.updateTrainerStatus("Expired DRL(L) & TRP", trainer.getTrainerID());
                    }
                } else if (trainerDrivingLicenceDTO.get(0).getExpiryDate().isBefore(LocalDate.now())) {
                    trainerDTO.setTrainerStatus("Expired DRL");
                    trainerRepo.updateTrainerStatus("Expired DRL", trainer.getTrainerID());
                } else if (trainerPermits.get(0).getExpiryDate().isBefore(LocalDate.now())) {
                    trainerDTO.setTrainerStatus("Expired TRP");
                    trainerRepo.updateTrainerStatus("Expired TRP", trainer.getTrainerID());
                } else {
                    trainerDTO.setTrainerStatus("Active");
                    trainerRepo.updateTrainerStatus("Active", trainer.getTrainerID());
                }
            }
            trainerDTO.setTrainerPermits(trainerPermits);
            trainerDTO.setTrainerDrivingLicences(trainerDrivingLicenceDTO);
            responseDTO.setMessage("Trainer fetched successfully");
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setContent(trainerDTO);
        }catch (Exception e){
            responseDTO.setMessage("An error occurred: " + e.getMessage());
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setContent(null);
        }

            return responseDTO;
    }

    public ResponseDTO getBookingDataByID(String trainerID){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Trainers trainer = trainerRepo.findById(trainerID).get();
            List<Scheduler> trainerSchedulesList = trainer.getSchedules().stream().toList();
            List<SchedulerDTO> schedulerDTOS = new ArrayList<>();
            for (Scheduler scheduler : trainerSchedulesList) {
                SchedulerDTO schedulerDTO = new SchedulerDTO();
                schedulerDTO.setTrainerRequestToCancel(scheduler.getTrainerRequestToCancel());
                schedulerDTO.setTrainerFname(scheduler.getTrainer().getFname());
                schedulerDTO.setTrainerLname(scheduler.getTrainer().getLname());
                schedulerDTO.setTrainerID(scheduler.getTrainer().getTrainerID());
                schedulerDTO.setSchedulerID(scheduler.getSchedulerID());
                schedulerDTO.setStart(scheduler.getStart());
                schedulerDTO.setEnd(scheduler.getEnd());
                schedulerDTO.setStudentCount(scheduler.getStudentCount());
                schedulerDTO.setTitle(scheduler.getTitle());
                schedulerDTO.setIsStarted(scheduler.getIsStarted());
                schedulerDTO.setIsCompleted(scheduler.getIsCompleted());
                schedulerDTO.setVehicleRegistrationNo(scheduler.getVehicle().getRegistrationNo());
                schedulerDTO.setMake(scheduler.getVehicle().getMake());
                schedulerDTO.setModal(scheduler.getVehicle().getModal());
                schedulerDTO.setVehicleClass(scheduler.getVehicle().getTypeID().getTypeID());
                schedulerDTO.setVehicleClassName(scheduler.getVehicle().getTypeID().getTypeName());
                schedulerDTO.setVehiclePhoto(scheduler.getVehicle().getVehiclePhoto());
                schedulerDTO.setCompleteOn(scheduler.getCompleteOn());
                schedulerDTO.setStartedOn(scheduler.getStartedOn());
                schedulerDTO.setVehicleClass(scheduler.getVehicle().getTypeID().getTypeID());
                schedulerDTO.setNumberofBooking(scheduler.getBookingSchedule().size());
                schedulerDTO.setNumberofAttendance((int) scheduler.getBookingSchedule().stream().filter(bookingSchedule -> bookingSchedule.getIsCompleted()).count());
                schedulerDTO.setVehicleClassName(scheduler.getVehicle().getTypeID().getTypeName());
                List<BookingSchedule> bookingSchedules = scheduler.getBookingSchedule().stream().toList();
                List<BookingScheduleDTO> bookingScheduleDTOS = new ArrayList<>();
                for (BookingSchedule bookingSchedule : bookingSchedules) {
                    BookingScheduleDTO bookingScheduleDTO = new BookingScheduleDTO();
                    bookingScheduleDTO.setBookingID(bookingSchedule.getBookingID());
                    bookingScheduleDTO.setBookingDate(bookingSchedule.getBookingDate());
                    bookingSchedule.setBookingTime(bookingSchedule.getBookingTime());
                    bookingScheduleDTO.setStdFname(bookingSchedule.getStudent().getFname());
                    bookingScheduleDTO.setStdLname(bookingSchedule.getStudent().getLname());
                    bookingScheduleDTO.setStdID(bookingSchedule.getStudent().getStdID());
                    bookingScheduleDTO.setIsAccepted(bookingSchedule.getIsAccepted());
                    bookingScheduleDTO.setIsCanceled(bookingSchedule.getIsCanceled());
                    bookingScheduleDTO.setIsCompleted(bookingSchedule.getIsCompleted());
                    bookingScheduleDTOS.add(bookingScheduleDTO);
                }
                schedulerDTO.setBookingScheduleDTO(bookingScheduleDTOS);
                schedulerDTOS.add(schedulerDTO);
            }
            responseDTO.setMessage("Booking data fetched successfully");
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setContent(schedulerDTOS);
        }catch (Exception e){
            responseDTO.setMessage("An error occurred: " + e.getMessage());
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setContent(null);
        }


        return responseDTO;
    }
}
