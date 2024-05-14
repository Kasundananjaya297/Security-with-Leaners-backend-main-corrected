package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.BookingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleBookingRepo extends JpaRepository<BookingSchedule,Long> {

    @Query(value = "SELECT * FROM booking_schedule WHERE schedulerid_fk = :schedulerID", nativeQuery = true)
    List<BookingSchedule> findBySchedulerID(Long schedulerID);

}
