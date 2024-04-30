package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.TrainerPermit;
import com.example.SecuritywithLeaners.Entity.TrialPermit;
import com.example.SecuritywithLeaners.Service.TrialPermitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrainerPermitRepo extends JpaRepository<TrainerPermit, Long>{
   @Query(value = "SELECT count(*) FROM trainer_permit where trainerid_fk = (:trainerID) AND expiry_date = (:expiryDate)",nativeQuery = true )
   int countByTrainerIdAndExpiryDate(String trainerID, String expiryDate);
}
