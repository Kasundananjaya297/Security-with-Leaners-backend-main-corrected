package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.CommonItemsOrServiesOfferedByServiceDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.Entity.CommonItemsOrServiesOfferedByService;
import com.example.SecuritywithLeaners.Repo.CommonItemsOrServicesOfferedByServiceRepo;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CommonItemsOrServiesOfferedByServiceService {
    @Autowired
    private CommonItemsOrServicesOfferedByServiceRepo commonItemsOrServicesOfferedByServiceRepo;
    @Autowired
    private ModelMapper modelMapper;
    public ResponseDTO saveCommonItemsOrServiesOfferedByService(CommonItemsOrServiesOfferedByServiceDTO commonItemsOrServiesOfferedByServiceDTO) {
       ResponseDTO responseDTO = new ResponseDTO();
        System.out.println(commonItemsOrServiesOfferedByServiceDTO);
       try {
              if((commonItemsOrServicesOfferedByServiceRepo.checkItemName(commonItemsOrServiesOfferedByServiceDTO.getItemName())==0)){
                  commonItemsOrServicesOfferedByServiceRepo.save(modelMapper.map(commonItemsOrServiesOfferedByServiceDTO, CommonItemsOrServiesOfferedByService.class));
                  responseDTO.setCode(varList.RSP_SUCCES);
                  responseDTO.setStatus(HttpStatus.ACCEPTED);
                  responseDTO.setMessage("Success");
              }else {
                    responseDTO.setCode(varList.RSP_DUPLICATED);
                    responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
                    responseDTO.setMessage("Failed");
              }
       }catch (Exception e){
           responseDTO.setCode(varList.RSP_ERROR);
           responseDTO.setStatus(HttpStatus.BAD_REQUEST);
           responseDTO.setMessage("Failed");
           responseDTO.setContent(null);

       }
       return responseDTO;
    }
    public ResponseDTO getCommonItemsOrServiesOfferedByService() {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            responseDTO.setMessage("Success");
            List<CommonItemsOrServiesOfferedByService> commonItemsOrServiesOfferedByService = commonItemsOrServicesOfferedByServiceRepo.findAll();
            List<CommonItemsOrServiesOfferedByServiceDTO> commonItemsOrServiesOfferedByServiceDTOS = commonItemsOrServiesOfferedByService.stream().map(commonItemsOrServiesOfferedByService1 -> modelMapper.map(commonItemsOrServiesOfferedByService1, CommonItemsOrServiesOfferedByServiceDTO.class)).toList();
            responseDTO.setContent(commonItemsOrServiesOfferedByServiceDTOS);
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setMessage("Failed");
            responseDTO.setContent(null);
        }
        return responseDTO;
    }
}
