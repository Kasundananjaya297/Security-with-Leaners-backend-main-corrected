package com.example.SecuritywithLeaners.DTO;

import com.example.SecuritywithLeaners.Entity.NotificationVievedFor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationDTO {
    private Long notificationID;
    private String itemID;
    private String itemFname;
    private String itemLname;
    private String message;
    private LocalDateTime notificationDate;
    private Date itemOrEventDate;
    private Boolean isRead;
    private String status;
    private String image;
    private List<NotificationViewedForDTO> notificationVievedForList;
}
