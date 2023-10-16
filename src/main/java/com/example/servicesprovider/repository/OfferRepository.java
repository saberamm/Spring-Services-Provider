package com.example.servicesprovider.repository;

import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.model.Offer;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends BaseRepository<Offer, Long> {
}