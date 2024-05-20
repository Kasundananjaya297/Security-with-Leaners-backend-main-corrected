package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.BookingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleBookingRepo extends JpaRepository<BookingSchedule,Long> {

    @Query(value = "SELECT * FROM booking_schedule WHERE schedulerid_fk = :schedulerID", nativeQuery = true)
    List<BookingSchedule> findBySchedulerID(Long schedulerID);

    @Query(value = "SELECT * FROM booking_schedule WHERE studentid_fk = :stdID", nativeQuery = true)
    List<BookingSchedule> findByStdID(String stdID);

    //update booking schedule
    @Modifying
    @Query(value = "UPDATE booking_schedule SET is_accepted = :isAccepted WHERE bookingid = :bookingID", nativeQuery = true)
    void updateBookingSchedule(Long bookingID, boolean isAccepted);
    //delete booking schedule
    @Modifying
    @Query(value = "DELETE FROM booking_schedule WHERE bookingid = :bookingID", nativeQuery = true)
    void deleteBookingSchedule(Long bookingID);

}
