package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.Agreement;
import com.example.SecuritywithLeaners.Entity.AgreementID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreementRepo extends JpaRepository<Agreement, AgreementID> {

}

