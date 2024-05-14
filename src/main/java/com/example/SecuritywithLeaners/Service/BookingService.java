package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.BookingScheduleDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.Entity.BookingSchedule;
import com.example.SecuritywithLeaners.Entity.Scheduler;
import com.example.SecuritywithLeaners.Entity.Student;
import com.example.SecuritywithLeaners.Repo.ScheduleBookingRepo;
import com.example.SecuritywithLeaners.Repo.SchedulerRepo;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class BookingService {
    @Autowired
    private ScheduleBookingRepo scheduleBookingRepo;
    @Autowired
    private SchedulerRepo schedulerRepo;

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

                bookingSchedule.setBookingDate(bookingScheduleDTO.getBookingDate());
                bookingSchedule.setBookingTime(bookingScheduleDTO.getBookingTime());
                bookingSchedule.setIsAccepted(bookingScheduleDTO.getIsAccepted());
                bookingSchedule.setIsCanceled(bookingScheduleDTO.getIsCanceled());
                bookingSchedule.setIsCompleted(bookingScheduleDTO.getIsCompleted());
                bookingSchedule.setScheduler(scheduler);
                bookingSchedule.setStudent(student);
                scheduleBookingRepo.save(bookingSchedule);
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
}
