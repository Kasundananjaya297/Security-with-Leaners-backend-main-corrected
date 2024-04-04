package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.ExtraSessionDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.Entity.Agreement;
import com.example.SecuritywithLeaners.Entity.ExtraSession;
import com.example.SecuritywithLeaners.Entity.Package;
import com.example.SecuritywithLeaners.Entity.Student;
import com.example.SecuritywithLeaners.Repo.ExtraSessionRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ExtraSessionService {
    @Autowired
    private ExtraSessionRepo extraSessionRepo;
    @Autowired
    private ModelMapper modelMapper;
    public ResponseDTO UpdateExtraSession(List<ExtraSessionDTO> extraSessionDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        ExtraSession extraSessions = new ExtraSession();
        for (ExtraSessionDTO e : extraSessionDTO) {
            extraSessionRepo.updateExtraSession(e.getTypeID(), e.getExtraLessons(), e.getPriceForExtraLesson(), e.getTotalLessons(), e.getStdID(), e.getPackageID());
        }
        responseDTO.setStatus(HttpStatus.ACCEPTED);
        System.out.println(extraSessionDTO);
        return responseDTO;
    }
}
