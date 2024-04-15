package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepo extends JpaRepository<Insurance, String> {
}
