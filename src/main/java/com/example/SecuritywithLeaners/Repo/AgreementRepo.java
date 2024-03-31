package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.Agreement;
import com.example.SecuritywithLeaners.Entity.AgreementID;
import com.example.SecuritywithLeaners.Entity.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AgreementRepo extends JpaRepository<Agreement, AgreementID> {

    @Query(value = "SELECT is_finished FROM agreement WHERE stdid_stdid =:stdID AND agreement_date =(SELECT MAX(agreement_date) FROM agreement WHERE stdid_stdid=:stdID)", nativeQuery = true)
    Boolean agreementIsFinished(@Param("stdID") String stdID);
    @Query(value = "SELECT * FROM agreement WHERE stdid_stdid =:stdID",nativeQuery = true)
    List<Agreement> getAgreementsByStdID(@Param("stdID") String stdID);
    @Query(value = "SELECT package_price FROM agreement WHERE stdid_stdid =:stdID AND agreement_date =(SELECT MAX(agreement_date) FROM agreement WHERE stdid_stdid=:stdID)", nativeQuery = true)
    Double getPackagePrice(@Param("stdID") String stdID);
    @Modifying
    @Query(value = "UPDATE agreement SET discount = :discount , total_amount = :totalAmount   WHERE stdid_stdid =:stdID AND packageid_packageid = :packageID", nativeQuery = true)
    void updateDiscount(@Param("stdID") String stdID,@Param("discount") double discount,@Param("totalAmount") double totalAmount,@Param("packageID") String packageID);

    @Query(value = "SELECT total_amount FROM agreement WHERE stdid_stdid =:stdID AND agreement_date =(SELECT MAX(agreement_date) FROM agreement WHERE stdid_stdid=:stdID)", nativeQuery = true)
    Double getTotalAmount(@Param("stdID") String stdID);

}

