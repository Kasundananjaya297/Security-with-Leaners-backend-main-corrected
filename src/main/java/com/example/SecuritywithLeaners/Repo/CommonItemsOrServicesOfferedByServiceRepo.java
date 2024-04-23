package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.CommonItemsOrServiesOfferedByService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommonItemsOrServicesOfferedByServiceRepo extends JpaRepository<CommonItemsOrServiesOfferedByService,Long> {

    @Query(value = "SELECT COUNT(*) FROM common_items_or_servies_offered_by_service c WHERE c.item_name = :itemName", nativeQuery = true)
    int checkItemName(@Param("itemName") String itemName);
}
