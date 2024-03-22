package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.MedicalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MedicalRepo extends JpaRepository<MedicalReport, String> {

    @Query(value = "SELECT MAX(examination) FROM medical_report WHERE stdID_FK = :stdID", nativeQuery = true)
    String getMaxDate(@Param("stdID") String stdID);
    @Query(value = "SELECT * FROM medical_report WHERE stdID_FK = :stdID", nativeQuery = true)
    List<MedicalReport> getMedicalReport(@Param("stdID") String stdID);

    @Query(value= "SELECT count(*) FROM medical_report WHERE serial_no =:serialNo", nativeQuery = true)
    int checkSerialNoCount(@Param("serialNo") String serialNo);
}
