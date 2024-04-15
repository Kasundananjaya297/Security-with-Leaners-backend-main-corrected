package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.InsuranceTypes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceTypeRepo extends JpaRepository<InsuranceTypes, String> {
}
