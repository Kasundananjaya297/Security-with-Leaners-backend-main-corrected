package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.Student;
import com.example.SecuritywithLeaners.Entity.TrialPermit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TrialPermitRepo extends JpaRepository<TrialPermit,String>{
    @Query(value = "SELECT * FROM trial_permit WHERE stdID_fk = (:stdID) ORDER BY exp_date DESC ", nativeQuery = true)
    List<TrialPermit> findAllByStdID(String stdID);
    @Query(value = "SELECT EXISTS(SELECT 1 FROM trial_permit WHERE stdID_fk = :stdID)", nativeQuery = true)
    int existsByStdID(String stdID);
    @Query(value = "SELECT MAX(exp_date) FROM trial_permit WHERE stdID_fk = :stdID", nativeQuery = true)
    LocalDate getMaximumExpDateByStdID(String stdID);





}
