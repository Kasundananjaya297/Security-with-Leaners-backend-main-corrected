package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.Entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleTypeRepo extends JpaRepository<VehicleType, String>{

}
