package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.NotificationVievedFor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationViewsForRepo extends JpaRepository<NotificationVievedFor,Long> {
}
