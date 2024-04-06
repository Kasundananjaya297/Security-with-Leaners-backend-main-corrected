package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.ExtraSessionDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.Entity.Agreement;
import com.example.SecuritywithLeaners.Entity.ExtraSession;
import com.example.SecuritywithLeaners.Entity.Package;
import com.example.SecuritywithLeaners.Entity.Student;
import com.example.SecuritywithLeaners.Repo.ExtraSessionRepo;
import com.example.SecuritywithLeaners.Util.varList;
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
        try{
            for (ExtraSessionDTO e : extraSessionDTO) {
                if(extraSessionRepo.checkExtraSession(e.getStdID(), e.getPackageID(), e.getTypeID()) > 0){
                    extraSessionRepo.updateExtraSession(e.getTypeID(), e.getExtraLessons(), e.getPriceForExtraLesson(), e.getTotalLessons(), e.getStdID(), e.getPackageID(), e.getPrice());
                    System.out.println("Extra Session Found");
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                    responseDTO.setCode(varList.RSP_SUCCES);
                    responseDTO.setMessage("Success");
                    responseDTO.setContent(null);
                }
                else {
                    responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
                    responseDTO.setMessage("No Data Found");
                    responseDTO.setContent(null);
                    responseDTO.setStatus(HttpStatus.NOT_FOUND);
                    break;
                }
            }
        }catch (Exception e){
            System.out.println(e);
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setMessage("Failed");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }
        return responseDTO;
    }
}
