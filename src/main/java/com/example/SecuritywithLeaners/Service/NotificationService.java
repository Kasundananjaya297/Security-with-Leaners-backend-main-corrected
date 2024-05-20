package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.NotificationDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.Entity.Notification;
import com.example.SecuritywithLeaners.Repo.NotificationRepo;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class NotificationService {
    @Autowired
    private NotificationRepo notificationRepo;
    @Autowired
    private ModelMapper modelMapper;

    public Boolean saveNotification(NotificationDTO notificationDTO) {
        try {
            Notification notification = modelMapper.map(notificationDTO, Notification.class);
            notification.setNotificationDate(java.time.LocalDateTime.now());
            notification.setIsRead(false);
            notificationRepo.save(notification);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public ResponseDTO getAllNotification(){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<Notification> notificationList = notificationRepo.findAll();
            //sort notifications based on date and time(bubble sort)
            notificationList.sort((o1, o2) -> o2.getNotificationDate().compareTo(o1.getNotificationDate()));
            NotificationDTO notificationDTO = modelMapper.map(notificationList,NotificationDTO.class);
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setMessage("Notification List successfully fetched");
            responseDTO.setContent(notificationDTO);
            responseDTO.setStatus(HttpStatus.ACCEPTED);
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setMessage("Notification List failed to fetch");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }
        return responseDTO;
    }
}
