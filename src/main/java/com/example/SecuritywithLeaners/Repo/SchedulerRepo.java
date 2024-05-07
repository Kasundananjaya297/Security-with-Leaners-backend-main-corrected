package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SchedulerRepo extends JpaRepository<Scheduler, Long>{
    @Query(value = "SELECT * FROM scheduler WHERE trainerid_fk =:trainerId AND date =:date AND vehicleid_fk =:vehicleID", nativeQuery = true)
    Optional<Scheduler> getScheduleByTrainerAndDateAndVehicle(@Param("trainerId")String trainerId, @Param("date") String date, @Param("vehicleID") String vehicleID);
}
