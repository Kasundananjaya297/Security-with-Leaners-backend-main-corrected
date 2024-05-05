package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.DTO.SchedulerDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class SchedulerService {
    public ResponseDTO saveSchedules(List<SchedulerDTO> schedulerDTO) {
        ResponseDTO responseDTO = new ResponseDTO();


        return responseDTO;
    }

}
