package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.BookingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleBookingRepo extends JpaRepository<BookingSchedule,Long> {
    
}
