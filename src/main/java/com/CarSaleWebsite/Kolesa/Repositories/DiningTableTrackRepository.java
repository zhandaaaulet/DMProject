package com.CarSaleWebsite.Kolesa.Repositories;

import com.CarSaleWebsite.Kolesa.Models.DiningTableTrack;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DiningTableTrackRepository extends CrudRepository<DiningTableTrack,Long> {
    @Query("select count(u.order_id) from DiningTableTrack u where u.diningTables_id=?1")
    int CountofOrderByTableID(Long id);
}