package com.example.servicesprovider.repository;

import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.model.Offer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfferRepository extends BaseRepository<Offer, Long> {

    @Query("SELECT o FROM Offer o " +
            "WHERE o.order.id = :orderId " +
            "ORDER BY CASE WHEN :storeBy = 'price' THEN o.suggestionPrice ELSE o.technician.overallScore END")
    List<Offer> findOffersByOrderAndStoreBy(@Param("orderId") Long orderId, @Param("storeBy") String storeBy);
}