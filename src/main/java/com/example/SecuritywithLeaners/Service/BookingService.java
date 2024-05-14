package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.BookingScheduleDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class BookingService {
    public ResponseDTO makeBooking(BookingScheduleDTO bookingScheduleDTO){
        ResponseDTO responseDTO = new ResponseDTO();


        return responseDTO;
    }
}
