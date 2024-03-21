package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.MedicalReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRepo extends JpaRepository<MedicalReport, String> {
}
