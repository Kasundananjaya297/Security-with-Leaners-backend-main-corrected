package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SchedulerRepo extends JpaRepository<Scheduler, Long>{
    @Query(value = "SELECT * FROM scheduler WHERE trainerid_fk =:trainerId AND date =:date AND vehicleid_fk =:vehicleID", nativeQuery = true)
    Optional<Scheduler> getScheduleByTrainerAndDateAndVehicle(@Param("trainerId")String trainerId, @Param("date") String date, @Param("vehicleID") String vehicleID);

    @Query(value = "SELECT student_count FROM scheduler WHERE schedulerid =:schedulerID", nativeQuery = true)
    int getStudentCountBySchedulerID(@Param("schedulerID") Long schedulerID);

    @Query(value = "SELECT * FROM scheduler WHERE studentid_fk =:stdID", nativeQuery = true)
    Optional<Scheduler> getScheduleByStudentID(@Param("stdID") String stdID);


    @Modifying
    @Query(value = "UPDATE scheduler SET trainer_request_to_cancel = true WHERE  schedulerid =:schedulerid", nativeQuery = true)
    void TrainerRequsetToCancel(@Param("schedulerid") Long schedulerid);



}
