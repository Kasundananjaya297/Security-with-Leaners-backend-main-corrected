package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepo extends JpaRepository<Notification, Long> {

}
