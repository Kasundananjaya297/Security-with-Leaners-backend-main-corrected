package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.MedicalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MedicalRepo extends JpaRepository<MedicalReport, String> {

    @Query(value = "SELECT MAX(examination) FROM medical_report WHERE stdID_FK = :stdID", nativeQuery = true)
    String getMaxDate(@Param("stdID") String stdID);
}
