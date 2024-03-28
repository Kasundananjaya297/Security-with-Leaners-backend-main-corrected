package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.Agreement;
import com.example.SecuritywithLeaners.Entity.AgreementID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AgreementRepo extends JpaRepository<Agreement, AgreementID> {

    @Query(value = "SELECT is_finished FROM agreement WHERE stdid_stdid =:stdID AND agreement_date =(SELECT MAX(agreement_date) FROM agreement WHERE stdid_stdid=:stdID)", nativeQuery = true)
    Boolean agreementIsFinished(@Param("stdID") String stdID);
    @Query(value = "SELECT * FROM agreement WHERE stdid_stdid =:stdID",nativeQuery = true)
    List<Agreement> getAgreementsByStdID(@Param("stdID") String stdID);
}

