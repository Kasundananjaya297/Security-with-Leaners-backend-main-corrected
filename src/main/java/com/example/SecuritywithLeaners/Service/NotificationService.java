package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.NotificationDTO;
import com.example.SecuritywithLeaners.DTO.NotificationViewedForDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.Entity.Notification;
import com.example.SecuritywithLeaners.Entity.NotificationVievedFor;
import com.example.SecuritywithLeaners.Repo.NotificationRepo;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            List<NotificationVievedFor> notificationVievedForList = new ArrayList<>();
            for (NotificationViewedForDTO notificationViewedForDTO: notificationDTO.getNotificationVievedForList()) {
                NotificationVievedFor notificationVievedFor = modelMapper.map(notificationViewedForDTO, NotificationVievedFor.class);
                notificationVievedFor.setNotification(notification);
                notificationVievedForList.add(notificationVievedFor);
            }
            notification.setNotificationVievedForList(notificationVievedForList);

            notificationRepo.save(notification);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public ResponseDTO getAllNotification(String viewedFor){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<Notification> notificationList = notificationRepo.findAll();
            //filter notification based on viewsFor
            List<Notification> filteredNotificationList = new ArrayList<>();
            for (Notification notification: notificationList) {
                for (NotificationVievedFor notificationVievedFor: notification.getNotificationVievedForList()) {
                    if (notificationVievedFor.getViewsFor().equals(viewedFor)){
                        filteredNotificationList.add(notification);
                        break;
                    }
                }
            }
            List<NotificationDTO> notificationDTOList = new ArrayList<>();
            for (Notification notification: filteredNotificationList) {
                NotificationDTO notificationDTO = modelMapper.map(notification, NotificationDTO.class);
                notificationDTOList.add(notificationDTO);
            }


            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setMessage("Notification List successfully fetched");
            responseDTO.setContent(notificationDTOList);
            responseDTO.setStatus(HttpStatus.ACCEPTED);
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setMessage("Notification List failed to fetch");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }
        return responseDTO;
    }
    public ResponseDTO deleteNotification(String notificationID){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Optional<Notification> notification = notificationRepo.findById(Long.parseLong(notificationID));
            if (notification.isPresent()){
                notificationRepo.delete(notification.get());
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setMessage("Notification successfully deleted");
                responseDTO.setContent(null);
                responseDTO.setStatus(HttpStatus.ACCEPTED);
            }else {
                responseDTO.setCode(varList.RSP_FAIL);
                responseDTO.setMessage("Notification not found");
                responseDTO.setContent(null);
                responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setMessage("Notification failed to delete");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }
        return responseDTO;
    }
    public ResponseDTO getAllNotificationByID(String id,String viewedFor){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<Notification> notificationList = notificationRepo.findAll();
            //filter notification based on viewsFor
            List<Notification> filteredNotificationList = new ArrayList<>();
            for (Notification notification: notificationList) {
                for (NotificationVievedFor notificationVievedFor: notification.getNotificationVievedForList()) {
                    if (notificationVievedFor.getViewsFor().equals(viewedFor)){
                        filteredNotificationList.add(notification);
                        break;
                    }
                }
            }
            // filter notification based on item ID
            List<Notification> filteredNotificationListByID = new ArrayList<>();
            for (Notification notification: filteredNotificationList) {
                if (notification.getItemID().equals(id)){
                    filteredNotificationListByID.add(notification);
                }
            }
            //set to dto
            List<NotificationDTO> notificationDTOList = new ArrayList<>();
            for (Notification notification: filteredNotificationListByID) {
                NotificationDTO notificationDTO = modelMapper.map(notification, NotificationDTO.class);
                notificationDTOList.add(notificationDTO);
            }
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setMessage("Notification List successfully fetched");
            System.out.println("+++++++++++++++++++");
            responseDTO.setContent(notificationDTOList);
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
