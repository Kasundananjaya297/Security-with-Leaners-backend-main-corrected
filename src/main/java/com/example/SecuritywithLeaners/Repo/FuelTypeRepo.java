package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelTypeRepo extends JpaRepository<FuelType, String> {
}
