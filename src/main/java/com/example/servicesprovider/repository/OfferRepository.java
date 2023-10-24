package com.example.servicesprovider.repository;

import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.model.Offer;

import java.util.List;

public interface OfferRepository extends BaseRepository<Offer, Long> {

    public List<Offer> findAllByOrderId(Long order_id);
}