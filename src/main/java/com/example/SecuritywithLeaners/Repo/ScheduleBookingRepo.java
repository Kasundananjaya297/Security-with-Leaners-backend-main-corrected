package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.BookingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    //update student attendance
    @Modifying
    @Query(value = "UPDATE booking_schedule SET is_completed = :isAttended WHERE bookingid = :bookingID", nativeQuery = true)
    void updateStudentAttendance(@Param("isAttended") boolean isAttended,@Param("bookingID") Long bookingID);

    //get participated lessons by student ID and is completed
    @Query(value = "SELECT * FROM booking_schedule WHERE studentid_fk = :stdID AND is_completed = true", nativeQuery = true)
    List<BookingSchedule> getParticipatedLessons(@Param("stdID") String stdID);

    //get last booked schedules by student ID
    @Query(value = "SELECT * FROM booking_schedule WHERE studentid_fk = :stdID ORDER BY bookingid DESC LIMIT 1", nativeQuery = true)
    BookingSchedule getLastBookedSchedule(@Param("stdID") String stdID);

    //get max booking id
    @Query(value = "SELECT MAX(bookingid) FROM booking_schedule", nativeQuery = true)
    Long getMaxBookingID();




}
