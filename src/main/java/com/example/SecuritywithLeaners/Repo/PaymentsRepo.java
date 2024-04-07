package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PaymentsRepo extends JpaRepository<Payments, Integer> {

    @Query(value = "SELECT COALESCE(SUM(amount), 0) FROM payments WHERE agreement_stdid_stdid =:stdID AND agreement_packageid_packageid =:packageID", nativeQuery = true)
    double getTotalAmountPaid(@Param("stdID") String stdID,@Param("packageID") String packageID);

    @Query(value = "SELECT * FROM payments WHERE agreement_stdid_stdid =:stdID AND agreement_packageid_packageid =:packageID", nativeQuery = true)
    List<Payments> getPaymentsByStdIDAndPackageID(@Param("stdID") String stdID, @Param("packageID") String packageID);

}
